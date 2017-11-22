package nexuslink.charon.jim.contract;

import nexuslink.charon.jim.model.RegisterModel;

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

            void countDown();

            void send();
        }

        interface Logon {
            String getUsername();

            String getPassword();

            String check();

            void loading(boolean loading);

            void showError(String msg);

            void success(RegisterModel user);
        }

        interface Login {
            String getUsername();

            String getCode();

            void setCode(String msg);

            String getPassword();

            String getPassword2();

            void codeClickable(boolean clickable);

            String check();

            void loading(boolean loading);

            void showError(String msg);

            void countDown();

            void send();

        }

    }

    interface Presenter {

        interface Forget {
            void send();
        }

        interface Login {
            void send();
        }

        interface Logon {
            void send();
        }
    }
}
