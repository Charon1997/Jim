package nexuslink.charon.jim.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import cn.smssdk.SMSSDK;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnViewPagerScroll;
import nexuslink.charon.jim.presenter.register.LoginPresenter;
import nexuslink.charon.jim.utils.SystemUtil;

import static nexuslink.charon.jim.Constant.COUNT_DOWN_SECOND;
import static nexuslink.charon.jim.Constant.CURRENT_TIME;
import static nexuslink.charon.jim.Constant.FORGET_COUNT_DOWN_TIME;
import static nexuslink.charon.jim.Constant.LOGIN_COUNT_DOWN_TIME;
import static nexuslink.charon.jim.Constant.PHONE_LENGTH;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:23
 * 修改人：Charon
 * 修改时间：2017/11/21 20:23
 * 修改备注：
 */

public class LoginFragment extends Fragment implements RegisterContract.View.Login {
    @BindView(R.id.logo_login)
    ImageView logoLogin;
    @BindView(R.id.et_username_login_register)
    EditText etUsernameLoginRegister;
    @BindView(R.id.et_code_login_register)
    EditText etCodeLoginRegister;
    @BindView(R.id.btn_code_login_register)
    Button btnCodeLoginRegister;
    @BindView(R.id.et_password_login_register)
    EditText etPasswordLoginRegister;
    @BindView(R.id.et_password2_login_register)
    EditText etPassword2LoginRegister;
    @BindView(R.id.btn_send_login_register)
    Button btnSendLoginRegister;
    @BindView(R.id.tv_right_login_register)
    TextView tvRightLogonRegister;
    Unbinder unbinder;
    @BindView(R.id.layout_login)
    LinearLayout layoutLogin;

    private OnViewPagerScroll scroll;
    private SharedPreferences sp;
    private CountDownTimer timer;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    public void setOnViewPagerScroll(OnViewPagerScroll scroll) {
        if (this.scroll == null) {
            this.scroll = scroll;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login_register, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences(LOGIN_COUNT_DOWN_TIME, Context.MODE_PRIVATE);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        isCountDown();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_code_login_register, R.id.btn_send_login_register,
            R.id.tv_right_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code_login_register:
                SystemUtil.hideSoftKeyboard((InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE), getActivity().getWindow());
                //发送短信
                if (getUsername().length() == PHONE_LENGTH) {
                    SMSSDK.getVerificationCode("86", getUsername());
                } else {
                    showError("请输入正确的手机号");
                }
                break;
            case R.id.btn_send_login_register:
                SystemUtil.hideSoftKeyboard((InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE), getActivity().getWindow());
                //presenter.send();
                if (check().equals("正确")) {
                    //验证
                    SMSSDK.submitVerificationCode("86", getUsername(), getCode());
                } else {
                    showError(check());
                }
                break;
            case R.id.tv_right_login_register:
                scroll.rightScroll();
                break;
            default:
                break;
        }
    }


    private String view2String(EditText et) {
        return et.getText().toString().trim();
    }

    @Override
    public String getUsername() {
        return view2String(etUsernameLoginRegister);
    }

    @Override
    public String getCode() {
        return view2String(etCodeLoginRegister);
    }

    @Override
    public void setCode(String msg) {
        btnCodeLoginRegister.setText(msg);
    }

    @Override
    public String getPassword() {
        return view2String(etPasswordLoginRegister);
    }

    @Override
    public String getPassword2() {
        return view2String(etPassword2LoginRegister);
    }

    @Override
    public void codeClickable(boolean clickable) {
        Button button = btnCodeLoginRegister;
        button.setClickable(clickable);
        if (clickable) {
            button.setBackgroundResource(R.drawable.btn_down);
        } else {
            button.setBackgroundResource(R.drawable.btn_unclick);
        }
    }

    @Override
    public String check() {
        return SystemUtil.check(getUsername(), getCode(), getPassword(), getPassword2());
    }

    @Override
    public void loading(boolean loading) {
         progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在注册");
        progressDialog.setTitle("注册");
        if (loading) {
            progressDialog.show();
        } else {
            progressDialog.cancel();
        }
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(layoutLogin, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void countDown() {
        codeClickable(false);
        timer = new CountDownTimer(COUNT_DOWN_SECOND * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setCode(millisUntilFinished / 1000 + "秒后可重发");
            }

            @Override
            public void onFinish() {
                codeClickable(true);
                setCode("获取验证码");
            }
        };
        timer.start();

    }

    @Override
    public void send() {
        new LoginPresenter(this).send();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (timer != null) {
            timer.cancel();
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(LOGIN_COUNT_DOWN_TIME, SystemUtil.readTime(btnCodeLoginRegister));
        editor.putLong(CURRENT_TIME, System.currentTimeMillis());
        editor.apply();
    }

    public void isCountDown() {
        long remainingTime = sp.getLong(LOGIN_COUNT_DOWN_TIME, 0);
        final Button button = btnCodeLoginRegister;
        long restTime = (remainingTime - ((System.currentTimeMillis() - sp.getLong(CURRENT_TIME, 0)) / 1000));
        if (restTime > 0) {
            timer = new CountDownTimer(restTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    codeClickable(false);
                    button.setText(millisUntilFinished / 1000 + "秒后可重发");
                }

                @Override
                public void onFinish() {
                    codeClickable(true);
                    button.setText("获取验证码");
                }
            };
            timer.start();
        }
    }
}
