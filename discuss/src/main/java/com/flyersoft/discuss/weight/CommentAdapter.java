package com.flyersoft.discuss.weight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.javabean.seekbook.Comments;

import java.util.List;

/**
 * 评论配器
 * Created by huzheng on 2017/8/31.
 */

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comments> mData;
    private OnItemClickListener mListener;

    public CommentAdapter(List<Comments> data, Context context) {
        this.mData = data;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            // 实例化展示的view
            view = LayoutInflater.from(context).inflate(R.layout.comment_main_item, null);
            // 实例化viewholder
            viewHolder = new ViewHolder(view);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        // 绑定数据
        viewHolder.name.setText(mData.get(position).getUserName());
        viewHolder.content.setText(mData.get(position).getCont());
        viewHolder.lou.setText(mData.get(position).getFloorNum()+"楼");
        viewHolder.pic.setImageURI(mData.get(position).getUserIcn());
        viewHolder.time.setText(mData.get(position).getCreateTime());
        view.setTag(viewHolder);

        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        SimpleDraweeView pic;
        TextView name;
        TextView content;
        TextView lou;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = itemView.findViewById(R.id.shuhuang_main_comm_pic);
            name = itemView.findViewById(R.id.shuhuang_main_comm_auther);
            time = itemView.findViewById(R.id.shuhuang_main_comm_time);
            lou = itemView.findViewById(R.id.shuhuang_main_comm_lou);
            content = itemView.findViewById(R.id.shuhuang_main_item_comm_content);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
