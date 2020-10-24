package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.SelfDestructiveThread;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontsContractCompat {
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    public static final String PARCEL_FONT_RESULTS = "font_results";
    static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS);
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() {
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: byte} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int compare(byte[] r5, byte[] r6) {
            /*
                r4 = this;
                int r0 = r5.length
                int r1 = r6.length
                if (r0 == r1) goto L_0x0008
                int r5 = r5.length
                int r6 = r6.length
            L_0x0006:
                int r5 = r5 - r6
                return r5
            L_0x0008:
                r0 = 0
                r1 = 0
            L_0x000a:
                int r2 = r5.length
                if (r1 >= r2) goto L_0x001b
                byte r2 = r5[r1]
                byte r3 = r6[r1]
                if (r2 == r3) goto L_0x0018
                byte r5 = r5[r1]
                byte r6 = r6[r1]
                goto L_0x0006
            L_0x0018:
                int r1 = r1 + 1
                goto L_0x000a
            L_0x001b:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontsContractCompat.AnonymousClass5.compare(byte[], byte[]):int");
        }
    };
    static final Object sLock = new Object();
    static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies = new SimpleArrayMap<>();
    static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);

    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
        public static final int RESULT_OK = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface FontRequestFailReason {
        }

        public void onTypefaceRequestFailed(int i) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }
    }

    private FontsContractCompat() {
    }

    static TypefaceResult getFontInternal(Context context, FontRequest fontRequest, int i) {
        try {
            FontFamilyResult fetchFonts = fetchFonts(context, (CancellationSignal) null, fontRequest);
            int i2 = -3;
            if (fetchFonts.getStatusCode() == 0) {
                Typeface createFromFontInfo = TypefaceCompat.createFromFontInfo(context, (CancellationSignal) null, fetchFonts.getFonts(), i);
                if (createFromFontInfo != null) {
                    i2 = 0;
                }
                return new TypefaceResult(createFromFontInfo, i2);
            }
            if (fetchFonts.getStatusCode() == 1) {
                i2 = -2;
            }
            return new TypefaceResult((Typeface) null, i2);
        } catch (PackageManager.NameNotFoundException unused) {
            return new TypefaceResult((Typeface) null, -1);
        }
    }

    private static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(Typeface typeface, int i) {
            this.mTypeface = typeface;
            this.mResult = i;
        }
    }

    public static void resetCache() {
        sTypefaceCache.evictAll();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0074, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0085, code lost:
        sBackgroundThread.postAndReply(r1, new androidx.core.provider.FontsContractCompat.AnonymousClass3());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008f, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface getFontSync(final android.content.Context r2, final androidx.core.provider.FontRequest r3, final androidx.core.content.res.ResourcesCompat.FontCallback r4, final android.os.Handler r5, boolean r6, int r7, final int r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r3.getIdentifier()
            r0.append(r1)
            java.lang.String r1 = "-"
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r1 = sTypefaceCache
            java.lang.Object r1 = r1.get(r0)
            android.graphics.Typeface r1 = (android.graphics.Typeface) r1
            if (r1 == 0) goto L_0x0028
            if (r4 == 0) goto L_0x0027
            r4.onFontRetrieved(r1)
        L_0x0027:
            return r1
        L_0x0028:
            if (r6 == 0) goto L_0x0045
            r1 = -1
            if (r7 != r1) goto L_0x0045
            androidx.core.provider.FontsContractCompat$TypefaceResult r2 = getFontInternal(r2, r3, r8)
            if (r4 == 0) goto L_0x0042
            int r3 = r2.mResult
            if (r3 != 0) goto L_0x003d
            android.graphics.Typeface r3 = r2.mTypeface
            r4.callbackSuccessAsync(r3, r5)
            goto L_0x0042
        L_0x003d:
            int r3 = r2.mResult
            r4.callbackFailAsync(r3, r5)
        L_0x0042:
            android.graphics.Typeface r2 = r2.mTypeface
            return r2
        L_0x0045:
            androidx.core.provider.FontsContractCompat$1 r1 = new androidx.core.provider.FontsContractCompat$1
            r1.<init>(r2, r3, r8, r0)
            r2 = 0
            if (r6 == 0) goto L_0x0058
            androidx.core.provider.SelfDestructiveThread r3 = sBackgroundThread     // Catch:{ InterruptedException -> 0x0057 }
            java.lang.Object r3 = r3.postAndWait(r1, r7)     // Catch:{ InterruptedException -> 0x0057 }
            androidx.core.provider.FontsContractCompat$TypefaceResult r3 = (androidx.core.provider.FontsContractCompat.TypefaceResult) r3     // Catch:{ InterruptedException -> 0x0057 }
            android.graphics.Typeface r2 = r3.mTypeface     // Catch:{ InterruptedException -> 0x0057 }
        L_0x0057:
            return r2
        L_0x0058:
            if (r4 != 0) goto L_0x005c
            r3 = r2
            goto L_0x0061
        L_0x005c:
            androidx.core.provider.FontsContractCompat$2 r3 = new androidx.core.provider.FontsContractCompat$2
            r3.<init>(r5)
        L_0x0061:
            java.lang.Object r4 = sLock
            monitor-enter(r4)
            androidx.collection.SimpleArrayMap<java.lang.String, java.util.ArrayList<androidx.core.provider.SelfDestructiveThread$ReplyCallback<androidx.core.provider.FontsContractCompat$TypefaceResult>>> r5 = sPendingReplies     // Catch:{ all -> 0x0090 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x0090 }
            java.util.ArrayList r5 = (java.util.ArrayList) r5     // Catch:{ all -> 0x0090 }
            if (r5 == 0) goto L_0x0075
            if (r3 == 0) goto L_0x0073
            r5.add(r3)     // Catch:{ all -> 0x0090 }
        L_0x0073:
            monitor-exit(r4)     // Catch:{ all -> 0x0090 }
            return r2
        L_0x0075:
            if (r3 == 0) goto L_0x0084
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ all -> 0x0090 }
            r5.<init>()     // Catch:{ all -> 0x0090 }
            r5.add(r3)     // Catch:{ all -> 0x0090 }
            androidx.collection.SimpleArrayMap<java.lang.String, java.util.ArrayList<androidx.core.provider.SelfDestructiveThread$ReplyCallback<androidx.core.provider.FontsContractCompat$TypefaceResult>>> r3 = sPendingReplies     // Catch:{ all -> 0x0090 }
            r3.put(r0, r5)     // Catch:{ all -> 0x0090 }
        L_0x0084:
            monitor-exit(r4)     // Catch:{ all -> 0x0090 }
            androidx.core.provider.SelfDestructiveThread r3 = sBackgroundThread
            androidx.core.provider.FontsContractCompat$3 r4 = new androidx.core.provider.FontsContractCompat$3
            r4.<init>(r0)
            r3.postAndReply(r1, r4)
            return r2
        L_0x0090:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0090 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontsContractCompat.getFontSync(android.content.Context, androidx.core.provider.FontRequest, androidx.core.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, int, int):android.graphics.Typeface");
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int i, int i2, boolean z, int i3) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = i;
            this.mWeight = i2;
            this.mItalic = z;
            this.mResultCode = i3;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        public FontFamilyResult(int i, FontInfo[] fontInfoArr) {
            this.mStatusCode = i;
            this.mFonts = fontInfoArr;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    public static void requestFont(Context context, FontRequest fontRequest, FontRequestCallback fontRequestCallback, Handler handler) {
        requestFontInternal(context.getApplicationContext(), fontRequest, fontRequestCallback, handler);
    }

    private static void requestFontInternal(final Context context, final FontRequest fontRequest, final FontRequestCallback fontRequestCallback, Handler handler) {
        final Handler handler2 = new Handler();
        handler.post(new Runnable() {
            public void run() {
                try {
                    FontFamilyResult fetchFonts = FontsContractCompat.fetchFonts(context, (CancellationSignal) null, fontRequest);
                    if (fetchFonts.getStatusCode() != 0) {
                        int statusCode = fetchFonts.getStatusCode();
                        if (statusCode == 1) {
                            handler2.post(new Runnable() {
                                public void run() {
                                    fontRequestCallback.onTypefaceRequestFailed(-2);
                                }
                            });
                        } else if (statusCode != 2) {
                            handler2.post(new Runnable() {
                                public void run() {
                                    fontRequestCallback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            handler2.post(new Runnable() {
                                public void run() {
                                    fontRequestCallback.onTypefaceRequestFailed(-3);
                                }
                            });
                        }
                    } else {
                        FontInfo[] fonts = fetchFonts.getFonts();
                        if (fonts == null || fonts.length == 0) {
                            handler2.post(new Runnable() {
                                public void run() {
                                    fontRequestCallback.onTypefaceRequestFailed(1);
                                }
                            });
                            return;
                        }
                        int length = fonts.length;
                        int i = 0;
                        while (i < length) {
                            FontInfo fontInfo = fonts[i];
                            if (fontInfo.getResultCode() != 0) {
                                final int resultCode = fontInfo.getResultCode();
                                if (resultCode < 0) {
                                    handler2.post(new Runnable() {
                                        public void run() {
                                            fontRequestCallback.onTypefaceRequestFailed(-3);
                                        }
                                    });
                                    return;
                                } else {
                                    handler2.post(new Runnable() {
                                        public void run() {
                                            fontRequestCallback.onTypefaceRequestFailed(resultCode);
                                        }
                                    });
                                    return;
                                }
                            } else {
                                i++;
                            }
                        }
                        final Typeface buildTypeface = FontsContractCompat.buildTypeface(context, (CancellationSignal) null, fonts);
                        if (buildTypeface == null) {
                            handler2.post(new Runnable() {
                                public void run() {
                                    fontRequestCallback.onTypefaceRequestFailed(-3);
                                }
                            });
                        } else {
                            handler2.post(new Runnable() {
                                public void run() {
                                    fontRequestCallback.onTypefaceRetrieved(buildTypeface);
                                }
                            });
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    handler2.post(new Runnable() {
                        public void run() {
                            fontRequestCallback.onTypefaceRequestFailed(-1);
                        }
                    });
                }
            }
        });
    }

    public static Typeface buildTypeface(Context context, CancellationSignal cancellationSignal, FontInfo[] fontInfoArr) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fontInfoArr, 0);
    }

    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fontInfoArr, CancellationSignal cancellationSignal) {
        HashMap hashMap = new HashMap();
        for (FontInfo fontInfo : fontInfoArr) {
            if (fontInfo.getResultCode() == 0) {
                Uri uri = fontInfo.getUri();
                if (!hashMap.containsKey(uri)) {
                    hashMap.put(uri, TypefaceCompatUtil.mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        ProviderInfo provider = getProvider(context.getPackageManager(), fontRequest, context.getResources());
        if (provider == null) {
            return new FontFamilyResult(1, (FontInfo[]) null);
        }
        return new FontFamilyResult(0, getFontFromProvider(context, fontRequest, provider.authority, cancellationSignal));
    }

    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest fontRequest, Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = fontRequest.getProviderAuthority();
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (resolveContentProvider.packageName.equals(fontRequest.getProviderPackage())) {
            List<byte[]> convertToByteArrayList = convertToByteArrayList(packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures);
            Collections.sort(convertToByteArrayList, sByteArrayComparator);
            List<List<byte[]>> certificates = getCertificates(fontRequest, resources);
            for (int i = 0; i < certificates.size(); i++) {
                ArrayList arrayList = new ArrayList(certificates.get(i));
                Collections.sort(arrayList, sByteArrayComparator);
                if (equalsByteArrayList(convertToByteArrayList, arrayList)) {
                    return resolveContentProvider;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + fontRequest.getProviderPackage());
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest fontRequest, Resources resources) {
        if (fontRequest.getCertificates() != null) {
            return fontRequest.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, fontRequest.getCertificatesArrayResId());
    }

    private static boolean equalsByteArrayList(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatureArr) {
        ArrayList arrayList = new ArrayList();
        for (Signature byteArray : signatureArr) {
            arrayList.add(byteArray.toByteArray());
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x014b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static androidx.core.provider.FontsContractCompat.FontInfo[] getFontFromProvider(android.content.Context r23, androidx.core.provider.FontRequest r24, java.lang.String r25, android.os.CancellationSignal r26) {
        /*
            r0 = r25
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.net.Uri$Builder r2 = new android.net.Uri$Builder
            r2.<init>()
            java.lang.String r3 = "content"
            android.net.Uri$Builder r2 = r2.scheme(r3)
            android.net.Uri$Builder r2 = r2.authority(r0)
            android.net.Uri r2 = r2.build()
            android.net.Uri$Builder r4 = new android.net.Uri$Builder
            r4.<init>()
            android.net.Uri$Builder r3 = r4.scheme(r3)
            android.net.Uri$Builder r0 = r3.authority(r0)
            java.lang.String r3 = "file"
            android.net.Uri$Builder r0 = r0.appendPath(r3)
            android.net.Uri r0 = r0.build()
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0147 }
            r5 = 16
            java.lang.String r9 = "font_variation_settings"
            r11 = 2
            r12 = 7
            java.lang.String r13 = "result_code"
            java.lang.String r14 = "font_italic"
            java.lang.String r15 = "font_weight"
            java.lang.String r3 = "font_ttc_index"
            java.lang.String r6 = "file_id"
            java.lang.String r7 = "_id"
            r8 = 1
            r10 = 0
            if (r4 <= r5) goto L_0x0083
            android.content.ContentResolver r4 = r23.getContentResolver()     // Catch:{ all -> 0x0147 }
            java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ all -> 0x0147 }
            r12[r10] = r7     // Catch:{ all -> 0x0147 }
            r12[r8] = r6     // Catch:{ all -> 0x0147 }
            r12[r11] = r3     // Catch:{ all -> 0x0147 }
            r5 = 3
            r12[r5] = r9     // Catch:{ all -> 0x0147 }
            r5 = 4
            r12[r5] = r15     // Catch:{ all -> 0x0147 }
            r5 = 5
            r12[r5] = r14     // Catch:{ all -> 0x0147 }
            r5 = 6
            r12[r5] = r13     // Catch:{ all -> 0x0147 }
            java.lang.String r9 = "query = ?"
            java.lang.String[] r11 = new java.lang.String[r8]     // Catch:{ all -> 0x0147 }
            java.lang.String r5 = r24.getQuery()     // Catch:{ all -> 0x0147 }
            r11[r10] = r5     // Catch:{ all -> 0x0147 }
            r16 = 0
            r5 = r2
            r20 = r1
            r1 = r6
            r6 = r12
            r12 = r7
            r7 = r9
            r9 = 1
            r8 = r11
            r11 = 1
            r9 = r16
            r11 = 0
            r10 = r26
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0147 }
            r10 = r12
            r11 = 1
            goto L_0x00b5
        L_0x0083:
            r20 = r1
            r1 = r6
            r10 = r7
            r4 = 2
            r11 = 0
            android.content.ContentResolver r5 = r23.getContentResolver()     // Catch:{ all -> 0x0147 }
            java.lang.String[] r6 = new java.lang.String[r12]     // Catch:{ all -> 0x0147 }
            r6[r11] = r10     // Catch:{ all -> 0x0147 }
            r6[r8] = r1     // Catch:{ all -> 0x0147 }
            r6[r4] = r3     // Catch:{ all -> 0x0147 }
            r4 = 3
            r6[r4] = r9     // Catch:{ all -> 0x0147 }
            r4 = 4
            r6[r4] = r15     // Catch:{ all -> 0x0147 }
            r4 = 5
            r6[r4] = r14     // Catch:{ all -> 0x0147 }
            r4 = 6
            r6[r4] = r13     // Catch:{ all -> 0x0147 }
            java.lang.String r7 = "query = ?"
            java.lang.String[] r9 = new java.lang.String[r8]     // Catch:{ all -> 0x0147 }
            java.lang.String r4 = r24.getQuery()     // Catch:{ all -> 0x0147 }
            r9[r11] = r4     // Catch:{ all -> 0x0147 }
            r12 = 0
            r4 = r5
            r5 = r2
            r11 = 1
            r8 = r9
            r9 = r12
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0147 }
        L_0x00b5:
            if (r4 == 0) goto L_0x0136
            int r5 = r4.getCount()     // Catch:{ all -> 0x0133 }
            if (r5 <= 0) goto L_0x0136
            int r5 = r4.getColumnIndex(r13)     // Catch:{ all -> 0x0133 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x0133 }
            r6.<init>()     // Catch:{ all -> 0x0133 }
            int r7 = r4.getColumnIndex(r10)     // Catch:{ all -> 0x0133 }
            int r1 = r4.getColumnIndex(r1)     // Catch:{ all -> 0x0133 }
            int r3 = r4.getColumnIndex(r3)     // Catch:{ all -> 0x0133 }
            int r8 = r4.getColumnIndex(r15)     // Catch:{ all -> 0x0133 }
            int r9 = r4.getColumnIndex(r14)     // Catch:{ all -> 0x0133 }
        L_0x00da:
            boolean r10 = r4.moveToNext()     // Catch:{ all -> 0x0133 }
            if (r10 == 0) goto L_0x0131
            r10 = -1
            if (r5 == r10) goto L_0x00ea
            int r12 = r4.getInt(r5)     // Catch:{ all -> 0x0133 }
            r22 = r12
            goto L_0x00ec
        L_0x00ea:
            r22 = 0
        L_0x00ec:
            if (r3 == r10) goto L_0x00f5
            int r12 = r4.getInt(r3)     // Catch:{ all -> 0x0133 }
            r19 = r12
            goto L_0x00f7
        L_0x00f5:
            r19 = 0
        L_0x00f7:
            if (r1 != r10) goto L_0x0102
            long r12 = r4.getLong(r7)     // Catch:{ all -> 0x0133 }
            android.net.Uri r12 = android.content.ContentUris.withAppendedId(r2, r12)     // Catch:{ all -> 0x0133 }
            goto L_0x010a
        L_0x0102:
            long r12 = r4.getLong(r1)     // Catch:{ all -> 0x0133 }
            android.net.Uri r12 = android.content.ContentUris.withAppendedId(r0, r12)     // Catch:{ all -> 0x0133 }
        L_0x010a:
            r18 = r12
            if (r8 == r10) goto L_0x0115
            int r12 = r4.getInt(r8)     // Catch:{ all -> 0x0133 }
            r20 = r12
            goto L_0x0119
        L_0x0115:
            r12 = 400(0x190, float:5.6E-43)
            r20 = 400(0x190, float:5.6E-43)
        L_0x0119:
            if (r9 == r10) goto L_0x0124
            int r10 = r4.getInt(r9)     // Catch:{ all -> 0x0133 }
            if (r10 != r11) goto L_0x0124
            r21 = 1
            goto L_0x0126
        L_0x0124:
            r21 = 0
        L_0x0126:
            androidx.core.provider.FontsContractCompat$FontInfo r10 = new androidx.core.provider.FontsContractCompat$FontInfo     // Catch:{ all -> 0x0133 }
            r17 = r10
            r17.<init>(r18, r19, r20, r21, r22)     // Catch:{ all -> 0x0133 }
            r6.add(r10)     // Catch:{ all -> 0x0133 }
            goto L_0x00da
        L_0x0131:
            r1 = r6
            goto L_0x0138
        L_0x0133:
            r0 = move-exception
            r3 = r4
            goto L_0x0149
        L_0x0136:
            r1 = r20
        L_0x0138:
            if (r4 == 0) goto L_0x013d
            r4.close()
        L_0x013d:
            r0 = 0
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = new androidx.core.provider.FontsContractCompat.FontInfo[r0]
            java.lang.Object[] r0 = r1.toArray(r0)
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = (androidx.core.provider.FontsContractCompat.FontInfo[]) r0
            return r0
        L_0x0147:
            r0 = move-exception
            r3 = 0
        L_0x0149:
            if (r3 == 0) goto L_0x014e
            r3.close()
        L_0x014e:
            goto L_0x0150
        L_0x014f:
            throw r0
        L_0x0150:
            goto L_0x014f
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontsContractCompat.getFontFromProvider(android.content.Context, androidx.core.provider.FontRequest, java.lang.String, android.os.CancellationSignal):androidx.core.provider.FontsContractCompat$FontInfo[]");
    }
}
