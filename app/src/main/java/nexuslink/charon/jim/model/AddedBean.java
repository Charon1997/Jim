package nexuslink.charon.jim.model;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/24 13:57
 * 修改人：Charon
 * 修改时间：2017/11/24 13:57
 * 修改备注：
 */

public class AddedBean {
    private String avatar;
    private String name;
    private String reason;
    private boolean isHandle;

    public boolean isHandle() {
        return isHandle;
    }

    public void setHandle(boolean handle) {
        isHandle = handle;
    }

    public AddedBean(String avatar, String name, String reason, boolean isHandle) {
        this.avatar = avatar;
        this.name = name;
        this.reason = reason;
        this.isHandle = isHandle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
