package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.Entity.CommentJson;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.CommentAdapter;
import com.example.administrator.myapplication.mytoast.MyToast;
import com.example.administrator.myapplication.staticc.DefineStatic;
import com.example.administrator.myapplication.utils.JsonDoGet;
import com.google.gson.Gson;

public class ShowCommentActivty extends AppCompatActivity {
  public ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment_activty);
        list= (ListView) findViewById(R.id.list);
        initData();
    }

    private void initData() {
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",-1);
        if(id==-1){
            MyToast.myMakeToast(this,"没有此item",Toast.LENGTH_SHORT).show();
        }else{
            String url="";
            url+= DefineStatic.GETCOMMENT+"?id="+id;
            new CommentTask().execute(url);
        }
    }
    public class CommentTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            String url="";
            url=strings[0];
            Log.d("xxxhere",url);
            JsonDoGet get=new JsonDoGet(url);
            return get.DoGet();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CommentJson commentJson=new CommentJson();
            Gson gson=new Gson();
            Log.d("xxxhere",s);
            commentJson=gson.fromJson(s,CommentJson.class);
            if(commentJson==null){
                MyToast.myMakeToast(ShowCommentActivty.this,"暂时还没有评论",Toast.LENGTH_SHORT).show();
            }else
            {
                CommentAdapter adapter=new CommentAdapter(getApplicationContext(),commentJson);
            list.setAdapter(adapter);}
        }

    }
}
