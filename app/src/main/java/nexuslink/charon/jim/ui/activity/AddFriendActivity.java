package nexuslink.charon.jim.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.utils.SystemUtil;

import static nexuslink.charon.jim.Constant.PHONE_LENGTH;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/24 11:46
 * 修改人：Charon
 * 修改时间：2017/11/24 11:46
 * 修改备注：
 */

public class AddFriendActivity extends BaseActivity {
    @BindView(R.id.add_friend_toolbar)
    Toolbar addFriendToolbar;
    @BindView(R.id.et_username_add_friend)
    EditText etUsernameAddFriend;
    @BindView(R.id.et_reason_add_friend)
    EditText etReasonAddFriend;

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(addFriendToolbar);
        getSupportActionBar().setTitle("加好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addFriendToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:
                if (check()) {
                    String username = SystemUtil.et2String(etUsernameAddFriend);
                    String reason = SystemUtil.et2String(etReasonAddFriend);
                    ContactManager.sendInvitationRequest(username, null, reason, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                showMsg("发送成功");
                                finish();
                            } else {
                                showMsg(s);
                            }
                        }
                    });
                } else {
                    showMsg("请输入正确的Jim号");
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean check() {
        String username = etUsernameAddFriend.getText().toString().trim();
        return username.length() == PHONE_LENGTH;
    }

    private void showMsg(String msg) {
        Snackbar.make(addFriendToolbar,msg,Snackbar.LENGTH_SHORT).show();
    }

}
