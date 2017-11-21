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
        interface Forget {
            String getUsername();

            String getCode();

            void setCode(String msg);

            String getPassword();

            String getPassword2();

            void codeClickable(boolean clickable);

            String check();

            void loading(boolean loading);

            void showError(String msg);
        }

        interface Logon {
            String getLogonUsername();

            String getLogonPassword();

            String checkLogon();

            void loading(boolean loading);

            void showError(String msg);
        }

        interface Login {
            String getLoginUsername();

            String getLoginCode();

            void setLoginCode(String msg);

            String getLoginPassword();

            String getLoginPassword2();

            void loginCodeClickable(boolean clickable);

            String checkLogin();

            void loading(boolean loading);

            void showError(String msg);
        }

    }

    interface Presenter {
        
        interface Forget {
            void getCode();
            void send();
        }

        interface Login {
            void getCode();
            void send();
        }
        
        interface Logon{
            void send();
        }
    }
}
