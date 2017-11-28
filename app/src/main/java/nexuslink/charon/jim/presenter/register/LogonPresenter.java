package nexuslink.charon.jim.presenter.register;

import nexuslink.charon.jim.biz.IRegisterBiz;
import nexuslink.charon.jim.biz.RegisterBiz;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnLogonListener;
import nexuslink.charon.jim.model.RegisterModel;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/22 14:26
 * 修改人：Charon
 * 修改时间：2017/11/22 14:26
 * 修改备注：
 */

public class LogonPresenter implements RegisterContract.Presenter.Logon {
    private RegisterModel model;
    private IRegisterBiz biz;
    private RegisterContract.View.Logon view;

    public LogonPresenter(RegisterContract.View.Logon view) {
        this.view = view;
        this.biz = new RegisterBiz();
    }

    @Override
    public void send() {

        String msg = view.check();
        if ("正确".equals(msg)) {
            view.loading(true);
            biz.logon(view.getUsername(), view.getPassword(), new OnLogonListener() {
                @Override
                public void success(RegisterModel user) {
                    //修改数据库中的账号密码

                    view.success(user);
                    view.showError("登录成功");
                    view.loading(false);
                }

                @Override
                public void failed(String msg) {
                    view.showError("登录失败" + msg);
                    view.loading(false);
                }
            });

        } else {
            view.showError(msg);
        }
    }
}
