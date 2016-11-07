package com.example.administrator.myapplication.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.myapplication.Entity.Entity;
import com.example.administrator.myapplication.fragments.ViewpageFragment;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/24.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public Map<String,Fragment> map;
    public ViewPagerAdapter(FragmentManager fm,Map<String,Fragment> map) {
        super(fm);
        this.map=map;
    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     *
     *
     */
    @Override
    public Fragment getItem(int position) {
        if (map.get(Entity.CODE[position]) == null) {
            map.put(Entity.CODE[position], ViewpageFragment.newInstance(Entity.URL[position]));
        }
        return map.get(Entity.CODE[position]);
    }

    @Override
    public int getCount() {
        return map.size();
    }
}
