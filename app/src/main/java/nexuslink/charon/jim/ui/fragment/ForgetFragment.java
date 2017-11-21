package nexuslink.charon.jim.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.OnViewPagerScroll;
import nexuslink.charon.jim.presenter.register.ForgetPresenter;
import nexuslink.charon.jim.ui.activity.RegisterActivity;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:27
 * 修改人：Charon
 * 修改时间：2017/11/21 20:27
 * 修改备注：
 */

public class ForgetFragment extends Fragment implements RegisterContract.View.Forget {
    private static ForgetFragment instance;
    @BindView(R.id.logo_forget)
    ImageView logoForget;
    @BindView(R.id.et_username_forget_register)
    EditText etUsernameForgetRegister;
    @BindView(R.id.et_code_forget_register)
    EditText etCodeForgetRegister;
    @BindView(R.id.btn_code_forget_register)
    Button btnCodeForgetRegister;
    @BindView(R.id.et_password_forget_register)
    EditText etPasswordForgetRegister;
    @BindView(R.id.et_password2_forget_register)
    EditText etPassword2ForgetRegister;
    @BindView(R.id.btn_send_forget_register)
    Button btnSendForgetRegister;
    @BindView(R.id.tv_left_forget_register)
    TextView tvLeftForgetRegister;
    Unbinder unbinder;
    @BindView(R.id.layout_forget)
    RelativeLayout layoutForget;

    private RegisterContract.Presenter.Forget presenter = new ForgetPresenter(this);
    private RegisterActivity activity;


    private OnViewPagerScroll scroll;

    public void setOnViewPagerScroll(OnViewPagerScroll scroll) {
        if (this.scroll == null) {
            this.scroll = scroll;
        }
    }

    public static ForgetFragment getInstance() {
        if (instance == null) {
            instance = new ForgetFragment();
        }
        return instance;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_forget_register, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_code_forget_register, R.id.btn_send_forget_register
    ,R.id.tv_left_forget_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code_forget_register:
                presenter.getCode();
                break;
            case R.id.btn_send_forget_register:
                presenter.send();
                break;
            case R.id.tv_left_forget_register:
                scroll.leftScroll();
                break;
            default:
                break;
        }
    }

    @Override
    public String getUsername() {
        return view2String(etUsernameForgetRegister);
    }

    @Override
    public String getCode() {
        return view2String(etCodeForgetRegister);
    }

    @Override
    public void setCode(String msg) {
        btnCodeForgetRegister.setText(msg);
    }

    @Override
    public String getPassword() {
        return view2String(etPasswordForgetRegister);
    }

    @Override
    public String getPassword2() {
        return view2String(etPassword2ForgetRegister);
    }

    @Override
    public void codeClickable(boolean clickable) {
        Button button = btnCodeForgetRegister;
        button.setClickable(clickable);
        if (clickable){
            button.setBackgroundResource(R.drawable.btn_down);
        } else {
            button.setBackgroundResource(R.drawable.btn_unclick);
        }
    }

    @Override
    public String check() {
        return null;
    }

    @Override
    public void loading(boolean loading) {

    }

    @Override
    public void showError(String msg) {
        Snackbar.make(layoutForget,msg,Snackbar.LENGTH_SHORT).show();
    }

    private String view2String(EditText et){
        return et.getText().toString().trim();
    }





}
