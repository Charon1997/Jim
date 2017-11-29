package nexuslink.charon.jim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import nexuslink.charon.jim.R;
import nexuslink.charon.jim.listener.main.OnItemClickListener;
import nexuslink.charon.jim.model.UserInfo;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/28 14:53
 * 修改人：Charon
 * 修改时间：2017/11/28 14:53
 * 修改备注：
 */

public class FriendRvAdapter extends RecyclerView.Adapter {
    private List<UserInfo> userList;
    private Context mContext;
    private OnItemClickListener listener;

    public FriendRvAdapter(List<UserInfo> userList,Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (this.listener == null) {
            this.listener = listener;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_rv, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        if (!"null".equals(userList.get(position).getAvatarMediaID())){
//            Glide.with(mContext).load(userList.get(position).getAvatarMediaID()).into(((MyViewHolder)holder).mIvHead);
//        }

        String nickName = userList.get(position).getNickname();
        if (!"".equals(nickName) && nickName != null) {
            ((MyViewHolder) holder).mTvName.setText(nickName);
        } else {
            ((MyViewHolder) holder).mTvName.setText(userList.get(position).getUserName());
        }

        ((MyViewHolder)holder).mRlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvHead;
        private TextView mTvName;
        private RelativeLayout mRlView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvHead = (ImageView) itemView.findViewById(R.id.iv_head_friend);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name_friend);
            mRlView = (RelativeLayout) itemView.findViewById(R.id.rl_layout_friend);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
