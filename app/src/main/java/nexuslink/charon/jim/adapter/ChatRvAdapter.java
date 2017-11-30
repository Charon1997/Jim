package nexuslink.charon.jim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.jpush.im.android.api.model.Message;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.model.ChatModel;

import static nexuslink.charon.jim.Constant.IMAGE;
import static nexuslink.charon.jim.Constant.TEXT;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/29 15:53
 * 修改人：Charon
 * 修改时间：2017/11/29 15:53
 * 修改备注：
 */

public class ChatRvAdapter extends RecyclerView.Adapter {
    private static final String TAG = ChatRvAdapter.class.getSimpleName();

    private List<ChatModel> chatList;
    private Context mContext;

    private int LEFT_MESSAGE = 0x00;
    private int RIGHT_MESSAGE = 0x01;

    public ChatRvAdapter(List<ChatModel> chatList, Context mContext) {
        Log.d(TAG, "ChatRvAdapter");
        this.chatList = chatList;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == LEFT_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_chat_rv, null);
            Log.d(TAG, "onCreateViewHolder-->left");
            return new LeftViewHolder(view);
        } else if (viewType == RIGHT_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_chat_rv, null);
            Log.d(TAG, "onCreateViewHolder-->right");
            return new RightViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder"+position);
        if (holder instanceof LeftViewHolder) {
            switch (chatList.get(position).getType()) {
                case TEXT:
                    ((LeftViewHolder) holder).showText();
                    //// TODO: 2017/11/29 判断头像 
                    ((LeftViewHolder) holder).mTvMessage.setText(chatList.get(position).getMessageText());
                    break;
                case IMAGE:
                    ((LeftViewHolder) holder).showImage();
                    //// TODO: 2017/11/29 判断头像
                    //Glide.with(mContext).load()
                    break;
                default:
                    break;
            }
        } else {
            switch (chatList.get(position).getType()) {
                case TEXT:
                    ((RightViewHolder) holder).showText();
                    //// TODO: 2017/11/29 判断头像
                    ((RightViewHolder) holder).mTvMessage.setText(chatList.get(position).getMessageText());
                    break;
                case IMAGE:
                    ((RightViewHolder) holder).showImage();
                    //// TODO: 2017/11/29 判断头像
                    //Glide.with(mContext).load()
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (isMyMessage(position)) {
            Log.d(TAG, "getItemViewType: --->right"+position);
            return RIGHT_MESSAGE;
        } else {
            Log.d(TAG, "getItemViewType: --->left"+position);
            return LEFT_MESSAGE;
        }
    }

    public boolean isMyMessage(int position) {
        return chatList.get(position).isMyMessage();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class LeftViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvAvatar, mIvMessage;
        private TextView mTvMessage;

        public LeftViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_head_left_chat);
            mIvMessage = (ImageView) itemView.findViewById(R.id.iv_msg_left_chat);
            mTvMessage = (TextView) itemView.findViewById(R.id.tv_msg_left_chat);
        }

        public void showText() {
            mIvMessage.setVisibility(View.GONE);
            mTvMessage.setVisibility(View.VISIBLE);
        }

        public void showImage() {
            mIvMessage.setVisibility(View.VISIBLE);
            mTvMessage.setVisibility(View.GONE);
        }


    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvAvatar, mIvMessage;
        private TextView mTvMessage;

        public RightViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_head_right_chat);
            mIvMessage = (ImageView) itemView.findViewById(R.id.iv_msg_right_chat);
            mTvMessage = (TextView) itemView.findViewById(R.id.tv_msg_right_chat);
        }

        public void showText() {
            mIvMessage.setVisibility(View.GONE);
            mTvMessage.setVisibility(View.VISIBLE);
        }

        public void showImage() {
            mIvMessage.setVisibility(View.VISIBLE);
            mTvMessage.setVisibility(View.GONE);
        }
    }


}
