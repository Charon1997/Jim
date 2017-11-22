package nexuslink.charon.jim.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.adapter.RegisterPagerAdapter;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnViewPagerScroll;
import nexuslink.charon.jim.ui.fragment.ForgetFragment;
import nexuslink.charon.jim.ui.fragment.LoginFragment;
import nexuslink.charon.jim.ui.fragment.LogonFragment;


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
    private EventHandler eventHandler;

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

        initSMS();


    }

    private void initSMS() {
        SMSSDK.setAskPermisionOnReadContact(true);
        eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int i, int i1, Object o) {
                int current = viewpagerMain.getCurrentItem();
                if (o instanceof Throwable){
                    Throwable throwable = (Throwable) o;
                    String msg = throwable.getMessage();
                    if (current == 0) {
                        loginFragment.showError(msg.substring(24,msg.length()-2 ));
                    } else {
                        forgetFragment.showError(msg.substring(24,msg.length()-2 ));
                    }
                } else {
                    if (i1 == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Log.d("TAG", "验证成功");
                            if (current == 0) {
                                loginFragment.send();
                            } else {
                                forgetFragment.send();
                            }
                        } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            if (current == 0) {
                                loginFragment.showError("验证码已发送");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginFragment.countDown();
                                    }
                                });

                            } else {
                                forgetFragment.showError("验证码已发送");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        forgetFragment.countDown();
                                    }
                                });

                            }
                        } else if (i == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                            //语音
                        } else if (i == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
