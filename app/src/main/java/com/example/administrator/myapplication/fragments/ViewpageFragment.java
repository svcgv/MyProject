package com.example.administrator.myapplication.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ItemAdapter;
import com.example.administrator.myapplication.bean.JsonInfo;
import com.example.administrator.myapplication.utils.JsonDoGet;
import com.google.gson.Gson;


/*
* *
 * A simple {@link Fragment} subclass.
 */
   public class ViewpageFragment extends Fragment {
    public String result;
    public ListView listView;
   public   Handler handler=new Handler(new Handler.Callback() {
       @Override
       public boolean handleMessage(Message msg) {
           if(msg.what==1){
              JsonInfo info= (JsonInfo) msg.obj;
               ItemAdapter adapter=new ItemAdapter(getContext(),info);
               listView.setAdapter(adapter);
           }
           return false;
       }
   });
    public static ViewpageFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("URL",url);
        ViewpageFragment fragment = new ViewpageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_viewpage, container, false);
        InitData(view);
        return view;
    }
    private void InitData(View view) {
        final String link= (String) getArguments().get("URL");
        Log.d("xxxx1234",link);
        listView= (ListView) view.findViewById(R.id.list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonDoGet json=new JsonDoGet(link);
                result=json.DoGet();
                Log.d("xxxx1234",result);
                Gson myjson=new Gson();
                try{
                    JsonInfo info=myjson.fromJson(result,JsonInfo.class);
                    Message message=Message.obtain();
                    message.obj=info;
                    message.what=1;
                    handler.sendMessage(message);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
