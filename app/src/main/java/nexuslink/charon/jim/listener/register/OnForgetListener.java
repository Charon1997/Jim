package nexuslink.charon.jim.listener.register;

import nexuslink.charon.jim.model.RegisterModel;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/22 14:55
 * 修改人：Charon
 * 修改时间：2017/11/22 14:55
 * 修改备注：
 */

public interface OnForgetListener {
    void success(RegisterModel user);

    void failed();
}
