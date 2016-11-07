package com.example.administrator.myapplication.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.myapplication.Entity.Entity;
import com.example.administrator.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost= (FragmentTabHost) findViewById
                (android.R.id.tabhost);
        //android.R.id.tabcontent是Fragment的布局
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //去掉分割线
        tabHost.getTabWidget().setDividerDrawable(null);
        initTabhost();
    }
    private void initTabhost() {
        LayoutInflater inflater=LayoutInflater.from(this);
        for (int i=0;i<5;i++){
            TabHost.TabSpec tab=tabHost.newTabSpec("tab" + i);
            View view=inflater.inflate(R.layout.item, null);
            //需要通过view去拿到控件，否则会空指针异常
            ImageView ivtitle= (ImageView)view.findViewById(R.id.image);
            TextView textView= (TextView) view.findViewById(R.id.text);
            ivtitle.setImageResource(Entity.vt[i]);
            textView.setText(Entity.titile[i]);
            if(i==2){
                textView.setText("");
            }
            tab.setIndicator(view);
            tabHost.addTab(tab,
                    Entity.fragmentClass[i], null);
        }
    }


}
