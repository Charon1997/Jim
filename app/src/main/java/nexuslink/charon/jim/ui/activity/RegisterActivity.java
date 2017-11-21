package nexuslink.charon.jim.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.adapter.RegisterPagerAdapter;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.OnViewPagerScroll;
import nexuslink.charon.jim.ui.fragment.ForgetFragment;
import nexuslink.charon.jim.ui.fragment.LoginFragment;
import nexuslink.charon.jim.ui.fragment.LogonFragment;

import static nexuslink.charon.jim.Constant.CODE_LENGTH;
import static nexuslink.charon.jim.Constant.PASSWORD_MAX;
import static nexuslink.charon.jim.Constant.PASSWORD_MIX;
import static nexuslink.charon.jim.Constant.PHONE_LENGTH;

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


    @BindView(R.id.viewpager_main)
    UltraViewPager viewpagerMain;
    private ArrayList<Fragment> viewList = new ArrayList<>();
    private RegisterPagerAdapter adapter;

    SharedPreferences sp;

    LoginFragment loginFragment = new LoginFragment();
    LogonFragment logonFragment = new LogonFragment();
    ForgetFragment forgetFragment =ForgetFragment.getInstance();

    private static RegisterActivity activity;


    public static RegisterActivity getInstance(){
        if (activity == null) {
            activity = new RegisterActivity();
        }
        return activity;
    }

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

        sp = getSharedPreferences("register", MODE_PRIVATE);
    }

    private void initView() {
        loginFragment.setOnViewPagerScroll(new OnViewPagerScroll() {
            @Override
            public void leftScroll() {

            }

            @Override
            public void rightScroll() {
                viewpagerMain.scrollNextPage();
            }
        });
        logonFragment.setOnViewPagerScroll(new OnViewPagerScroll() {
            @Override
            public void leftScroll() {
                viewpagerMain.setCurrentItem(0,true);
            }

            @Override
            public void rightScroll() {
                viewpagerMain.scrollNextPage();
            }
        });
        forgetFragment.setOnViewPagerScroll(new OnViewPagerScroll() {
            @Override
            public void leftScroll() {
                viewpagerMain.setCurrentItem(1,true);
            }

            @Override
            public void rightScroll() {

            }
        });

        viewList.add(loginFragment);
        viewList.add(logonFragment);
        viewList.add(forgetFragment);

        viewpagerMain.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new RegisterPagerAdapter(getSupportFragmentManager(),viewList);
        viewpagerMain.setAdapter(adapter);

        viewpagerMain.initIndicator();
        viewpagerMain.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(0x9A03A9F4)
                .setNormalColor(0x9Affffff)
                .setRadius(10);
        viewpagerMain.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        viewpagerMain.getIndicator().build();
        viewpagerMain.setCurrentItem(1);

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

    private String check(String username, String code, String password, String password2) {
        if (username == null || "".equals(username) || username.length() != PHONE_LENGTH) {
            return "请输入正确的手机号";
        } else if (code == null || "".equals(code) || code.length() != CODE_LENGTH) {
            return "请输入正确的验证码";
        } else if (password == null || "".equals(password) || password.length() > PASSWORD_MAX || password.length() < PASSWORD_MIX) {
            return "密码位数应在" + PASSWORD_MIX + "-" + PASSWORD_MAX + "之间";
        } else if (password2 == null || "".equals(password2) || password2.length() > PASSWORD_MAX || password2.length() < PASSWORD_MIX) {
            return "密码位数应在" + PASSWORD_MIX + "-" + PASSWORD_MAX + "之间";
        } else if (!password.equals(password2)) {
            return "两个密码不一致";
        } else {
            return "正确";
        }
    }
}
