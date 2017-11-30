package nexuslink.charon.jim.ui.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Message;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.model.AddedBean;
import nexuslink.charon.jim.model.ChatModel;
import nexuslink.charon.jim.utils.SystemUtil;

import static nexuslink.charon.jim.Constant.NICKNAME;
import static nexuslink.charon.jim.Constant.TEXT;
import static nexuslink.charon.jim.Constant.USERNAME;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/23 12:59
 * 修改人：Charon
 * 修改时间：2017/11/23 12:59
 * 修改备注：
 */

public abstract class BaseActivity extends AppCompatActivity {
    private View mContextView = null;
    private static long msgId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = bindView();
        if (view == null) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else {
            mContextView = view;
        }
        JMessageClient.registerEventReceiver(this);
        setContentView(mContextView);
        initData();
        initView();
        doSomething();
    }


    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    protected abstract int bindLayout();

    protected abstract View bindView();

    protected abstract void doSomething();

    protected abstract void initView();

    protected abstract void initData();

    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();

        //存数据库
        switch (event.getType()) {
            case invite_received://收到好友邀请
                //...
                AddedFriendActivity.appkey = appkey;
                AddedFriendActivity.reason = reason;
                AddedFriendActivity.fromUsername = fromUsername;
                AddedFriendActivity.addedList.add(new AddedBean(null, fromUsername, reason, false));
                Log.d("tag", "onPass: reason " + reason + "appkey" + appkey + "username" + fromUsername);
                notification(AddedFriendActivity.class, fromUsername + "请求添加你为好友");
                break;
            case invite_accepted://对方接收了你的好友邀请
                //...
                notification(null, fromUsername + "接收了你的好友请求");
                break;
            case invite_declined://对方拒绝了你的好友邀请
                //...
                notification(null, fromUsername + "拒绝了你的好友请求，并说：“" + reason + "“");
                break;
            case contact_deleted://对方将你从好友中删除
                //...
                notification(null, "喔噢，" + fromUsername + "把你删除咯");
                break;
            default:
                break;
        }
    }

    private void notification(Class activityClass, String msg) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Jim")
                        .setContentText(msg)
                        .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6"))
                        .setVibrate(new long[]{1000, 200, 1000, 200, 1000});
        if (activityClass != null) {
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this, activityClass), PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(contentIntent);
        }

        notificationManager.notify(0, mBuilder.build());

    }

    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();

        if (msgId == msg.getServerMessageId()) {
            return;
        } else {
            msgId = msg.getServerMessageId();
        }
        Log.d("tag", "msg" + msg.toString());
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                ChatModel chat = new ChatModel();
                chat.setUserInfo(msg.getFromUser());
                chat.setMyMessage(false);
                chat.setType(TEXT);
                chat.setCreateTime(msg.getCreateTime());
                chat.setMessageText(textContent.getText());
                Log.d("tag", "onEvent: "+ChatActivity.chatList.get(0).getMessageText());
                //ChatActivity.chatList.add(0, chat);
                Log.d("tag", "onEvent: "+ChatActivity.chatList.get(0).getMessageText());
                ChatActivity.notifyItem(chat);
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                break;
            default:
                break;
        }
    }

    public void onEvent(NotificationClickEvent event) {
        SystemUtil.vibrate(BaseActivity.this,new long[]{300,100,300},-1);
        //通知栏点击事件
        Intent notificationIntent = new Intent(BaseActivity.this, ChatActivity.class);
        Message message = event.getMessage();
        String username = message.getFromUser().getUserName();
        String nickname = message.getFromUser().getNickname();
        notificationIntent.putExtra(USERNAME, username);
        notificationIntent.putExtra(NICKNAME, nickname);
        startActivity(notificationIntent);
    }
}
