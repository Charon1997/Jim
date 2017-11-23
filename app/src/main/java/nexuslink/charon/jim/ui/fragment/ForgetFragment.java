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
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.SMSSDK;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnViewPagerScroll;
import nexuslink.charon.jim.presenter.register.ForgetPresenter;
import nexuslink.charon.jim.ui.activity.RegisterActivity;
import nexuslink.charon.jim.utils.SystemUtil;

import static nexuslink.charon.jim.Constant.COUNT_DOWN_SECOND;
import static nexuslink.charon.jim.Constant.CURRENT_TIME;
import static nexuslink.charon.jim.Constant.FORGET_COUNT_DOWN_TIME;
import static nexuslink.charon.jim.Constant.PHONE_LENGTH;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:27
 * 修改人：Charon
 * 修改时间：2017/11/21 20:27
 * 修改备注：
 */

public class ForgetFragment extends BaseFragment implements RegisterContract.View.Forget {
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

    private SharedPreferences sp;
    private CountDownTimer timer;
    private ProgressDialog progressDialog;
    private OnViewPagerScroll scroll;

    private static final String TAG = ForgetFragment.class.getSimpleName();

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences(FORGET_COUNT_DOWN_TIME, Context.MODE_PRIVATE);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_forget_register, null);
        unbinder = ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_code_forget_register, R.id.btn_send_forget_register
            , R.id.tv_left_forget_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code_forget_register:
                SystemUtil.hideSoftKeyboard((InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE), getActivity().getWindow());

                if (getUsername().length() == PHONE_LENGTH) {
                    SMSSDK.getVerificationCode("86", getUsername());
                } else {
                    showError("请输入正确的手机号");
                }
                break;
            case R.id.btn_send_forget_register:
                SystemUtil.hideSoftKeyboard((InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE), getActivity().getWindow());
                //验证验证码
                if (check().equals("正确")) {
                    //验证
                    SMSSDK.submitVerificationCode("86", getUsername(), getCode());
                } else {
                    showError(check());
                }
                //presenter.send();
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
        super.loading(loading, "忘记密码", "正在修改", 2);
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(layoutForget, msg, Snackbar.LENGTH_SHORT).show();
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

    private String view2String(EditText et) {
        return et.getText().toString().trim();
    }

    @Override
    public void send() {
        new ForgetPresenter(this).send();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        isCountDown();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (timer != null) {
            timer.cancel();
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(FORGET_COUNT_DOWN_TIME, SystemUtil.readTime(btnCodeForgetRegister));
        editor.putLong(CURRENT_TIME, System.currentTimeMillis());
        editor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    public void isCountDown() {
        long remainingTime = sp.getLong(FORGET_COUNT_DOWN_TIME, 0);
        final Button button = btnCodeForgetRegister;
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
