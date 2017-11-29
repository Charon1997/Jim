package nexuslink.charon.jim.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.adapter.ChatRvAdapter;
import nexuslink.charon.jim.contract.ChatContract;
import nexuslink.charon.jim.model.ChatModel;
import nexuslink.charon.jim.utils.SystemUtil;

import static cn.jpush.im.android.api.content.EventNotificationContent.EventNotificationType.group_member_added;
import static nexuslink.charon.jim.Constant.IMAGE;
import static nexuslink.charon.jim.Constant.NICKNAME;
import static nexuslink.charon.jim.Constant.TEXT;
import static nexuslink.charon.jim.Constant.USER;
import static nexuslink.charon.jim.Constant.USERNAME;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/28 19:06
 * 修改人：Charon
 * 修改时间：2017/11/28 19:06
 * 修改备注：
 */

public class ChatActivity extends BaseActivity implements ChatContract.View {
    static RecyclerView rvChat;
    @BindView(R.id.et_chat)
    EditText etChat;
    @BindView(R.id.btn_chat)
    Button btnChat;
    @BindView(R.id.toolbar_chat)
    Toolbar toolbarChat;
    private String username;
    private String nickname;

    private Conversation conversation;
    private Message message;

    private SharedPreferences sp;

    //// TODO: 2017/11/29 List的泛型改一改
    static List<ChatModel> chatList = new ArrayList<>();
    private String latestContent;


    @Override
    protected int bindLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected View bindView() {
        return null;
    }

    @Override
    protected void doSomething() {
        initChat();
    }

    private void initChat() {
        //获取单个单聊会话
        conversation = JMessageClient.getSingleConversation(username);
        Log.d("TAG", "conversation" + conversation.toString() + "");
        //// TODO: 2017/11/29 allmessage不一样 
        for (int i = 0,size = conversation.getAllMessage().size();i < size;i++) {
            Message message = conversation.getAllMessage().get(i);
            if (message != null) {
                ChatModel chat = new ChatModel();
                chat.setUserInfo(message.getFromUser());
                chat.setCreateTime(message.getCreateTime());
                switch (message.getContentType()) {
                    case text:
                        TextContent textContent = (TextContent) message.getContent();
                        chat.setMessageText(textContent.getText());
                        chat.setType(TEXT);
                        break;
                    case image:
                        ImageContent imageContent = (ImageContent) message.getContent();
                        imageContent.getLocalPath();
                        chat.setType(IMAGE);
                        break;
                    default:
                        break;
                }
                if (message.getFromUser().getUserName().equals(sp.getString(USERNAME, ""))){
                    //如果是自己发的
                    chat.setMyMessage(true);
                } else {
                    chat.setMyMessage(false);
                }
                chatList.add(chat);
            }
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbarChat);
        if (!"null".equals(nickname) && !"".equals(nickname) && nickname !=null){
            getSupportActionBar().setTitle(nickname);
        } else {
            getSupportActionBar().setTitle(username);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvChat = (RecyclerView) findViewById(R.id.rv_chat);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(manager);
        rvChat.setItemAnimator(new DefaultItemAnimator());
        ChatRvAdapter adapter = new ChatRvAdapter(chatList, this);
        rvChat.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        sp = getSharedPreferences(USER, MODE_PRIVATE);
        Intent intent = getIntent();
        username = intent.getStringExtra(USERNAME);
        nickname = intent.getStringExtra(NICKNAME);
    }


    @OnClick(R.id.btn_chat)
    public void onViewClicked() {
        TextContent content = new TextContent(getEditText());
        message = conversation.createSendMessage(content);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    TextContent textContent = (TextContent) message.getContent();
                    ChatModel chat = new ChatModel();
                    chat.setUserInfo(message.getFromUser());
                    chat.setMyMessage(true);
                    chat.setType(TEXT);
                    chat.setCreateTime(message.getCreateTime());
                    chat.setMessageText(textContent.getText());
                    chatList.add(chat);
                    etChat.setText("");
                    rvChat.notify();
                    Toast.makeText(ChatActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        JMessageClient.sendMessage(message);
    }

    @Override
    public String getEditText() {
        return etChat.getText().toString().trim();
    }
}
