package nexuslink.charon.jim.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.model.RegisterModel;

import static nexuslink.charon.jim.Constant.HAVE_USER;
import static nexuslink.charon.jim.Constant.KEY_USER;
import static nexuslink.charon.jim.Constant.PASSWORD;
import static nexuslink.charon.jim.Constant.USER;
import static nexuslink.charon.jim.Constant.USERNAME;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/25 16:18
 * 修改人：Charon
 * 修改时间：2017/11/25 16:18
 * 修改备注：
 */

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;
    SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        sp = getSharedPreferences(USER, MODE_PRIVATE);

        showAnimator();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser(sp.getBoolean(HAVE_USER, false));
            }
        }, 1050);

    }

    private void checkUser(boolean have) {
        if (have) {
            String username = sp.getString(USERNAME, "");
            String password = sp.getString(PASSWORD, "");

            toIntent(MainActivity.class, new RegisterModel(username, password));
        } else {
            toIntent(RegisterActivity.class, null);
        }
    }

    private void toIntent(Class mainActivityClass, RegisterModel user) {
        Intent intent = new Intent(WelcomeActivity.this, mainActivityClass);
        if (user != null) {
            intent.putExtra(KEY_USER, user);
        }
        startActivity(intent);
        finish();
    }

    private void showAnimator() {
        ObjectAnimator ob = ObjectAnimator.ofFloat(ivWelcome, "Alpha", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.play(ob);
        set.setDuration(1000);
        set.start();
    }


}
