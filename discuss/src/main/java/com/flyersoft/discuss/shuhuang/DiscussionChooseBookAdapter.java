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
import com.flyersoft.discuss.javabean.Book;
import com.flyersoft.discuss.tools.LogTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 书慌界面适配器
 * Created by huzheng on 2017/8/31.
 */

public class DiscussionChooseBookAdapter extends RecyclerView.Adapter<ViewHolder> {


    public static final int TYPE_FOOT = 0;
    public static final int TYPE_DATA = 1;

    private List<Book> mData;
    private OnItemClickListener mListener;
    private boolean hasFoot;//是否展示加载更多
    private int currentPosition = -1;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public DiscussionChooseBookAdapter(List<Book> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<Book> data) {
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
        LogTools.H("onCreateViewHolder");
        if (viewType == TYPE_FOOT) {
            return onCreateFootViewHolder(parent, viewType);
        }else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        LogTools.H("onBindViewHolder");
        if(holder instanceof FootViewHolder){

            FootViewHolder vholder = (FootViewHolder) holder;
            vholder.text.setVisibility(View.VISIBLE);
        }else{
            ItemViewHolder iholder = (ItemViewHolder) holder;
            // 绑定数据
            iholder.name.setText(mData.get(position).getBookName());
            iholder.pic.setImageURI(mData.get(position).getMidImage());
            iholder.author.setText(mData.get(position).getAuthor());
            if(position == currentPosition){
                iholder.choose.setVisibility(View.VISIBLE);
            }else{
                iholder.choose.setVisibility(View.GONE);
            }
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

    public static class ItemViewHolder extends ViewHolder {

        View itemView;
        SimpleDraweeView pic;
        TextView name;
        TextView author;
        View choose;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = itemView.findViewById(R.id.discussion_book_choose_main_item_book_pic);
            name = itemView.findViewById(R.id.discussion_book_choose_main_item_book_name);
            author = itemView.findViewById(R.id.discussion_book_choose_main_item_book_author);
            choose = itemView.findViewById(R.id.discussion_book_choose_main_item_book_choose);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_book_choose_main_item, null);
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

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
