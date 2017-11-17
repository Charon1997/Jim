package nexuslink.charon.jim.contract;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/17 11:24
 * 修改人：Charon
 * 修改时间：2017/11/17 11:24
 * 修改备注：
 */

public interface RegisterContract {
    interface Model {
    }

    interface View {
        void loading(boolean loading,int position);

        String getLogonUsername();

        String getLogonPassword();

        String getLoginUsername();

        String getLoginCode();

        String getLoginPassword();

        String getLoginPassword2();

        String getForgetUsername();

        String getForgetCode();

        String getForgetPassword();

        String getForgetPassword2();

        String checkLogon();

        String checkLogin();

        String checkForget();
    }

    interface Presenter {
        void getForgetCode();

        void getLoginCode();

        void login();

        void logon();

        void forget();
    }
}
