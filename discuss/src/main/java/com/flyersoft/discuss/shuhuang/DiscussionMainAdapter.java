package com.flyersoft.discuss.shuhuang;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 书慌界面适配器
 * Created by huzheng on 2017/8/31.
 */

public class DiscussionMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_FOOT = 0;
    public static final int TYPE_DATA = 1;

    private List<Discuss> mData;
    private OnItemClickListener mListener;
    private boolean hasFoot;//是否展示加载更多

    public DiscussionMainAdapter(List<Discuss> data) {
        this.mData = data;
    }


    public void changeData(List<Discuss> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Discuss> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setFoot(boolean hasFoot){
        this.hasFoot = hasFoot;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            return onCreateFootViewHolder(parent, viewType);
        }else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(holder instanceof FootViewHolder){

            FootViewHolder vholder = (FootViewHolder) holder;
            vholder.text.setVisibility(View.VISIBLE);
        }else{
            ItemViewHolder iholder = (ItemViewHolder) holder;
            // 绑定数据
            iholder.name.setText(mData.get(position).getUserName());
            iholder.content.setText(mData.get(position).getTitle());
            iholder.comm_count.setText("评论："+mData.get(position).getCommCount());
            iholder.tip_count.setText("同感："+mData.get(position).getSameFeelCount());
            iholder.pic.setImageURI(mData.get(position).getUserIcn());
            iholder.time.setText(StringTools.differTime(mData.get(position).getCreateTime()));
            if(null != mListener){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemClick(position);
                    }
                });
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        SimpleDraweeView pic;
        TextView name;
        TextView time;
        TextView content;
        TextView comm_count;//评论数
        TextView tip_count;//点赞/收藏数

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = itemView.findViewById(R.id.shuhuang_main_item_user_pic);
            name = itemView.findViewById(R.id.shuhuang_main_item_user_name);
            time = itemView.findViewById(R.id.shuhuang_main_item_time);
            content = itemView.findViewById(R.id.shuhuang_main_item_user_help);
            comm_count = itemView.findViewById(R.id.shuhuang_main_item_comm_count);
            tip_count = itemView.findViewById(R.id.shuhuang_main_item_tip_count);
        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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


    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shuhuang_main_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ItemViewHolder(view);
    }

    public RecyclerView.ViewHolder onCreateFootViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_foot_progress, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FootViewHolder(view);
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
