package nexuslink.charon.jim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import nexuslink.charon.jim.R;
import nexuslink.charon.jim.model.AddedBean;

/**
 * 项目名称：Jim
 * 类描述：
 * 创建人：Charon
 * 创建时间：2017/11/24 13:56
 * 修改人：Charon
 * 修改时间：2017/11/24 13:56
 * 修改备注：
 */

public class AddedRvAdapter extends RecyclerView.Adapter {
    private ArrayList<AddedBean> list;
    private OnAddedListener listener;

    public AddedRvAdapter(ArrayList<AddedBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_added_rv, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tvNameAdded.setText(list.get(position).getName());
            ((MyViewHolder) holder).tvReasonAdded.setText(list.get(position).getReason());
            ((MyViewHolder) holder).btnPassAdded.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPass(position);
                    ((MyViewHolder) holder).tvIsHandler.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).rlLayoutAdded.setVisibility(View.GONE);
                }
            });
            ((MyViewHolder) holder).btnDeclinedAdded.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyViewHolder) holder).tvIsHandler.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).rlLayoutAdded.setVisibility(View.GONE);
                    listener.onDeclined(position);
                }
            });
            if (list.get(position).isHandle()) {
                ((MyViewHolder) holder).tvIsHandler.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).rlLayoutAdded.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).tvIsHandler.setVisibility(View.GONE);
                ((MyViewHolder) holder).rlLayoutAdded.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView cvHeadAdded;
        private TextView tvNameAdded, tvReasonAdded,tvIsHandler;
        private Button btnPassAdded, btnDeclinedAdded;
        private RelativeLayout rlLayoutAdded;

        public MyViewHolder(View itemView) {
            super(itemView);
            cvHeadAdded = (ImageView) itemView.findViewById(R.id.iv_head_added);
            tvNameAdded = (TextView) itemView.findViewById(R.id.tv_name_added);
            tvReasonAdded = (TextView) itemView.findViewById(R.id.tv_reason_added);
            tvIsHandler = (TextView) itemView.findViewById(R.id.tv_handler_added);
            btnPassAdded = (Button) itemView.findViewById(R.id.btn_pass_added);
            btnDeclinedAdded = (Button) itemView.findViewById(R.id.btn_declined_added);
            rlLayoutAdded = (RelativeLayout) itemView.findViewById(R.id.rl_layout_added);
        }
    }

    public interface OnAddedListener {
        void onPass(int position);

        void onDeclined(int position);
    }

    public void setOnAddedListener(OnAddedListener listener) {
        if (this.listener == null) {
            this.listener = listener;
        }
    }
}
