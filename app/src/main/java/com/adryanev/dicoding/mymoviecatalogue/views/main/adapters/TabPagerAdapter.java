package com.adryanev.dicoding.mymoviecatalogue.views.main.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.views.main.fragment.NowPlayingFragment;
import com.adryanev.dicoding.mymoviecatalogue.views.main.fragment.PopularFragment;
import com.adryanev.dicoding.mymoviecatalogue.views.main.fragment.UpcomingFragment;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

    Context context;
    List<Fragment> tabList;
    public TabPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        tabList = new ArrayList<>();
        tabList.add(new PopularFragment());
        tabList.add(new UpcomingFragment());
        tabList.add(new NowPlayingFragment());
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return tabList.get(i);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return context.getString(R.string.popular);
            case 1: return context.getString(R.string.upcoming);
            case 2: return context.getString(R.string.now_playing);
        }
        return super.getPageTitle(position);
    }
}
