package nexuslink.charon.jim.biz;


import nexuslink.charon.jim.listener.register.OnForgetListener;
import nexuslink.charon.jim.listener.register.OnLoginListener;
import nexuslink.charon.jim.listener.register.OnLogonListener;


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
    void login(String username, String password, OnLoginListener listener);

    void logon(String username, String password, OnLogonListener listener);

    void forget(String username, String password, OnForgetListener listener);
}
