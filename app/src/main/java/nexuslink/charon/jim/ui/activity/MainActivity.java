package nexuslink.charon.jim.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.model.RegisterModel;
import nexuslink.charon.jim.ui.fragment.BaseFragment;
import nexuslink.charon.jim.ui.fragment.main.GroupFragment;
import nexuslink.charon.jim.ui.fragment.main.MeFragment;
import nexuslink.charon.jim.ui.fragment.main.SmsFragment;

import static nexuslink.charon.jim.Constant.HAVE_USER;
import static nexuslink.charon.jim.Constant.KEY_USER;
import static nexuslink.charon.jim.Constant.USER;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BaseFragment currentNavFragment;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RegisterModel user;
    private ImageView ivHeadNav;
    private TextView tvNameNav,tvSubTitleNav;
    private BottomNavigationView bnvCheck;

    private List<BaseFragment> fragmentNavPool = Arrays.asList(SmsFragment.getInstance(), GroupFragment.getInstance(), MeFragment.getInstance());
    private SharedPreferences sp;
    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initUser();
        setDefaultFragment();

        bnvCheck.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_nav_sms:
                        switchFragment(currentNavFragment,fragmentNavPool.get(0));
                        currentNavFragment = fragmentNavPool.get(0);
                        toolbar.setTitle("消息");
                        break;
                    case R.id.menu_nav_group:
                        switchFragment(currentNavFragment,fragmentNavPool.get(1));
                        currentNavFragment = fragmentNavPool.get(1);
                        toolbar.setTitle("群组");
                        break;
                    case R.id.menu_nav_me:
                        switchFragment(currentNavFragment,fragmentNavPool.get(2));
                        currentNavFragment = fragmentNavPool.get(2);
                        toolbar.setTitle("我的");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }



    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.content_main, currentNavFragment);
        transaction.commit();
        toolbar.setTitle("消息");
    }

    private void switchFragment(BaseFragment from, BaseFragment to) {
        if (!to.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, to).commit();
        }
        getSupportFragmentManager().beginTransaction().hide(from).show(to).commit();

    }

    private void initUser() {
        tvNameNav.setText(user.getUsername());
        tvSubTitleNav.setText("这个家伙很懒");
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        bnvCheck = (BottomNavigationView) findViewById(R.id.bnv_main);

        View view = navigationView.getHeaderView(0);
        ivHeadNav = (ImageView) view.findViewById(R.id.iv_head_nav);
        tvNameNav = (TextView) view.findViewById(R.id.tv_name_nav);
        tvSubTitleNav = (TextView) view.findViewById(R.id.tv_sub_title_nav);
    }

    @Override
    protected void initData() {
        user = (RegisterModel) getIntent().getSerializableExtra(KEY_USER);
        //设置首页fragment
        currentNavFragment = fragmentNavPool.get(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_sms_add) {
            Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(MainActivity.this, AddedFriendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            sp = getSharedPreferences(USER, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(HAVE_USER, false);
            editor.apply();
            JMessageClient.logout();
            //判断是否退出
            finish();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
