package com.xuhao.myapp.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class MyShowPagerAdpter extends FragmentStatePagerAdapter {

    private List<Fragment> mViews;
    private List<String> mTitles;

    public MyShowPagerAdpter(FragmentManager fm, List<Fragment> mViews, List<String> mTitles) {
        super(fm);
        this.mViews = mViews;
        this.mTitles = mTitles;
    }


    @Override
    public Fragment getItem(int i) {
        return mViews.get(i);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
