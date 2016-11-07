package com.example.administrator.myapplication.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.staticc.DefineStatic;
import com.example.administrator.myapplication.utils.UploadMood;
import com.example.administrator.myapplication.utils.getimagepath.ImagePath;

import java.io.File;
import java.io.IOException;
public class MoodActivity extends AppCompatActivity{
    public ImageView userImage;
    public TextView username;
    public EditText content;
    public ImageView contentImage;
    public Button upload;
    public Button fabiao;
    public Button quxiao;
    public String picPath;
    private String us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        initView();
    }
    private void initView() {
        userImage = (ImageView) findViewById(R.id.userImage);
        username = (TextView) findViewById(R.id.user);
        content = (EditText) findViewById(R.id.contenttext);
        contentImage = (ImageView) findViewById(R.id.contentImage);
        upload = (Button) findViewById(R.id.uploadimage);
        fabiao = (Button) findViewById(R.id.fabiao);
        quxiao = (Button) findViewById(R.id.quxiao);
        initOnclcik();
    }

    private void initOnclcik() {
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
                us= preferences.getString("username","");
                if(us==""){
                    mt("您还没注册，请先注册");
                    Intent intent=new Intent(MoodActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
            }
        });

        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
                us= preferences.getString("username","");
             //   UploadMood mood=new UploadMood();
                if(us==""){
                    mt("您还没注册，请先注册");
                    Intent intent=new Intent(MoodActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    SharedPreferences preferencs=getSharedPreferences("PATHED",MODE_PRIVATE);
                    picPath=preferencs.getString("MYPATH","");
                    final File file = new File(picPath);

                /*    preferences = getSharedPreferences(DefineStatic.PREFERCE, MODE_PRIVATE);
                    picPath =preferences.getString("PATH","");
                  */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UploadMood.uploadFile(file, DefineStatic.UPLOADMOOD, us, content.getText().toString());
                         // Toast.makeText(MoodActivity.this,"发表成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MoodActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).start();
                    }


            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mt("我是取消");
            }
        });
    }


    private void mt(String message) {
        Toast.makeText(MoodActivity.this,message,Toast.LENGTH_SHORT).show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //android版本大于4.4不能简单的获得图片的路径
        if (resultCode == Activity.RESULT_OK) {
            ImagePath imagePath = new ImagePath();
            Uri uri = data.getData();
            picPath = imagePath.getPath(this, uri);
            SharedPreferences preferences=getSharedPreferences("PATHED",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("MYPATH",picPath);
            editor.commit();
            try {
                contentImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}