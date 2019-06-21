package com.flyersoft.discuss.shuhuang;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.seekbook.BookListInfo;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 书慌界面适配器
 * Created by huzheng on 2017/8/31.
 */

public class BookListCreatAdapter extends RecyclerView.Adapter<ViewHolder> {


    public static final int TYPE_FOOT = 0;
    public static final int TYPE_DATA = 1;

    private List<BookListInfo> mData;
    private OnItemRemoveListener mListener;
    private boolean hasFoot;//是否展示加载更多

    public BookListCreatAdapter(List<BookListInfo> data) {
        this.mData = data;
    }


    public void changeData(List<BookListInfo> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<BookListInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setFoot(boolean hasFoot){
        this.hasFoot = hasFoot;
    }

    public void setOnItemClickListener(OnItemRemoveListener listener){
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
            iholder.title.setText(mData.get(position).getBookName());
            iholder.author.setText(mData.get(position).getBookAuthor());
            iholder.type.setText("奇幻");
            iholder.recomm.setText(mData.get(position).getBookAppraise());
            iholder.pic.setImageURI(mData.get(position).getBookIcn());
            if(null != mListener){
                iholder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemRemove(position);
                    }
                });
                iholder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemEdit(position);
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
        TextView title;
        TextView author;
        TextView type;
        View remove;
        TextView recomm;
        TextView edit;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = itemView.findViewById(R.id.book_list_creat_book_pic);
            title = itemView.findViewById(R.id.book_list_creat_book_title);
            author = itemView.findViewById(R.id.book_list_creat_book_author);
            type = itemView.findViewById(R.id.book_list_creat_book_type);
            remove = itemView.findViewById(R.id.book_list_creat_book_remove);
            edit = itemView.findViewById(R.id.book_list_creat_book_edit);
            recomm = itemView.findViewById(R.id.book_list_creat_book_recomm);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_creat_book_item, null);
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

    public interface OnItemRemoveListener{
        void onItemEdit(int position);
        void onItemRemove(int position);
    }

}
