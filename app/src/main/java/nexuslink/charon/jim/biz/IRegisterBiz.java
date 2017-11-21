package nexuslink.charon.jim.biz;

import android.view.View;

import nexuslink.charon.jim.listener.OnClickableListener;


/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 15:08
 * 修改人：Charon
 * 修改时间：2017/11/21 15:08
 * 修改备注：
 */

public interface IRegisterBiz {
    void login(String username, String code, String password, String password2);

    void logon(String username, String password);

    void forget(String username, String code, String password, String password2);

    void getCode(String username, OnClickableListener listener);
}
