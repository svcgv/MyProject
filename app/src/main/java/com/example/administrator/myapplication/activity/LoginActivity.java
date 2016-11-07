package com.example.administrator.myapplication.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mytoast.MyToast;
import com.example.administrator.myapplication.staticc.DefineStatic;
import com.example.administrator.myapplication.utils.LoginDoGet;
import com.example.administrator.myapplication.utils.UpLoadImage;
import com.example.administrator.myapplication.utils.getimagepath.ImagePath;
import com.jakewharton.disklrucache.DiskLruCache;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class LoginActivity extends AppCompatActivity {
    String url=null;
    public SharedPreferences preferences;
    Map<String,String> map;
    Button login;
    Button resigster;
    Button mood;
    EditText username;
    EditText password;
    String us;
    String pwd;
    Button upload;
    ImageView imageView;
    public  Handler handle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DefineStatic.SUCCESS:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case DefineStatic.FEILD:
                    mt("用户名或密码错误");
                    password.setText("");
                    break;
                case DefineStatic.Resisger_Success:
                    mt("注册成功，请重新登录");
                    break;
                case DefineStatic.Resisger_Field:
                    mt("注册失败,该用户已被注册");
                    break;
            }
            return false;
        }
    }
    );
    private void mt(String message) {
        MyToast.myMakeToast(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initNetWork();
    }
    private void initNetWork() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = DefineStatic.loachost;
                map = new HashMap<String, String>();
                us = username.getText().toString();
                pwd = password.getText().toString();
                preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", us);
                editor.putString("password", pwd);
                editor.commit();
                map.put("username", us);
                map.put("password", pwd);
                url += "?";
                for (String s : map.keySet()) {
                    url = url + s + "=" + map.get(s) + "&";
                }
                url = url.substring(0, url.length() - 1);
                Log.d("xxx", url);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      /*
                       LoginDoGet cc=new LoginDoGet(DefineStatic.LOGIN,handle,url);
                       cc.DoGet();
                       */
                        LoginDoGet cc = new LoginDoGet(DefineStatic.LOGIN, handle, url);
                        cc.DoGet();
                    }
                }).start();
            }
        });
        resigster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = DefineStatic.resigster;
                map = new HashMap<String, String>();
                us = username.getText().toString();
                pwd = password.getText().toString();
                preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", us);
                editor.putString("password", pwd);
                editor.commit();
                map.put("username", us);
                map.put("password", pwd);
                url += "?";
                for (String s : map.keySet()) {
                    url = url + s + "=" + map.get(s) + "&";
                }
                url = url.substring(0, url.length() - 1);
                Log.d("xxx",url);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoginDoGet cc = new LoginDoGet(DefineStatic.RESIGSTER, handle, url);
                        cc.DoGet();
                    }
                }).start();

            }
        });
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MoodActivity.class);
                startActivity(intent);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getString("username", "") == "") {
                    Dialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("您还没注册,请先注册")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                              //      picPath = null;
                                }
                            }).create();
                    dialog.show();
                    Toast.makeText(LoginActivity.this, "请先注册", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
    private String picPath = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //android版本大于4.4不能简单的获得图片的路径
        if (resultCode == Activity.RESULT_OK) {
            ImagePath imagePath = new ImagePath();
            Uri uri = data.getData();
            picPath= imagePath.getPath(this, uri);
            preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("PATH",picPath);
            editor.commit();
            Log.d("xxx","picpath"+picPath);
            final File file=new File(picPath);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UpLoadImage upLoadImage=new UpLoadImage();
                    Log.d("xxxx",DefineStatic.UPLOAD);
                    Log.d("xxxx", "回执编号为" + upLoadImage.uploadFile(file, DefineStatic.UPLOAD,us));
                }
            }).start();
            try {
                imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
    /*    if (resultCode == Activity.RESULT_OK) {
            *//**t
             *
             *
             * 当选择的图片不为空的话，在获取到图片的途径
             *//*
            Uri uri = data.getData();
            Log.e("TAG", "uri = " + uri);

            //显得到bitmap图片
            try {
                imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String[] pojo = { MediaStore.Images.Media.DATA };
           //     Cursor cursor = managedQuery(uri, pojo, null, null, null);
                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档

                Cursor cursor = managedQuery(uri, proj, null, null, null);
                if (cursor != null) {
                    *//*ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                     cursor.moveToFirst();
                    Log.d("xxx",""+colunm_index);
                    String path = cursor.getString(colunm_index);
                   //cursor.close();
                   *//*
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    Log.d("xxx11","我是地址"+path);
                    try {
                        Log.d("xxx",path);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    *//***
             * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
             * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
             *//*
                 if(path==null){
                     alert();
                 }
                    Log.d("xxx", path);
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                        imageView.setImageBitmap(bitmap);
                    } else {
                        alert();
                    }
                } else {
                    alert();
                }

            } catch (Exception e) {
            }
        }*/

        }
    }
    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        picPath = null;
                    }
                }).create();
        dialog.show();
    }

// BitmapFactory.decodeFile(file.getAbsolutePath()) 路径没问题 但是始终报kongji
    private void initView() {
        preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
        String path=preferences.getString("PATH","");
       // Log.d("xxx","路径为:"+path);
        File file=new File(path);
        try{
            if(file.exists()){
                Bitmap map= BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(map);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
       /*
        Uri u=Uri.parse(path);
        try {
            imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), u));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
         username= (EditText) findViewById(R.id.username);
         password= (EditText) findViewById(R.id.password);
         login= (Button) findViewById(R.id.btlogin);
         resigster= (Button) findViewById(R.id.register);
         upload= (Button) findViewById(R.id.upload);
         imageView= (ImageView) findViewById(R.id.image);
          mood= (Button) findViewById(R.id.mood);
    }
}
