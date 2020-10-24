package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class NavController {
    private static final String KEY_BACK_STACK = "android-support-nav:controller:backStack";
    static final String KEY_DEEP_LINK_EXTRAS = "android-support-nav:controller:deepLinkExtras";
    static final String KEY_DEEP_LINK_HANDLED = "android-support-nav:controller:deepLinkHandled";
    static final String KEY_DEEP_LINK_IDS = "android-support-nav:controller:deepLinkIds";
    public static final String KEY_DEEP_LINK_INTENT = "android-support-nav:controller:deepLinkIntent";
    private static final String KEY_NAVIGATOR_STATE = "android-support-nav:controller:navigatorState";
    private static final String KEY_NAVIGATOR_STATE_NAMES = "android-support-nav:controller:navigatorState:names";
    private static final String TAG = "NavController";
    private Activity mActivity;
    final Deque<NavBackStackEntry> mBackStack = new ArrayDeque();
    private Parcelable[] mBackStackToRestore;
    private final Context mContext;
    private boolean mDeepLinkHandled;
    private boolean mEnableOnBackPressedCallback = true;
    NavGraph mGraph;
    private NavInflater mInflater;
    private final LifecycleObserver mLifecycleObserver = new LifecycleEventObserver() {
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (NavController.this.mGraph != null) {
                for (NavBackStackEntry handleLifecycleEvent : NavController.this.mBackStack) {
                    handleLifecycleEvent.handleLifecycleEvent(event);
                }
            }
        }
    };
    private LifecycleOwner mLifecycleOwner;
    private NavigatorProvider mNavigatorProvider = new NavigatorProvider();
    private Bundle mNavigatorStateToRestore;
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
        public void handleOnBackPressed() {
            NavController.this.popBackStack();
        }
    };
    private final CopyOnWriteArrayList<OnDestinationChangedListener> mOnDestinationChangedListeners = new CopyOnWriteArrayList<>();
    private NavControllerViewModel mViewModel;

    public interface OnDestinationChangedListener {
        void onDestinationChanged(NavController navController, NavDestination navDestination, Bundle bundle);
    }

    public NavController(Context context) {
        this.mContext = context;
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                break;
            } else if (context instanceof Activity) {
                this.mActivity = (Activity) context;
                break;
            } else {
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        this.mNavigatorProvider.addNavigator(new NavGraphNavigator(this.mNavigatorProvider));
        this.mNavigatorProvider.addNavigator(new ActivityNavigator(this.mContext));
    }

    public Deque<NavBackStackEntry> getBackStack() {
        return this.mBackStack;
    }

    /* access modifiers changed from: package-private */
    public Context getContext() {
        return this.mContext;
    }

    public NavigatorProvider getNavigatorProvider() {
        return this.mNavigatorProvider;
    }

    public void setNavigatorProvider(NavigatorProvider navigatorProvider) {
        if (this.mBackStack.isEmpty()) {
            this.mNavigatorProvider = navigatorProvider;
            return;
        }
        throw new IllegalStateException("NavigatorProvider must be set before setGraph call");
    }

    public void addOnDestinationChangedListener(OnDestinationChangedListener onDestinationChangedListener) {
        if (!this.mBackStack.isEmpty()) {
            NavBackStackEntry peekLast = this.mBackStack.peekLast();
            onDestinationChangedListener.onDestinationChanged(this, peekLast.getDestination(), peekLast.getArguments());
        }
        this.mOnDestinationChangedListeners.add(onDestinationChangedListener);
    }

    public void removeOnDestinationChangedListener(OnDestinationChangedListener onDestinationChangedListener) {
        this.mOnDestinationChangedListeners.remove(onDestinationChangedListener);
    }

    public boolean popBackStack() {
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        return popBackStack(getCurrentDestination().getId(), true);
    }

    public boolean popBackStack(int i, boolean z) {
        return popBackStackInternal(i, z) && dispatchOnDestinationChanged();
    }

    /* access modifiers changed from: package-private */
    public boolean popBackStackInternal(int i, boolean z) {
        boolean z2;
        boolean z3 = false;
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<NavBackStackEntry> descendingIterator = this.mBackStack.descendingIterator();
        while (true) {
            if (!descendingIterator.hasNext()) {
                z2 = false;
                break;
            }
            NavDestination destination = descendingIterator.next().getDestination();
            Navigator navigator = this.mNavigatorProvider.getNavigator(destination.getNavigatorName());
            if (z || destination.getId() != i) {
                arrayList.add(navigator);
            }
            if (destination.getId() == i) {
                z2 = true;
                break;
            }
        }
        if (!z2) {
            String displayName = NavDestination.getDisplayName(this.mContext, i);
            Log.i(TAG, "Ignoring popBackStack to destination " + displayName + " as it was not found on the current back stack");
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext() && ((Navigator) it.next()).popBackStack()) {
            NavBackStackEntry removeLast = this.mBackStack.removeLast();
            removeLast.setMaxLifecycle(Lifecycle.State.DESTROYED);
            NavControllerViewModel navControllerViewModel = this.mViewModel;
            if (navControllerViewModel != null) {
                navControllerViewModel.clear(removeLast.mId);
            }
            z3 = true;
        }
        updateOnBackPressedCallbackEnabled();
        return z3;
    }

    public boolean navigateUp() {
        if (getDestinationCountOnBackStack() != 1) {
            return popBackStack();
        }
        NavDestination currentDestination = getCurrentDestination();
        int id = currentDestination.getId();
        for (NavGraph parent = currentDestination.getParent(); parent != null; parent = parent.getParent()) {
            if (parent.getStartDestination() != id) {
                Bundle bundle = new Bundle();
                Activity activity = this.mActivity;
                if (!(activity == null || activity.getIntent() == null || this.mActivity.getIntent().getData() == null)) {
                    bundle.putParcelable(KEY_DEEP_LINK_INTENT, this.mActivity.getIntent());
                    NavDestination.DeepLinkMatch matchDeepLink = this.mGraph.matchDeepLink(new NavDeepLinkRequest(this.mActivity.getIntent()));
                    if (matchDeepLink != null) {
                        bundle.putAll(matchDeepLink.getMatchingArgs());
                    }
                }
                new NavDeepLinkBuilder(this).setDestination(parent.getId()).setArguments(bundle).createTaskStackBuilder().startActivities();
                Activity activity2 = this.mActivity;
                if (activity2 != null) {
                    activity2.finish();
                }
                return true;
            }
            id = parent.getId();
        }
        return false;
    }

    private int getDestinationCountOnBackStack() {
        int i = 0;
        for (NavBackStackEntry destination : this.mBackStack) {
            if (!(destination.getDestination() instanceof NavGraph)) {
                i++;
            }
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:5:0x002d, LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean dispatchOnDestinationChanged() {
        /*
            r10 = this;
        L_0x0000:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            boolean r0 = r0.isEmpty()
            r1 = 1
            if (r0 != 0) goto L_0x0030
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            androidx.navigation.NavDestination r0 = r0.getDestination()
            boolean r0 = r0 instanceof androidx.navigation.NavGraph
            if (r0 == 0) goto L_0x0030
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            androidx.navigation.NavDestination r0 = r0.getDestination()
            int r0 = r0.getId()
            boolean r0 = r10.popBackStackInternal(r0, r1)
            if (r0 == 0) goto L_0x0030
            goto L_0x0000
        L_0x0030:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0114
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            androidx.navigation.NavDestination r0 = r0.getDestination()
            r2 = 0
            boolean r3 = r0 instanceof androidx.navigation.FloatingWindow
            if (r3 == 0) goto L_0x0068
            java.util.Deque<androidx.navigation.NavBackStackEntry> r3 = r10.mBackStack
            java.util.Iterator r3 = r3.descendingIterator()
        L_0x004f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0068
            java.lang.Object r4 = r3.next()
            androidx.navigation.NavBackStackEntry r4 = (androidx.navigation.NavBackStackEntry) r4
            androidx.navigation.NavDestination r4 = r4.getDestination()
            boolean r5 = r4 instanceof androidx.navigation.NavGraph
            if (r5 != 0) goto L_0x004f
            boolean r5 = r4 instanceof androidx.navigation.FloatingWindow
            if (r5 != 0) goto L_0x004f
            r2 = r4
        L_0x0068:
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.util.Deque<androidx.navigation.NavBackStackEntry> r4 = r10.mBackStack
            java.util.Iterator r4 = r4.descendingIterator()
        L_0x0073:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00cb
            java.lang.Object r5 = r4.next()
            androidx.navigation.NavBackStackEntry r5 = (androidx.navigation.NavBackStackEntry) r5
            androidx.lifecycle.Lifecycle$State r6 = r5.getMaxLifecycle()
            androidx.navigation.NavDestination r7 = r5.getDestination()
            if (r0 == 0) goto L_0x00a1
            int r8 = r7.getId()
            int r9 = r0.getId()
            if (r8 != r9) goto L_0x00a1
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.RESUMED
            if (r6 == r7) goto L_0x009c
            androidx.lifecycle.Lifecycle$State r6 = androidx.lifecycle.Lifecycle.State.RESUMED
            r3.put(r5, r6)
        L_0x009c:
            androidx.navigation.NavGraph r0 = r0.getParent()
            goto L_0x0073
        L_0x00a1:
            if (r2 == 0) goto L_0x00c5
            int r7 = r7.getId()
            int r8 = r2.getId()
            if (r7 != r8) goto L_0x00c5
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.RESUMED
            if (r6 != r7) goto L_0x00b7
            androidx.lifecycle.Lifecycle$State r6 = androidx.lifecycle.Lifecycle.State.STARTED
            r5.setMaxLifecycle(r6)
            goto L_0x00c0
        L_0x00b7:
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.STARTED
            if (r6 == r7) goto L_0x00c0
            androidx.lifecycle.Lifecycle$State r6 = androidx.lifecycle.Lifecycle.State.STARTED
            r3.put(r5, r6)
        L_0x00c0:
            androidx.navigation.NavGraph r2 = r2.getParent()
            goto L_0x0073
        L_0x00c5:
            androidx.lifecycle.Lifecycle$State r6 = androidx.lifecycle.Lifecycle.State.CREATED
            r5.setMaxLifecycle(r6)
            goto L_0x0073
        L_0x00cb:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.util.Iterator r0 = r0.iterator()
        L_0x00d1:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x00ed
            java.lang.Object r2 = r0.next()
            androidx.navigation.NavBackStackEntry r2 = (androidx.navigation.NavBackStackEntry) r2
            java.lang.Object r4 = r3.get(r2)
            androidx.lifecycle.Lifecycle$State r4 = (androidx.lifecycle.Lifecycle.State) r4
            if (r4 == 0) goto L_0x00e9
            r2.setMaxLifecycle(r4)
            goto L_0x00d1
        L_0x00e9:
            r2.updateState()
            goto L_0x00d1
        L_0x00ed:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            java.util.concurrent.CopyOnWriteArrayList<androidx.navigation.NavController$OnDestinationChangedListener> r2 = r10.mOnDestinationChangedListeners
            java.util.Iterator r2 = r2.iterator()
        L_0x00fb:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0113
            java.lang.Object r3 = r2.next()
            androidx.navigation.NavController$OnDestinationChangedListener r3 = (androidx.navigation.NavController.OnDestinationChangedListener) r3
            androidx.navigation.NavDestination r4 = r0.getDestination()
            android.os.Bundle r5 = r0.getArguments()
            r3.onDestinationChanged(r10, r4, r5)
            goto L_0x00fb
        L_0x0113:
            return r1
        L_0x0114:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.dispatchOnDestinationChanged():boolean");
    }

    public NavInflater getNavInflater() {
        if (this.mInflater == null) {
            this.mInflater = new NavInflater(this.mContext, this.mNavigatorProvider);
        }
        return this.mInflater;
    }

    public void setGraph(int i) {
        setGraph(i, (Bundle) null);
    }

    public void setGraph(int i, Bundle bundle) {
        setGraph(getNavInflater().inflate(i), bundle);
    }

    public void setGraph(NavGraph navGraph) {
        setGraph(navGraph, (Bundle) null);
    }

    public void setGraph(NavGraph navGraph, Bundle bundle) {
        NavGraph navGraph2 = this.mGraph;
        if (navGraph2 != null) {
            popBackStackInternal(navGraph2.getId(), true);
        }
        this.mGraph = navGraph;
        onGraphCreated(bundle);
    }

    private void onGraphCreated(Bundle bundle) {
        Activity activity;
        ArrayList<String> stringArrayList;
        Bundle bundle2 = this.mNavigatorStateToRestore;
        if (!(bundle2 == null || (stringArrayList = bundle2.getStringArrayList(KEY_NAVIGATOR_STATE_NAMES)) == null)) {
            Iterator<String> it = stringArrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                Navigator navigator = this.mNavigatorProvider.getNavigator(next);
                Bundle bundle3 = this.mNavigatorStateToRestore.getBundle(next);
                if (bundle3 != null) {
                    navigator.onRestoreState(bundle3);
                }
            }
        }
        Parcelable[] parcelableArr = this.mBackStackToRestore;
        boolean z = false;
        if (parcelableArr != null) {
            int length = parcelableArr.length;
            int i = 0;
            while (i < length) {
                NavBackStackEntryState navBackStackEntryState = (NavBackStackEntryState) parcelableArr[i];
                NavDestination findDestination = findDestination(navBackStackEntryState.getDestinationId());
                if (findDestination != null) {
                    Bundle args = navBackStackEntryState.getArgs();
                    if (args != null) {
                        args.setClassLoader(this.mContext.getClassLoader());
                    }
                    this.mBackStack.add(new NavBackStackEntry(this.mContext, findDestination, args, this.mLifecycleOwner, this.mViewModel, navBackStackEntryState.getUUID(), navBackStackEntryState.getSavedState()));
                    i++;
                } else {
                    String displayName = NavDestination.getDisplayName(this.mContext, navBackStackEntryState.getDestinationId());
                    throw new IllegalStateException("Restoring the Navigation back stack failed: destination " + displayName + " cannot be found from the current destination " + getCurrentDestination());
                }
            }
            updateOnBackPressedCallbackEnabled();
            this.mBackStackToRestore = null;
        }
        if (this.mGraph == null || !this.mBackStack.isEmpty()) {
            dispatchOnDestinationChanged();
            return;
        }
        if (!this.mDeepLinkHandled && (activity = this.mActivity) != null && handleDeepLink(activity.getIntent())) {
            z = true;
        }
        if (!z) {
            navigate((NavDestination) this.mGraph, bundle, (NavOptions) null, (Navigator.Extras) null);
        }
    }

    public boolean handleDeepLink(Intent intent) {
        NavGraph navGraph;
        NavDestination.DeepLinkMatch matchDeepLink;
        if (intent == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        int[] intArray = extras != null ? extras.getIntArray(KEY_DEEP_LINK_IDS) : null;
        Bundle bundle = new Bundle();
        Bundle bundle2 = extras != null ? extras.getBundle(KEY_DEEP_LINK_EXTRAS) : null;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        if (!((intArray != null && intArray.length != 0) || intent.getData() == null || (matchDeepLink = this.mGraph.matchDeepLink(new NavDeepLinkRequest(intent))) == null)) {
            intArray = matchDeepLink.getDestination().buildDeepLinkIds();
            bundle.putAll(matchDeepLink.getMatchingArgs());
        }
        if (intArray == null || intArray.length == 0) {
            return false;
        }
        String findInvalidDestinationDisplayNameInDeepLink = findInvalidDestinationDisplayNameInDeepLink(intArray);
        if (findInvalidDestinationDisplayNameInDeepLink != null) {
            Log.i(TAG, "Could not find destination " + findInvalidDestinationDisplayNameInDeepLink + " in the navigation graph, ignoring the deep link from " + intent);
            return false;
        }
        bundle.putParcelable(KEY_DEEP_LINK_INTENT, intent);
        int flags = intent.getFlags();
        int i = 268435456 & flags;
        if (i != 0 && (flags & 32768) == 0) {
            intent.addFlags(32768);
            TaskStackBuilder.create(this.mContext).addNextIntentWithParentStack(intent).startActivities();
            Activity activity = this.mActivity;
            if (activity != null) {
                activity.finish();
                this.mActivity.overridePendingTransition(0, 0);
            }
            return true;
        } else if (i != 0) {
            if (!this.mBackStack.isEmpty()) {
                popBackStackInternal(this.mGraph.getId(), true);
            }
            int i2 = 0;
            while (i2 < intArray.length) {
                int i3 = i2 + 1;
                int i4 = intArray[i2];
                NavDestination findDestination = findDestination(i4);
                if (findDestination != null) {
                    navigate(findDestination, bundle, new NavOptions.Builder().setEnterAnim(0).setExitAnim(0).build(), (Navigator.Extras) null);
                    i2 = i3;
                } else {
                    String displayName = NavDestination.getDisplayName(this.mContext, i4);
                    throw new IllegalStateException("Deep Linking failed: destination " + displayName + " cannot be found from the current destination " + getCurrentDestination());
                }
            }
            return true;
        } else {
            NavGraph navGraph2 = this.mGraph;
            int i5 = 0;
            while (i5 < intArray.length) {
                int i6 = intArray[i5];
                NavDestination findNode = i5 == 0 ? this.mGraph : navGraph2.findNode(i6);
                if (findNode != null) {
                    if (i5 != intArray.length - 1) {
                        while (true) {
                            navGraph = (NavGraph) findNode;
                            if (!(navGraph.findNode(navGraph.getStartDestination()) instanceof NavGraph)) {
                                break;
                            }
                            findNode = navGraph.findNode(navGraph.getStartDestination());
                        }
                        navGraph2 = navGraph;
                    } else {
                        navigate(findNode, findNode.addInDefaultArgs(bundle), new NavOptions.Builder().setPopUpTo(this.mGraph.getId(), true).setEnterAnim(0).setExitAnim(0).build(), (Navigator.Extras) null);
                    }
                    i5++;
                } else {
                    String displayName2 = NavDestination.getDisplayName(this.mContext, i6);
                    throw new IllegalStateException("Deep Linking failed: destination " + displayName2 + " cannot be found in graph " + navGraph2);
                }
            }
            this.mDeepLinkHandled = true;
            return true;
        }
    }

    private String findInvalidDestinationDisplayNameInDeepLink(int[] iArr) {
        NavGraph navGraph;
        NavGraph navGraph2 = this.mGraph;
        int i = 0;
        while (true) {
            NavDestination navDestination = null;
            if (i >= iArr.length) {
                return null;
            }
            int i2 = iArr[i];
            if (i != 0) {
                navDestination = navGraph2.findNode(i2);
            } else if (this.mGraph.getId() == i2) {
                navDestination = this.mGraph;
            }
            if (navDestination == null) {
                return NavDestination.getDisplayName(this.mContext, i2);
            }
            if (i != iArr.length - 1) {
                while (true) {
                    navGraph = (NavGraph) navDestination;
                    if (!(navGraph.findNode(navGraph.getStartDestination()) instanceof NavGraph)) {
                        break;
                    }
                    navDestination = navGraph.findNode(navGraph.getStartDestination());
                }
                navGraph2 = navGraph;
            }
            i++;
        }
    }

    public NavGraph getGraph() {
        NavGraph navGraph = this.mGraph;
        if (navGraph != null) {
            return navGraph;
        }
        throw new IllegalStateException("You must call setGraph() before calling getGraph()");
    }

    public NavDestination getCurrentDestination() {
        NavBackStackEntry currentBackStackEntry = getCurrentBackStackEntry();
        if (currentBackStackEntry != null) {
            return currentBackStackEntry.getDestination();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public NavDestination findDestination(int i) {
        NavDestination navDestination;
        NavGraph navGraph;
        NavGraph navGraph2 = this.mGraph;
        if (navGraph2 == null) {
            return null;
        }
        if (navGraph2.getId() == i) {
            return this.mGraph;
        }
        if (this.mBackStack.isEmpty()) {
            navDestination = this.mGraph;
        } else {
            navDestination = this.mBackStack.getLast().getDestination();
        }
        if (navDestination instanceof NavGraph) {
            navGraph = (NavGraph) navDestination;
        } else {
            navGraph = navDestination.getParent();
        }
        return navGraph.findNode(i);
    }

    public void navigate(int i) {
        navigate(i, (Bundle) null);
    }

    public void navigate(int i, Bundle bundle) {
        navigate(i, bundle, (NavOptions) null);
    }

    public void navigate(int i, Bundle bundle, NavOptions navOptions) {
        navigate(i, bundle, navOptions, (Navigator.Extras) null);
    }

    public void navigate(int i, Bundle bundle, NavOptions navOptions, Navigator.Extras extras) {
        NavDestination navDestination;
        int i2;
        if (this.mBackStack.isEmpty()) {
            navDestination = this.mGraph;
        } else {
            navDestination = this.mBackStack.getLast().getDestination();
        }
        if (navDestination != null) {
            NavAction action = navDestination.getAction(i);
            Bundle bundle2 = null;
            if (action != null) {
                if (navOptions == null) {
                    navOptions = action.getNavOptions();
                }
                i2 = action.getDestinationId();
                Bundle defaultArguments = action.getDefaultArguments();
                if (defaultArguments != null) {
                    bundle2 = new Bundle();
                    bundle2.putAll(defaultArguments);
                }
            } else {
                i2 = i;
            }
            if (bundle != null) {
                if (bundle2 == null) {
                    bundle2 = new Bundle();
                }
                bundle2.putAll(bundle);
            }
            if (i2 == 0 && navOptions != null && navOptions.getPopUpTo() != -1) {
                popBackStack(navOptions.getPopUpTo(), navOptions.isPopUpToInclusive());
            } else if (i2 != 0) {
                NavDestination findDestination = findDestination(i2);
                if (findDestination == null) {
                    String displayName = NavDestination.getDisplayName(this.mContext, i2);
                    if (action != null) {
                        throw new IllegalArgumentException("Navigation destination " + displayName + " referenced from action " + NavDestination.getDisplayName(this.mContext, i) + " cannot be found from the current destination " + navDestination);
                    }
                    throw new IllegalArgumentException("Navigation action/destination " + displayName + " cannot be found from the current destination " + navDestination);
                }
                navigate(findDestination, bundle2, navOptions, extras);
            } else {
                throw new IllegalArgumentException("Destination id == 0 can only be used in conjunction with a valid navOptions.popUpTo");
            }
        } else {
            throw new IllegalStateException("no current navigation node");
        }
    }

    public void navigate(Uri uri) {
        navigate(new NavDeepLinkRequest(uri, (String) null, (String) null));
    }

    public void navigate(Uri uri, NavOptions navOptions) {
        navigate(new NavDeepLinkRequest(uri, (String) null, (String) null), navOptions);
    }

    public void navigate(Uri uri, NavOptions navOptions, Navigator.Extras extras) {
        navigate(new NavDeepLinkRequest(uri, (String) null, (String) null), navOptions, extras);
    }

    public void navigate(NavDeepLinkRequest navDeepLinkRequest) {
        navigate(navDeepLinkRequest, (NavOptions) null);
    }

    public void navigate(NavDeepLinkRequest navDeepLinkRequest, NavOptions navOptions) {
        navigate(navDeepLinkRequest, navOptions, (Navigator.Extras) null);
    }

    public void navigate(NavDeepLinkRequest navDeepLinkRequest, NavOptions navOptions, Navigator.Extras extras) {
        NavDestination.DeepLinkMatch matchDeepLink = this.mGraph.matchDeepLink(navDeepLinkRequest);
        if (matchDeepLink != null) {
            navigate(matchDeepLink.getDestination(), matchDeepLink.getMatchingArgs(), navOptions, extras);
            return;
        }
        throw new IllegalArgumentException("Navigation destination that matches request " + navDeepLinkRequest + " cannot be found in the navigation graph " + this.mGraph);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0031 A[LOOP:0: B:10:0x0031->B:15:0x005d, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void navigate(androidx.navigation.NavDestination r11, android.os.Bundle r12, androidx.navigation.NavOptions r13, androidx.navigation.Navigator.Extras r14) {
        /*
            r10 = this;
            r0 = 0
            if (r13 == 0) goto L_0x0017
            int r1 = r13.getPopUpTo()
            r2 = -1
            if (r1 == r2) goto L_0x0017
            int r1 = r13.getPopUpTo()
            boolean r2 = r13.isPopUpToInclusive()
            boolean r1 = r10.popBackStackInternal(r1, r2)
            goto L_0x0018
        L_0x0017:
            r1 = 0
        L_0x0018:
            androidx.navigation.NavigatorProvider r2 = r10.mNavigatorProvider
            java.lang.String r3 = r11.getNavigatorName()
            androidx.navigation.Navigator r2 = r2.getNavigator((java.lang.String) r3)
            android.os.Bundle r9 = r11.addInDefaultArgs(r12)
            androidx.navigation.NavDestination r11 = r2.navigate(r11, r9, r13, r14)
            r14 = 1
            if (r11 == 0) goto L_0x00c2
            boolean r12 = r11 instanceof androidx.navigation.FloatingWindow
            if (r12 != 0) goto L_0x0060
        L_0x0031:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r12 = r10.mBackStack
            boolean r12 = r12.isEmpty()
            if (r12 != 0) goto L_0x0060
            java.util.Deque<androidx.navigation.NavBackStackEntry> r12 = r10.mBackStack
            java.lang.Object r12 = r12.peekLast()
            androidx.navigation.NavBackStackEntry r12 = (androidx.navigation.NavBackStackEntry) r12
            androidx.navigation.NavDestination r12 = r12.getDestination()
            boolean r12 = r12 instanceof androidx.navigation.FloatingWindow
            if (r12 == 0) goto L_0x0060
            java.util.Deque<androidx.navigation.NavBackStackEntry> r12 = r10.mBackStack
            java.lang.Object r12 = r12.peekLast()
            androidx.navigation.NavBackStackEntry r12 = (androidx.navigation.NavBackStackEntry) r12
            androidx.navigation.NavDestination r12 = r12.getDestination()
            int r12 = r12.getId()
            boolean r12 = r10.popBackStackInternal(r12, r14)
            if (r12 == 0) goto L_0x0060
            goto L_0x0031
        L_0x0060:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r12 = r10.mBackStack
            boolean r12 = r12.isEmpty()
            if (r12 == 0) goto L_0x007c
            androidx.navigation.NavBackStackEntry r12 = new androidx.navigation.NavBackStackEntry
            android.content.Context r4 = r10.mContext
            androidx.navigation.NavGraph r5 = r10.mGraph
            androidx.lifecycle.LifecycleOwner r7 = r10.mLifecycleOwner
            androidx.navigation.NavControllerViewModel r8 = r10.mViewModel
            r3 = r12
            r6 = r9
            r3.<init>(r4, r5, r6, r7, r8)
            java.util.Deque<androidx.navigation.NavBackStackEntry> r13 = r10.mBackStack
            r13.add(r12)
        L_0x007c:
            java.util.ArrayDeque r12 = new java.util.ArrayDeque
            r12.<init>()
            r13 = r11
        L_0x0082:
            if (r13 == 0) goto L_0x00a6
            int r14 = r13.getId()
            androidx.navigation.NavDestination r14 = r10.findDestination(r14)
            if (r14 != 0) goto L_0x00a6
            androidx.navigation.NavGraph r13 = r13.getParent()
            if (r13 == 0) goto L_0x0082
            androidx.navigation.NavBackStackEntry r14 = new androidx.navigation.NavBackStackEntry
            android.content.Context r4 = r10.mContext
            androidx.lifecycle.LifecycleOwner r7 = r10.mLifecycleOwner
            androidx.navigation.NavControllerViewModel r8 = r10.mViewModel
            r3 = r14
            r5 = r13
            r6 = r9
            r3.<init>(r4, r5, r6, r7, r8)
            r12.addFirst(r14)
            goto L_0x0082
        L_0x00a6:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r13 = r10.mBackStack
            r13.addAll(r12)
            androidx.navigation.NavBackStackEntry r12 = new androidx.navigation.NavBackStackEntry
            android.content.Context r4 = r10.mContext
            android.os.Bundle r6 = r11.addInDefaultArgs(r9)
            androidx.lifecycle.LifecycleOwner r7 = r10.mLifecycleOwner
            androidx.navigation.NavControllerViewModel r8 = r10.mViewModel
            r3 = r12
            r5 = r11
            r3.<init>(r4, r5, r6, r7, r8)
            java.util.Deque<androidx.navigation.NavBackStackEntry> r13 = r10.mBackStack
            r13.add(r12)
            goto L_0x00d8
        L_0x00c2:
            if (r13 == 0) goto L_0x00d8
            boolean r13 = r13.shouldLaunchSingleTop()
            if (r13 == 0) goto L_0x00d8
            java.util.Deque<androidx.navigation.NavBackStackEntry> r13 = r10.mBackStack
            java.lang.Object r13 = r13.peekLast()
            androidx.navigation.NavBackStackEntry r13 = (androidx.navigation.NavBackStackEntry) r13
            if (r13 == 0) goto L_0x00d7
            r13.replaceArguments(r12)
        L_0x00d7:
            r0 = 1
        L_0x00d8:
            r10.updateOnBackPressedCallbackEnabled()
            if (r1 != 0) goto L_0x00e1
            if (r11 != 0) goto L_0x00e1
            if (r0 == 0) goto L_0x00e4
        L_0x00e1:
            r10.dispatchOnDestinationChanged()
        L_0x00e4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.navigate(androidx.navigation.NavDestination, android.os.Bundle, androidx.navigation.NavOptions, androidx.navigation.Navigator$Extras):void");
    }

    public void navigate(NavDirections navDirections) {
        navigate(navDirections.getActionId(), navDirections.getArguments());
    }

    public void navigate(NavDirections navDirections, NavOptions navOptions) {
        navigate(navDirections.getActionId(), navDirections.getArguments(), navOptions);
    }

    public void navigate(NavDirections navDirections, Navigator.Extras extras) {
        navigate(navDirections.getActionId(), navDirections.getArguments(), (NavOptions) null, extras);
    }

    public NavDeepLinkBuilder createDeepLink() {
        return new NavDeepLinkBuilder(this);
    }

    public Bundle saveState() {
        Bundle bundle;
        ArrayList arrayList = new ArrayList();
        Bundle bundle2 = new Bundle();
        for (Map.Entry next : this.mNavigatorProvider.getNavigators().entrySet()) {
            String str = (String) next.getKey();
            Bundle onSaveState = ((Navigator) next.getValue()).onSaveState();
            if (onSaveState != null) {
                arrayList.add(str);
                bundle2.putBundle(str, onSaveState);
            }
        }
        if (!arrayList.isEmpty()) {
            bundle = new Bundle();
            bundle2.putStringArrayList(KEY_NAVIGATOR_STATE_NAMES, arrayList);
            bundle.putBundle(KEY_NAVIGATOR_STATE, bundle2);
        } else {
            bundle = null;
        }
        if (!this.mBackStack.isEmpty()) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            Parcelable[] parcelableArr = new Parcelable[this.mBackStack.size()];
            int i = 0;
            for (NavBackStackEntry navBackStackEntryState : this.mBackStack) {
                parcelableArr[i] = new NavBackStackEntryState(navBackStackEntryState);
                i++;
            }
            bundle.putParcelableArray(KEY_BACK_STACK, parcelableArr);
        }
        if (this.mDeepLinkHandled) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(KEY_DEEP_LINK_HANDLED, this.mDeepLinkHandled);
        }
        return bundle;
    }

    public void restoreState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(this.mContext.getClassLoader());
            this.mNavigatorStateToRestore = bundle.getBundle(KEY_NAVIGATOR_STATE);
            this.mBackStackToRestore = bundle.getParcelableArray(KEY_BACK_STACK);
            this.mDeepLinkHandled = bundle.getBoolean(KEY_DEEP_LINK_HANDLED);
        }
    }

    /* access modifiers changed from: package-private */
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this.mLifecycleObserver);
    }

    /* access modifiers changed from: package-private */
    public void setOnBackPressedDispatcher(OnBackPressedDispatcher onBackPressedDispatcher) {
        if (this.mLifecycleOwner != null) {
            this.mOnBackPressedCallback.remove();
            onBackPressedDispatcher.addCallback(this.mLifecycleOwner, this.mOnBackPressedCallback);
            return;
        }
        throw new IllegalStateException("You must call setLifecycleOwner() before calling setOnBackPressedDispatcher()");
    }

    /* access modifiers changed from: package-private */
    public void enableOnBackPressed(boolean z) {
        this.mEnableOnBackPressedCallback = z;
        updateOnBackPressedCallbackEnabled();
    }

    private void updateOnBackPressedCallbackEnabled() {
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        boolean z = true;
        if (!this.mEnableOnBackPressedCallback || getDestinationCountOnBackStack() <= 1) {
            z = false;
        }
        onBackPressedCallback.setEnabled(z);
    }

    /* access modifiers changed from: package-private */
    public void setViewModelStore(ViewModelStore viewModelStore) {
        if (this.mBackStack.isEmpty()) {
            this.mViewModel = NavControllerViewModel.getInstance(viewModelStore);
            return;
        }
        throw new IllegalStateException("ViewModelStore should be set before setGraph call");
    }

    public ViewModelStoreOwner getViewModelStoreOwner(int i) {
        if (this.mViewModel != null) {
            NavBackStackEntry backStackEntry = getBackStackEntry(i);
            if (backStackEntry.getDestination() instanceof NavGraph) {
                return backStackEntry;
            }
            throw new IllegalArgumentException("No NavGraph with ID " + i + " is on the NavController's back stack");
        }
        throw new IllegalStateException("You must call setViewModelStore() before calling getViewModelStoreOwner().");
    }

    public NavBackStackEntry getBackStackEntry(int i) {
        NavBackStackEntry navBackStackEntry;
        Iterator<NavBackStackEntry> descendingIterator = this.mBackStack.descendingIterator();
        while (true) {
            if (!descendingIterator.hasNext()) {
                navBackStackEntry = null;
                break;
            }
            navBackStackEntry = descendingIterator.next();
            if (navBackStackEntry.getDestination().getId() == i) {
                break;
            }
        }
        if (navBackStackEntry != null) {
            return navBackStackEntry;
        }
        throw new IllegalArgumentException("No destination with ID " + i + " is on the NavController's back stack. The current destination is " + getCurrentDestination());
    }

    public NavBackStackEntry getCurrentBackStackEntry() {
        if (this.mBackStack.isEmpty()) {
            return null;
        }
        return this.mBackStack.getLast();
    }

    public NavBackStackEntry getPreviousBackStackEntry() {
        Iterator<NavBackStackEntry> descendingIterator = this.mBackStack.descendingIterator();
        if (descendingIterator.hasNext()) {
            descendingIterator.next();
        }
        while (descendingIterator.hasNext()) {
            NavBackStackEntry next = descendingIterator.next();
            if (!(next.getDestination() instanceof NavGraph)) {
                return next;
            }
        }
        return null;
    }
}
