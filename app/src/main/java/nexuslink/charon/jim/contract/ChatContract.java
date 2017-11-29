package nexuslink.charon.jim.contract;

import android.widget.EditText;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/29 15:57
 * 修改人：Charon
 * 修改时间：2017/11/29 15:57
 * 修改备注：
 */

public interface ChatContract {
    interface Model {
    }

    interface View {
        String getEditText();
    }

    interface Presenter {
        void sendMessage(String msg);
    }
}
