package nexuslink.charon.jim.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.OnViewPagerScroll;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:26
 * 修改人：Charon
 * 修改时间：2017/11/21 20:26
 * 修改备注：
 */

public class LogonFragment extends Fragment implements RegisterContract.View.Logon {
    @BindView(R.id.logo_logon)
    ImageView logoLogon;
    @BindView(R.id.et_username_logon_register)
    EditText etUsernameLogonRegister;
    @BindView(R.id.et_password_logon_register)
    EditText etPasswordLogonRegister;
    @BindView(R.id.btn_send_logon_register)
    Button btnSendLogonRegister;
    @BindView(R.id.tv_to_login_logon_register)
    TextView tvToLoginLogonRegister;
    @BindView(R.id.tv_to_forget_logon_register)
    TextView tvToForgetLogonRegister;
    Unbinder unbinder;

    private OnViewPagerScroll scroll;

    public void setOnViewPagerScroll(OnViewPagerScroll scroll) {
        if (this.scroll == null) {
            this.scroll = scroll;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_logon_register, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public String getLogonUsername() {
        return null;
    }

    @Override
    public String getLogonPassword() {
        return null;
    }

    @Override
    public String checkLogon() {
        return null;
    }

    @Override
    public void loading(boolean loading) {

    }

    @Override
    public void showError(String msg) {

    }

    @OnClick({R.id.tv_to_login_logon_register, R.id.tv_to_forget_logon_register,R.id.btn_send_logon_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_login_logon_register:
                scroll.leftScroll();
                break;
            case R.id.tv_to_forget_logon_register:
                scroll.rightScroll();
                break;
            case R.id.btn_send_logon_register:
                break;
            default:
                break;
        }
    }
}
