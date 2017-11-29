package nexuslink.charon.jim.model;


import cn.jpush.im.android.api.model.*;
import cn.jpush.im.android.api.model.UserInfo;
import nexuslink.charon.jim.contract.ChatContract;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/29 15:57
 * 修改人：Charon
 * 修改时间：2017/11/29 15:57
 * 修改备注：
 */

public class ChatModel implements ChatContract.Model {
    private cn.jpush.im.android.api.model.UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private String messageText;
    private String messageImage;
    private int type;
    private long createTime;

    public boolean isMyMessage() {
        return myMessage;
    }

    public void setMyMessage(boolean myMessage) {
        this.myMessage = myMessage;
    }

    private boolean myMessage;

    public ChatModel() {

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
