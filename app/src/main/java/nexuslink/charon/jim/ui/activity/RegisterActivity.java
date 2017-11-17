package nexuslink.charon.jim.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.adapter.RegisterPagerAdapter;
import nexuslink.charon.jim.contract.RegisterContract;

/**
 * 项目名称：Jim
 * 类描述：登录界面
 * 创建人：Charon
 * 创建时间：2017/11/17 11:26
 * 修改人：Charon
 * 修改时间：2017/11/17 11:26
 * 修改备注：
 */

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    @BindView(R.id.viewpager_register)
    UltraViewPager viewpagerRegister;
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
    @BindView(R.id.tv_right_logon_register)
    TextView tvRightLogonRegister;
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
    private ArrayList<View> viewList = new ArrayList<>();
    private RegisterPagerAdapter adapter;
    //private String[] titles = {"注册","登录","忘记密码"};

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        PermissionGen.with(RegisterActivity.this).addRequestCode(100)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.READ_PHONE_STATE).request();
        initView();
    }

    private void initView() {

        View loginView = LayoutInflater.from(this).inflate(R.layout.fragment_login_register, null);
        View logonView = LayoutInflater.from(this).inflate(R.layout.fragment_logon_register, null);
        View forgetView = LayoutInflater.from(this).inflate(R.layout.fragment_forget_register, null);

        viewList.add(loginView);
        viewList.add(logonView);
        viewList.add(forgetView);

        viewpagerRegister.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new RegisterPagerAdapter(viewList);
        viewpagerRegister.setAdapter(adapter);

        viewpagerRegister.initIndicator();
        viewpagerRegister.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(0x9A03A9F4)
                .setNormalColor(0x9Affffff)
                .setRadius(10);
        viewpagerRegister.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        viewpagerRegister.getIndicator().build();

        viewpagerRegister.setCurrentItem(1);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        Toast.makeText(this, "已经获取权限", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loading(boolean loading, int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    @Override
    public String getLogonUsername() {
        return view2String(etUsernameLogonRegister);
    }

    @Override
    public String getLogonPassword() {
        return view2String(etPasswordLogonRegister);
    }

    @Override
    public String getLoginUsername() {
        return view2String(etUsernameLoginRegister);
    }

    @Override
    public String getLoginCode() {
        return view2String(etCodeLoginRegister);
    }

    @Override
    public String getLoginPassword() {
        return view2String(etPasswordLoginRegister);
    }

    @Override
    public String getLoginPassword2() {
        return view2String(etPassword2LoginRegister);
    }

    @Override
    public String getForgetUsername() {
        return view2String(etUsernameForgetRegister);
    }

    @Override
    public String getForgetCode() {
        return view2String(etCodeForgetRegister);
    }

    @Override
    public String getForgetPassword() {
        return view2String(etPasswordForgetRegister);
    }

    @Override
    public String getForgetPassword2() {
        return view2String(etPassword2ForgetRegister);
    }

    @Override
    public String checkLogon() {
        return null;
    }

    @Override
    public String checkLogin() {
        return null;
    }

    @Override
    public String checkForget() {
        return null;
    }

    private String check(String username,String code,String password,String password2) {
        if (username == null || "".equals(username) || username.length() != 11) {
            return "手机输入错误";
        }
        return null;
    }

    private String view2String(EditText e) {
        return e.getText().toString().trim();
    }

    @OnClick({R.id.btn_code_login_register, R.id.tv_right_logon_register,
            R.id.btn_send_logon_register, R.id.tv_to_login_logon_register, R.id.tv_to_forget_logon_register
    ,R.id.btn_code_forget_register, R.id.btn_send_forget_register, R.id.tv_left_forget_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code_login_register:
                //注册验证码
                break;
            case R.id.tv_right_logon_register:
                viewpagerRegister.scrollNextPage();
                break;
            case R.id.btn_send_logon_register:
                break;
            case R.id.tv_to_login_logon_register:
                viewpagerRegister.setCurrentItem(0,true);
                break;
            case R.id.tv_to_forget_logon_register:
                viewpagerRegister.scrollNextPage();
                break;
            case R.id.btn_code_forget_register:
                //忘记密码验证码
                break;
            case R.id.btn_send_forget_register:
                //忘记密码登录
                break;
            case R.id.tv_left_forget_register:
                viewpagerRegister.setCurrentItem(1,true);
                break;
            default:
                break;
        }
    }
}
