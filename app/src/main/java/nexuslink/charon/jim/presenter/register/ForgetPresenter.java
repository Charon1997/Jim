package nexuslink.charon.jim.presenter.register;

import nexuslink.charon.jim.biz.IRegisterBiz;
import nexuslink.charon.jim.biz.RegisterBiz;
import nexuslink.charon.jim.contract.RegisterContract;
import nexuslink.charon.jim.listener.OnClickableListener;
import nexuslink.charon.jim.model.RegisterModel;

import static nexuslink.charon.jim.Constant.PHONE_LENGTH;

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
    public void getCode() {
        if (view.getUsername().length() == PHONE_LENGTH){
            view.codeClickable(false);
            biz.getCode(view.getUsername(),
                    new OnClickableListener() {
                        @Override
                        public void canClick() {
                            view.codeClickable(true);
                            view.setCode("获取验证码");
                        }

                        @Override
                        public void cannotClick(String information) {
                            view.codeClickable(true);
                            view.setCode(information);
                        }
                    }
            );
        } else {
            view.showError("请输入正确的手机号");
        }
    }

    @Override
    public void send() {
        view.showError("登录");
    }
}
