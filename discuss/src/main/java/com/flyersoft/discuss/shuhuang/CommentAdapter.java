package com.flyersoft.discuss.shuhuang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.account.AccountData;
import com.flyersoft.discuss.javabean.seekbook.Comments;
import com.flyersoft.discuss.tools.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 书慌界面适配器
 * Created by huzheng on 2017/8/31.
 */

public class CommentAdapter extends RecyclerView.Adapter<ViewHolder> {


    public static final int TYPE_FOOT = 0;
    public static final int TYPE_DATA = 1;

    private List<Comments> mData;
    private OnItemClickListener mListener;
    private boolean hasFoot;//是否展示加载更多
    private String disList;

    public CommentAdapter(Context context, List<Comments> data) {
        this.mData = data;
        disList = AccountData.getInstance((Activity) context).getDis(context);
    }

    public void updateData(ArrayList<Comments> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setFoot(boolean hasFoot) {
        this.hasFoot = hasFoot;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            return onCreateFootViewHolder(parent, viewType);
        } else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (holder instanceof FootViewHolder) {

            FootViewHolder vholder = (FootViewHolder) holder;
            vholder.text.setVisibility(View.VISIBLE);
        } else {
            final ItemViewHolder iholder = (ItemViewHolder) holder;
            if (null != mListener) {
                iholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemClick(position);
                    }
                });
                iholder.dis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener.onItemDis(position)) {
                            iholder.dis.setSelected(true);
//                            iholder.dis.setClickable(false);
                            iholder.dis.setText(Integer.parseInt(iholder.dis.getText().toString()) + 1 + "");
                            iholder.dis.setTextColor(Color.parseColor("#F55A5D"));
                        }
                    }
                });
            }
            // 绑定数据
            String replyUserName = mData.get(position).getReplyUserName();
            iholder.name.setText(mData.get(position).getUserName() + (StringTools.isNotEmpty(replyUserName) ? (" 回复：" + replyUserName) : ""));
            iholder.content.setText(mData.get(position).getCont());
            iholder.lou.setText(mData.get(position).getFloorNum() + "楼");
            iholder.pic.setImageURI(mData.get(position).getUserIcn());
            String ctime = mData.get(position).getCreateTime();
            iholder.time.setText(StringTools.differTimeGMT(ctime, ctime.indexOf("T") == -1 ? 2 : 1));
            iholder.dis.setText(mData.get(position).getSameFeelCount());
            //是否已经点赞
//            boolean hasDis = AccountData.getInstance((Activity) iholder.itemView.getContext()).hasDis(iholder.itemView.getContext(), mData.get(position).getCommId());
            if (disList.indexOf(mData.get(position).getCommId()) > -1) {
//                LogTools.H("点赞过：" + mData.get(position).getFloorNum());
                iholder.dis.setSelected(true);
//                iholder.dis.setClickable(false);
//                iholder.dis.setText(Integer.parseInt(iholder.dis.getText().toJson()) + 1 + "");
                iholder.dis.setTextColor(Color.parseColor("#F55A5D"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : (mData.size() + (hasFoot ? 1 : 0));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size() && hasFoot) {
            return TYPE_FOOT;
        } else {
            return TYPE_DATA;
        }
    }

    public static class ItemViewHolder extends ViewHolder {

        View itemView;
        SimpleDraweeView pic;
        RadioButton dis;//点赞
        TextView name;
        TextView content;
        TextView lou;
        TextView time;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = itemView.findViewById(R.id.shuhuang_main_comm_pic);
            name = itemView.findViewById(R.id.shuhuang_main_comm_auther);
            time = itemView.findViewById(R.id.shuhuang_main_comm_time);
            lou = itemView.findViewById(R.id.shuhuang_main_comm_lou);
            content = itemView.findViewById(R.id.shuhuang_main_item_comm_content);
            dis = itemView.findViewById(R.id.shuhuang_main_comm_dis_bt);
        }
    }


    class FootViewHolder extends ViewHolder implements View.OnClickListener {

        private View rootView;
        private TextView text;

        public FootViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.category_foot_item_layout);
            text = (TextView) itemView.findViewById(R.id.category_foot_item_title);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (null != onRecyclerViewListener) {
//                onRecyclerViewListener.onFootViewClick();
//            }
        }

    }


    public ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_main_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ItemViewHolder(view);
    }

    public ViewHolder onCreateFootViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_foot_progress, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FootViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        boolean onItemDis(int position);
    }

}
