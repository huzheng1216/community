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
import com.flyersoft.discuss.javabean.seekbook.BookList;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huzheng
 * @date 2019/6/20
 * @description
 *
 */

public class BookListFragmentAdapter extends RecyclerView.Adapter<ViewHolder> {


    public static final int TYPE_FOOT = 0;
    public static final int TYPE_DATA = 1;

    private List<BookList> mData;
    private OnItemClickListener mListener;
    private boolean hasFoot;//是否展示加载更多

    public BookListFragmentAdapter(List<BookList> data) {
        this.mData = data;
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
            iholder.title.setText(mData.get(position).getListName());
            iholder.intro.setText(mData.get(position).getListIntro());
            iholder.author.setText(mData.get(position).getUserName());
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

    public static class ItemViewHolder extends ViewHolder {

        View itemView;
        SimpleDraweeView pic1;
        SimpleDraweeView pic2;
        SimpleDraweeView pic3;
        TextView author;
        TextView title;
        TextView intro;
        TextView time;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic1 = itemView.findViewById(R.id.book_list_fragment_item_pic_1);
            pic2 = itemView.findViewById(R.id.book_list_fragment_item_pic_2);
            pic3 = itemView.findViewById(R.id.book_list_fragment_item_pic_3);
            author = itemView.findViewById(R.id.book_list_fragment_item_book_author);
            title = itemView.findViewById(R.id.book_list_fragment_item_title);
            intro = itemView.findViewById(R.id.book_list_fragment_item_rep);
            time = itemView.findViewById(R.id.book_list_fragment_item_time);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_fragment_item, null);
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
