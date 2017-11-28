package nexuslink.charon.jim.ui.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import nexuslink.charon.jim.R;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/24 13:04
 * 修改人：Charon
 * 修改时间：2017/11/24 13:04
 * 修改备注：
 */

public class ContactNotifyEventReceiver extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
    }
    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }
    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();

        //存数据库
        switch (event.getType()) {
            case invite_received://收到好友邀请
                //...

                break;
            case invite_accepted://对方接收了你的好友邀请
                //...
                notification(null,fromUsername+"接收了你的好友请求");
                break;
            case invite_declined://对方拒绝了你的好友邀请
                //...
                notification(null,fromUsername+"拒绝了你的好友请求，并说：“"+reason+"“");
                break;
            case contact_deleted://对方将你从好友中删除
                //...
                notification(null,"喔噢，"+fromUsername+"把你删除咯");
                break;
            default:
                break;
        }
    }

    private void notification(Class activityClass,String msg) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Jim")
                        .setContentText(msg)
                        .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6"))
                        .setVibrate(new long[] {1000,200,1000})
                        ;
        if (activityClass != null) {
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this,activityClass ), 0);
            mBuilder.setContentIntent(contentIntent);
        }

        notificationManager.notify(0, mBuilder.build());

    }
}
