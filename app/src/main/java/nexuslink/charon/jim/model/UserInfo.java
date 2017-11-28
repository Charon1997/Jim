package nexuslink.charon.jim.model;

import com.google.gson.jpush.annotations.Expose;
import com.google.gson.jpush.annotations.SerializedName;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/27 11:45
 * 修改人：Charon
 * 修改时间：2017/11/27 11:45
 * 修改备注：
 */

public class UserInfo {
    @Expose
    @SerializedName("uid")
    protected long userID;
    @Expose
    @SerializedName("username")
    protected String userName;
    @Expose
    protected String nickname = "";
    @Expose
    @SerializedName("avatar")
    protected String avatarMediaID;
    @Expose
    protected String birthday = "";
    @Expose
    protected String signature = "";
    @Expose
    protected String gender = "";
    @Expose
    protected String region = "";
    @Expose
    protected String address = "";
    @Expose
    protected String appkey = null;
    @Expose
    @SerializedName("memo_name")
    protected String notename = null;
    @Expose
    @SerializedName("memo_others")
    protected String noteText = null;
    @Expose
    @SerializedName("mtime")
    protected int mTime;
    protected int isFriend = -1;
    protected int star = -1;
    protected int blacklist = -1;
    protected int noDisturb = -1;
    public UserInfo() {
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarMediaID() {
        return avatarMediaID;
    }

    public void setAvatarMediaID(String avatarMediaID) {
        this.avatarMediaID = avatarMediaID;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getNotename() {
        return notename;
    }

    public void setNotename(String notename) {
        this.notename = notename;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getmTime() {
        return mTime;
    }

    public void setmTime(int mTime) {
        this.mTime = mTime;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(int blacklist) {
        this.blacklist = blacklist;
    }

    public int getNoDisturb() {
        return noDisturb;
    }

    public void setNoDisturb(int noDisturb) {
        this.noDisturb = noDisturb;
    }
}
