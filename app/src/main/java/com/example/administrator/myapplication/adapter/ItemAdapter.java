package com.example.administrator.myapplication.adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.Entity.Comment;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.MoodActivity;
import com.example.administrator.myapplication.activity.ShowCommentActivty;
import com.example.administrator.myapplication.bean.JsonInfo;
import com.example.administrator.myapplication.disk.DiskApp;
import com.example.administrator.myapplication.disk.DiskConfig;
import com.example.administrator.myapplication.disk.Utils;
import com.example.administrator.myapplication.mytoast.MyToast;
import com.example.administrator.myapplication.staticc.DefineStatic;
import com.example.administrator.myapplication.utils.CommentGet;
import com.example.administrator.myapplication.utils.LoginDoGet;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Handler;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ItemAdapter extends BaseAdapter {
    public JsonInfo info;
    public Context context;
    public DiskLruCache disk;
    LruCache<String,BitmapDrawable> mluCache;
    public ItemAdapter(Context context,JsonInfo info) {
        int MaxMenmory= (int) Runtime.getRuntime().maxMemory();
        int Cache=MaxMenmory/8;
        mluCache=new LruCache<String,BitmapDrawable>(Cache){
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
               // return super.sizeOf(key, drawable);
                return drawable.getBitmap().getByteCount();
            }
        };
        this.info=info;
        this.context=context;
       ;
        try {
            //创建文件的sd卡的缓存目录
            Log.d("xxxname1","我是缓存"+getCacheDir(context).getAbsolutePath());
        //    Log.d("xxxname1","我是缓存"+(((DiskApp)context.getApplicationContext()).getDisk().getCacheDir().getAbsolutePath())+"sjide");
         //   disk=DiskLruCache.open(((DiskApp)context.getApplicationContext()).getDisk().getCacheDir(), getAppVersion(context), 1, 100 * 1024 * 1024) ;// 100MB);
            disk=DiskLruCache.open(getCacheDir(context), getAppVersion(context), 1, 100 * 1024 * 1024) ;
        } catch (Exception e) {
    Log.d("xxxname1","我是缓存我异常了");
            e.printStackTrace();
        }
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    @Override
    public int getCount() {
        return info.infos.length;
    }
    @Override
    public Object getItem(int position) {
        return info.infos[position];
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder=new ViewHolder();
        if(convertView==null){
          View view= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            convertView=view;
            holder.useriamge= (ImageView) convertView.findViewById(R.id.image);
            holder.username= (TextView) convertView.findViewById(R.id.text);
            holder.content= (TextView) convertView.findViewById(R.id.contenttext);
            holder.contentiamge= (ImageView) convertView.findViewById(R.id.contentImage);
            holder.comment= (Button) convertView.findViewById(R.id.comment);
            holder.commentContent= (Button) convertView.findViewById(R.id.lookcomment);
            holder.tijiao= (Button) convertView.findViewById(R.id.tijiao);
            holder.bianji= (EditText) convertView.findViewById(R.id.bianji);
            convertView.setTag(holder);
        }
        final ViewHolder viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.content.getPaint().setFakeBoldText(true);
        viewHolder.username.setText(info.infos[position].username);
        viewHolder.useriamge.setImageResource(R.drawable.login_circle);
        viewHolder.content.setText("  " + info.infos[position].contenttext);
//首先判断sdk是否存在图片
    Bitmap bitmap=loadFromSdKa(info.infos[position].contentimageurl);
     Log.d("xxx",info.infos[position].contentimageurl);
        if(bitmap!=null){
                viewHolder.contentiamge.setImageBitmap(bitmap);
        }
    //    viewHolder.contentiamge.setImageResource(R.drawable.login_circle);
        //判断缓存中是否有该图片
        if(mluCache.get(info.infos[position].contentimageurl)!=null){
            viewHolder.contentiamge.setImageBitmap(mluCache.get(info.infos[position].contentimageurl).getBitmap());
        }else{
        MySyctask mySyctask=new MySyctask(viewHolder.contentiamge);
        mySyctask.execute(info.infos[position].contentimageurl);
        }
        //将评论属性调为可见
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处写评论的点击逻辑
                viewHolder.bianji.setVisibility(View.VISIBLE);
                viewHolder.tijiao.setVisibility(View.VISIBLE);
                viewHolder.tijiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String comment="";
                        HashMap<String,String> map = null;
                        String username=  context.getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE).getString("username","  ");
                        comment=viewHolder.bianji.getText().toString();
                        String url =DefineStatic.INSERTCOMMENT;
                        final Comment co=new Comment();
                        co.comment_id=info.infos[position].id;
                        co.username=username;
                        co.commt=comment;
                        Log.d("xxx",url);
                        Log.d("xxx的评论",comment);
                        if(comment==""){
                            mt("请输入评论");
                        }else{
                            final String finalUrl1 = url;
                            /*new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    CommentGet com=new CommentGet(finalUrl1,co);
                                    com.SendMessage();
                                    mt("评论成功");
                                }
                            }).start();*/
                           /* MySyctask syctask=new MySyctask(viewHolder.useriamge);
                            syctask.execute(finalUrl1);*/
                            //用Synctask进行加载
                            new AsyncTask<String, Integer, Integer>() {
                                @Override
                                protected Integer doInBackground(String... strings) {
                                    String u=strings[0];
                                    CommentGet commentGet=new CommentGet(u,co);
                                    return commentGet.SendMessage();
                                }

                                @Override
                                protected void onPostExecute(Integer integer) {
                                    super.onPostExecute(integer);
                                    //清空文本框
                                    viewHolder.bianji.setText("");
                                    viewHolder.tijiao.setVisibility(View.GONE);
                                    viewHolder.bianji.setVisibility(View.GONE);
                                }
                            }.execute(finalUrl1);
                        }
                    }
                });

            }
        });
        //显示评论
        viewHolder.commentContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowCommentActivty.class);
                intent.putExtra("id",info.infos[position].id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private void mt(String message) {
        Looper.prepare();
        MyToast.myMakeToast(context,message,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    public  class ViewHolder{
        public ImageView useriamge;
        public TextView  username;
        public TextView  content;
        public ImageView contentiamge;
        public Button comment;
        public  Button commentContent;
         public  Button tijiao;
        public EditText bianji;
    }

class MySyctask extends AsyncTask<String,Integer,Bitmap>{

   ImageView image;
    private Bitmap bitmap;
    private String link;
    public MySyctask(ImageView image) {
        this.image = image;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        //对链接进行md5加密
        String name = getDigestHexToString (params[0]);
        BitmapDrawable drawable;
        link=params[0];
        link= DefineStatic.ip+"/MyProject"+link;
        Log.d("xxxxxxx", link);
        HttpURLConnection conn=null;
        try {
            URL url=new URL(link);
            conn= (HttpURLConnection) url.openConnection();
            Log.d("xxxxxxxx",""+conn.getResponseCode());
            if(conn.getResponseCode()==conn.HTTP_OK){
             bitmap= BitmapFactory.decodeStream(conn.getInputStream());
                drawable=new BitmapDrawable(bitmap);
                addBitMaoToCache(link,drawable);
                DiskLruCache.Editor editor=disk.edit(name);
                if (editor != null) {
                    OutputStream os = editor.newOutputStream(0);
                    Utils.CopyStream(conn.getInputStream(), os);
                    editor.commit();
                }
                disk.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conn!=null) {
                conn.disconnect();
            }
        }
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
    //   super.onPostExecute(bitmap);

        image.setImageBitmap(bitmap);

    }
}
    private Bitmap loadFromSdKa(String url) {

        /*DiskLruCache*/
        String name = getDigestHexToString (url);
        try {
            Log.d("xxxname","我是空的1");
            if(disk==null){
                Log.d("xxxname","我是空的2");
            }
            if (disk.get(name)==null) {
                Log.d("xxxname","我是空的3");
                return null;
            }
            else {
                InputStream is = disk.get(name).getInputStream(0);
                return BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDigestHexToString(String url) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes());
            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0"
                            + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
    private void addBitMaoToCache(String link, BitmapDrawable drawable) {
        mluCache.put(link,drawable);
    }








       public  File getCacheDir(Context context) {
            // 初始化缓存目录
            //先判断sk卡是否存在
             File cacheDir;
            String CACHEDIR = "panoramaCache";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                //如果存在获取外存卡的下载目录
                cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            } else {
                cacheDir = Environment.getDataDirectory();
            }
            if (cacheDir != null) {
                cacheDir = new File(cacheDir.getAbsolutePath()+File.separator+CACHEDIR);
            }
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
             return cacheDir;
        }



}