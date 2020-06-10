package com.xuhao.myapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class CourseinfoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ImageView> imageViews;

    public CourseinfoViewPagerAdapter(Context context, List<ImageView> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return null != imageViews ? imageViews.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }
}
