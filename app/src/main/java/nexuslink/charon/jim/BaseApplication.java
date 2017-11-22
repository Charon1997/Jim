package nexuslink.charon.jim;

import android.app.Application;

import com.mob.MobApplication;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/17 11:27
 * 修改人：Charon
 * 修改时间：2017/11/17 11:27
 * 修改备注：
 */

public class BaseApplication extends MobApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
