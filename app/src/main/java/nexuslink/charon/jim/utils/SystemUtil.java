package nexuslink.charon.jim.utils;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import nexuslink.charon.jim.model.RegisterModel;

import static nexuslink.charon.jim.Constant.CODE_LENGTH;
import static nexuslink.charon.jim.Constant.PASSWORD_MAX;
import static nexuslink.charon.jim.Constant.PASSWORD_MIX;
import static nexuslink.charon.jim.Constant.PHONE_LENGTH;


/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 22:38
 * 修改人：Charon
 * 修改时间：2017/11/21 22:38
 * 修改备注：
 */

public class SystemUtil {
    public static void hideSoftKeyboard(InputMethodManager manager, Window window) {
        InputMethodManager imm = manager;
        if (imm != null) {
            imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
        }
    }

    public static String check(String username, String code, String password, String password2) {
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

    public static String check(RegisterModel user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (username == null || "".equals(username) || username.length() != PHONE_LENGTH) {
            return "请输入正确的手机号";
        } else if (password == null || "".equals(password) || password.length() > PASSWORD_MAX || password.length() < PASSWORD_MIX) {
            return "密码位数应在" + PASSWORD_MIX + "-" + PASSWORD_MAX + "之间";
        } else {
            return "正确";
        }
    }

    public static long readTime(Button button) {
        long time = 0;
        try {
            time = Long.parseLong(button.getText().toString().substring(0, button.getText().length() - 5));
        } catch (NumberFormatException e) {
            Log.e("TAG", "readTime: " + e);
        }
        return time;
    }

    public static String et2String(EditText et) {
        return et.getText().toString().trim();
    }

    //震动milliseconds毫秒
    public static void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
    //以pattern[]方式震动
    public static void vibrate(final Activity activity, long[] pattern,int repeat){
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern,repeat);
    }
    //取消震动
    public static void virateCancle(final Activity activity){
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}
