package name.lkk.cpdaily;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateHandle;
import name.lkk.cpdaily.databinding.FragmentAddBinding;

public class MainViewModel extends AndroidViewModel {
    SavedStateHandle handle;
    String key = getApplication().getResources().getString(R.string.user_name);
    String key2 = getApplication().getResources().getString(R.string.shp_num);
    String key3 = getApplication().getResources().getString(R.string.shp_reason);
    String key4 = getApplication().getResources().getString(R.string.shp_local);
    String key5 = getApplication().getResources().getString(R.string.shp_fdy);
    String shpName = getApplication().getResources().getString(R.string.shp_name);

    public MainViewModel(Application application, SavedStateHandle savedStateHandle) {
        super(application);
        this.handle = savedStateHandle;
        if (!savedStateHandle.contains(this.key) && !savedStateHandle.contains(this.key2) && !savedStateHandle.contains(this.key3)) {
            load();
        }
    }

    public String getData() {
        return (String) this.handle.get(this.key);
    }

    public String getNum() {
        return (String) this.handle.get(this.key2);
    }

    public String getReason() {
        return (String) this.handle.get(this.key3);
    }

    public String getLocal() {
        return (String) this.handle.get(this.key4);
    }

    public String getFdy() {
        return (String) this.handle.get(this.key5);
    }

    private void load() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(this.shpName, 0);
        String string = sharedPreferences.getString(this.key, "你的名字");
        String string2 = sharedPreferences.getString(this.key2, "");
        String string3 = sharedPreferences.getString(this.key3, "原因：\n目的地：\n出行方式：\n");
        String string4 = sharedPreferences.getString(this.key4, "中国");
        String string5 = sharedPreferences.getString(this.key5, "一级：[辅导员]XXX - 审批");
        this.handle.set(this.key, string);
        this.handle.set(this.key2, string2);
        this.handle.set(this.key3, string3);
        this.handle.set(this.key4, string4);
        this.handle.set(this.key5, string5);
    }

    /* access modifiers changed from: package-private */
    public void save(FragmentAddBinding fragmentAddBinding) {
        SharedPreferences.Editor edit = getApplication().getSharedPreferences(this.shpName, 0).edit();
        edit.putString(this.key, String.valueOf(fragmentAddBinding.editTextTextPersonName3.getText()));
        edit.putString(this.key2, String.valueOf(fragmentAddBinding.editTextTextPersonName4.getText()));
        edit.putString(this.key3, String.valueOf(fragmentAddBinding.editTextTextPersonName5.getText()));
        edit.putString(this.key4, String.valueOf(fragmentAddBinding.editTextTextPersonName6.getText()));
        edit.putString(this.key5, String.valueOf(fragmentAddBinding.editTextTextPersonName7.getText()));
        edit.apply();
    }
}
