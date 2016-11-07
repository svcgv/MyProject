package com.example.administrator.myapplication.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;


import com.example.administrator.myapplication.Entity.Entity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ViewPagerAdapter;

import java.util.HashMap;
import java.util.Map;
public class Fragment_one extends Fragment {
    public HorizontalScrollView scrollView;
    public ViewPager viewPager;
    public Map<String,Fragment> map;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_fragment_one,container,false);
        initView(view);
        return  view;
    }
    private void initView(View view) {
    //    scrollView= (HorizontalScrollView) view.findViewById(R.id.scrolview);
        viewPager= (ViewPager) view.findViewById(R.id.page);
        map=new HashMap<String,Fragment>();
        for(int i = 0; i< Entity.CODE.length; i++){
            map.put(Entity.CODE[i],ViewpageFragment.newInstance(Entity.URL[i]));
        }
        ViewPagerAdapter adapter=new ViewPagerAdapter(getFragmentManager(),map);
        viewPager.setAdapter(adapter);

    }
  /*  private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson=new Gson();
                JsonDoGet json=new JsonDoGet(DefineStatic.INFO);
                String result=json.DoGet();
                JsonInfo info= gson.fromJson(result, JsonInfo.class);
              //  JsonDoGet json=new JsonDoGet(link);
                  Log.d("xxx",DefineStatic.INFO);
                  Log.d("xxx", result);
                  Gson myjson=new Gson();
                try{
                    Log.d("xxx",info.infos[0].contentimageurl);
                    Log.d("xxx",info.infos[1].contentimageurl);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}
