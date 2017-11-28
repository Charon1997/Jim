package nexuslink.charon.jim.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.api.BasicCallback;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.adapter.AddedRvAdapter;
import nexuslink.charon.jim.model.AddedBean;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/24 13:37
 * 修改人：Charon
 * 修改时间：2017/11/24 13:37
 * 修改备注：
 */

public class AddedFriendActivity extends BaseActivity {
    @BindView(R.id.added_friend_toolbar)
    Toolbar addedFriendToolbar;
    @BindView(R.id.rv_added_friend)
    RecyclerView rvAddedFriend;

    static String reason;
    static String fromUsername;
    static String appkey;


    static ArrayList<AddedBean> addedList = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.activity_added_friend;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {
        JMessageClient.registerEventReceiver(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvAddedFriend.setLayoutManager(manager);
        rvAddedFriend.setItemAnimator(new DefaultItemAnimator());
        AddedRvAdapter adapter = new AddedRvAdapter(addedList);
        adapter.setOnAddedListener(new AddedRvAdapter.OnAddedListener() {
            @Override
            public void onPass(final int position) {
                ContactManager.acceptInvitation(fromUsername, null, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            addedList.remove(position);
                            Toast.makeText(AddedFriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddedFriendActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onDeclined(final int position) {
                ContactManager.declineInvitation(fromUsername, null, "sb吧", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            addedList.remove(position);
                            Toast.makeText(AddedFriendActivity.this, "拒绝成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddedFriendActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        rvAddedFriend.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(addedFriendToolbar);
        getSupportActionBar().setTitle("加好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addedFriendToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    @Override
    public void onEvent(ContactNotifyEvent event) {
        reason = event.getReason();
        fromUsername = event.getFromUsername();
        appkey = event.getfromUserAppKey();
        Log.d("tag", "onPass: reason "+reason+"appkey"+appkey+"username"+ fromUsername);
        switch (event.getType()) {
            case invite_received://收到好友邀请
                //...

                //addedList.add(new AddedBean(null, fromUsername, reason));
                rvAddedFriend.notify();
                break;
            case invite_accepted://对方接收了你的好友邀请
                //...
                break;
            case invite_declined://对方拒绝了你的好友邀请
                //...
                break;
            case contact_deleted://对方将你从好友中删除
                //...
                break;
            default:
                break;
        }
    }
}
