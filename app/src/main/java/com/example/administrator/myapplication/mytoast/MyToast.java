package com.example.administrator.myapplication.mytoast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/11/3.
 */

public class MyToast extends Toast {
    public  final  Context context;
    public MyToast(Context context) {
        super(context);
        this.context=context;
        setGravity(Gravity.CENTER,0,0);
    }


    /* 用来设置本toast的视图的方法*/
    @Override
    public void setView(View view) {
        super.setView(view);
    }

    @Override
    public void setText(CharSequence s) {
//        super.setText(s);
        ((TextView)getView().findViewById(R.id.textview)).setText(s);
    }

    public static Toast myMakeToast(Context context, String s, int lengthLong) {
        MyToast toast = new MyToast(context);
        toast.setView(LayoutInflater.from(context).inflate(R.layout.toast_layout, null));
        toast.setText(s);
        return toast;
    }
}
