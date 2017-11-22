package nexuslink.charon.jim.presenter.register;

import nexuslink.charon.jim.biz.IRegisterBiz;
import nexuslink.charon.jim.biz.RegisterBiz;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnLoginListener;
import nexuslink.charon.jim.model.RegisterModel;

import static nexuslink.charon.jim.Constant.PHONE_LENGTH;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/22 13:30
 * 修改人：Charon
 * 修改时间：2017/11/22 13:30
 * 修改备注：
 */

public class LoginPresenter implements RegisterContract.Presenter.Login {
    private RegisterModel model;
    private RegisterContract.View.Login view;
    private IRegisterBiz biz;

    public LoginPresenter(RegisterContract.View.Login view) {
        this.view = view;
        biz = new RegisterBiz();
    }


    @Override
    public void send() {
        view.loading(true);
        biz.login(view.getUsername(), view.getPassword()
                , new OnLoginListener() {
                    @Override
                    public void success(RegisterModel user) {
                        //修改数据库中的账号密码
                        view.showError("注册成功");
                        view.loading(false);
                    }

                    @Override
                    public void failed(String msg) {
                        view.showError("注册失败"+msg);
                        view.loading(false);
                    }
                });

    }
}
