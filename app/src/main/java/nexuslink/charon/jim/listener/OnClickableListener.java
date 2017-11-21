package nexuslink.charon.jim.listener;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/21 14:29
 * 修改人：Charon
 * 修改时间：2017/11/21 14:29
 * 修改备注：
 */

public interface OnClickableListener {
    void canClick();

    void cannotClick(String information);
}
