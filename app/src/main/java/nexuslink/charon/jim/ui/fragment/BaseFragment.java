package nexuslink.charon.jim.ui.fragment;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/23 13:07
 * 修改人：Charon
 * 修改时间：2017/11/23 13:07
 * 修改备注：
 */

public abstract class BaseFragment extends Fragment {
    private ProgressDialog dialog ;
    protected void loading(boolean loading, String title, String msg, final int position){
        final LogonFragment logonFragment = new LogonFragment();
        final LoginFragment loginFragment = new LoginFragment();
        final ForgetFragment forgetFragment = new ForgetFragment();

        if (dialog == null) {
            dialog = new ProgressDialog(getContext());
            dialog.setTitle(title);
            dialog.setMessage(msg);
        }
        if (loading) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dialog.isShowing()){
                        dialog.dismiss();
                        switch (position){
                            case 0:
                                loginFragment.showError("请检查网络状态");
                                break;
                            case 1:
                                logonFragment.showError("请检查网络状态");
                                break;
                            case 2:
                                forgetFragment.showError("请检查网络状态");
                                break;
                            default:
                                break;
                        }
                    }
                }
            },5000);
        }else {
            dialog.dismiss();
        }
    };
}
