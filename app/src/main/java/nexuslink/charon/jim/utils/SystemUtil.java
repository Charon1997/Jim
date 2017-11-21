package nexuslink.charon.jim.utils;

import android.view.Window;
import android.view.inputmethod.InputMethodManager;


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
    public static void hideSoftKeyboard(InputMethodManager manager,Window window) {
        InputMethodManager imm =  manager;
        if (imm != null) {
            imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
        }
    }
}
