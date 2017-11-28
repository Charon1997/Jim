package nexuslink.charon.jim.biz;

import android.os.CountDownTimer;
import android.util.Log;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import nexuslink.charon.jim.listener.register.OnForgetListener;
import nexuslink.charon.jim.listener.register.OnLoginListener;
import nexuslink.charon.jim.listener.register.OnLogonListener;
import nexuslink.charon.jim.model.RegisterModel;

import static nexuslink.charon.jim.Constant.COUNT_DOWN_SECOND;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 15:08
 * 修改人：Charon
 * 修改时间：2017/11/21 15:08
 * 修改备注：
 */

public class RegisterBiz implements IRegisterBiz {

    @Override
    public void login(final String username, final String password, final OnLoginListener listener) {
        JMessageClient.register(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.d("TAG", "gotResult: "+i+"s"+s);
                if (i == 0) {
                    listener.success(new RegisterModel(username,password));
                } else {
                    listener.failed(s);
                }
            }
        });
    }

    @Override
    public void logon(final String username, final String password, final OnLogonListener listener) {
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.d("TAG", "gotResult: "+i+"s"+s);
                if (i == 0) {
                    listener.success(new RegisterModel(username,password));
                } else {
                    listener.failed(s);
                }
            }
        });
    }

    @Override
    public void forget(String username, String password, OnForgetListener listener) {
        //需要更新前密码
        //JMessageClient.updateUserPassword();
    }



}
