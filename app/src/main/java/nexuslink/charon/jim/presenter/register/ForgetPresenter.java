package nexuslink.charon.jim.presenter.register;

import nexuslink.charon.jim.biz.IRegisterBiz;
import nexuslink.charon.jim.biz.RegisterBiz;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.register.OnForgetListener;
import nexuslink.charon.jim.model.RegisterModel;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 20:43
 * 修改人：Charon
 * 修改时间：2017/11/21 20:43
 * 修改备注：
 */

public class ForgetPresenter implements RegisterContract.Presenter.Forget {
    private RegisterModel model;
    private RegisterContract.View.Forget view;
    private IRegisterBiz biz = new RegisterBiz();

    public ForgetPresenter(RegisterContract.View.Forget view) {
        this.model = new RegisterModel();
        this.view = view;
    }

    @Override
    public void send() {
        biz.forget(view.getUsername(),  view.getPassword()
                , new OnForgetListener() {
                    @Override
                    public void success(RegisterModel user) {
                        //修改数据库中的账号密码
                        view.showError("用户" + user.getUsername() + "修改成功");
                        view.loading(false);
                    }

                    @Override
                    public void failed() {
                        view.showError("修改失败");
                        view.loading(false);
                    }
                });
    }
}
