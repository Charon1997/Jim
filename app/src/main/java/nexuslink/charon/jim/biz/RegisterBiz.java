package nexuslink.charon.jim.biz;

import android.os.CountDownTimer;

import nexuslink.charon.jim.listener.OnClickableListener;

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

public class RegisterBiz implements IRegisterBiz{

    @Override
    public void login(String username, String code, String password, String password2) {

    }

    @Override
    public void logon(String username, String password) {

    }

    @Override
    public void forget(String username, String code, String password, String password2) {

    }

    @Override
    public void getCode(String username, final OnClickableListener listener) {
        CountDownTimer timer = new CountDownTimer(COUNT_DOWN_SECOND * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                listener.cannotClick(millisUntilFinished/1000+"秒后可重发");
            }

            @Override
            public void onFinish() {
                listener.canClick();
            }
        };
        timer.start();
    }


}
