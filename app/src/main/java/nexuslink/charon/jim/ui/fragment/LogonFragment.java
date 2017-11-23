package nexuslink.charon.jim.ui.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nexuslink.charon.jim.ui.activity.MainActivity;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnViewPagerScroll;
import nexuslink.charon.jim.model.RegisterModel;
import nexuslink.charon.jim.presenter.register.LogonPresenter;
import nexuslink.charon.jim.utils.SystemUtil;

import static nexuslink.charon.jim.Constant.KEY_USER;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:26
 * 修改人：Charon
 * 修改时间：2017/11/21 20:26
 * 修改备注：
 */

public class LogonFragment extends BaseFragment implements RegisterContract.View.Logon {
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
    @BindView(R.id.layout_logon)
    LinearLayout layoutLogon;

    private OnViewPagerScroll scroll;
    private LogonPresenter presenter = new LogonPresenter(this);
    private ProgressDialog progressDialog;

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
    public String getUsername() {
        return view2String(etUsernameLogonRegister);
    }

    @Override
    public String getPassword() {
        return view2String(etPasswordLogonRegister);
    }

    @Override
    public String check() {
        return SystemUtil.check(new RegisterModel(getUsername(), getPassword()));
    }

    @Override
    public void loading(boolean loading) {
        super.loading(loading, "登录", "正在登录", 1);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(layoutLogon, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void success(RegisterModel user) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(KEY_USER, user);
        startActivity(intent);
        getActivity().finish();
    }


    @OnClick({R.id.tv_to_login_logon_register, R.id.tv_to_forget_logon_register, R.id.btn_send_logon_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_login_logon_register:

                scroll.leftScroll();
                break;
            case R.id.tv_to_forget_logon_register:
                scroll.rightScroll();
                break;
            case R.id.btn_send_logon_register:
                SystemUtil.hideSoftKeyboard((InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE), getActivity().getWindow());

                presenter.send();
                break;
            default:
                break;
        }
    }

    private String view2String(EditText et) {
        return et.getText().toString().trim();
    }
}
