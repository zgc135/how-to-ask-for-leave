package name.lkk.cpdaily;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import java.text.SimpleDateFormat;
import java.util.Date;
import name.lkk.cpdaily.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /* access modifiers changed from: private */
    public FragmentDetailBinding binding;
    /* access modifiers changed from: private */
    public Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            TextView textView = DetailFragment.this.binding.time;
            textView.setText("当前时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            return false;
        }
    });
    private String mParam1;
    private String mParam2;

    public static DetailFragment newInstance(String str, String str2) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.toolbar_menu, menu);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentDetailBinding inflate = FragmentDetailBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.binding.toolbar1);
        this.binding.toolbar1.setNavigationIcon((int) R.drawable.ic_baseline_arrow_back_ios_24);
        this.binding.toolbar1.setTitle((CharSequence) " ");
        this.binding.toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate((int) R.id.action_detailFragment_to_leaveFragment2);
            }
        });
        new TimeThread().start();
        String string = getArguments().getString("name");
        TextView textView = this.binding.detailContent2Layout.textView56;
        textView.setText(string + " - 发起申请");
        TextView textView2 = this.binding.detailContent2Layout.textView57;
        textView2.setText(string + " - 发起催办");
        this.binding.detailContent1Layout.textView51.setText(getArguments().getString("text2"));
        this.binding.detailContent1Layout.textView52.setText(getArguments().getString("text3"));
        this.binding.detailContent1Layout.textView53.setText(getArguments().getString("text4"));
        this.binding.detailContent1Layout.textView54.setText(getArguments().getString("text5"));
        this.binding.detailContent1Layout.textView12.setText(getArguments().getString("text10"));
        this.binding.detailContent1Layout.textView55.setText(getArguments().getString("text6"));
        this.binding.detailContent2Layout.textView20.setText(getArguments().getString("text7"));
        this.binding.detailContent2Layout.textView21.setText(getArguments().getString("text8"));
        this.binding.detailContent2Layout.textView22.setText(getArguments().getString("text9"));
        this.binding.detailContent2Layout.textView13.setText(getArguments().getString("text11"));
    }

    public class TimeThread extends Thread {
        public TimeThread() {
        }

        public void run() {
            super.run();
            while (true) {
                try {
                    Thread.sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    DetailFragment.this.handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
