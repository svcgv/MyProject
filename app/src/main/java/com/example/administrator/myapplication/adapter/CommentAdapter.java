package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myapplication.Entity.Comment;
import com.example.administrator.myapplication.Entity.CommentJson;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ShowCommentActivty;

/**
 * Created by Administrator on 2016/11/8.
 */

public class CommentAdapter extends BaseAdapter {
    CommentJson commentJson;
    Context context;
    public CommentAdapter(Context context, CommentJson commentJson) {
        this.commentJson = commentJson;
        this.context=context;
    }

    @Override
    public int getCount() {
        return commentJson.comments.length;
    }

    @Override
    public Object getItem(int i) {
        return commentJson.comments[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=new ViewHolder();
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.comment_item,null);
            holder.comment= (TextView) view.findViewById(R.id.comment);
            holder.username= (TextView) view.findViewById(R.id.username);
            holder.data= (TextView) view.findViewById(R.id.data);
            view.setTag(holder);
        }
     //   Log.d("xxxitem","我是评论"+commentJson.comments[i].commt);
        holder= (ViewHolder) view.getTag();
        holder.comment.setText(commentJson.comments[i].commt);
        holder.username.setText(commentJson.comments[i].username);
        holder.data.setText(commentJson.comments[i].timedate);

        return view;
    }
    class  ViewHolder{
        public TextView  username;
        public  TextView comment;
        public  TextView data;
    }
}
