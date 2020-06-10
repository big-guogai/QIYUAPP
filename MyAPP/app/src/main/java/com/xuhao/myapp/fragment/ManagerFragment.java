package com.xuhao.myapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.MyShowPagerAdpter;
import com.xuhao.myapp.fragment.indentkind.BossHandleIndentFragment;
import com.xuhao.myapp.fragment.indentkind.BossHistoryIndentFragment;
import com.xuhao.myapp.fragment.indentkind.HistoryIndentFragment;
import com.xuhao.myapp.fragment.indentkind.WaitCallBackIndentFragment;
import com.xuhao.myapp.fragment.indentkind.WaitPayIndentFragment;
import com.xuhao.myapp.fragment.managerkind.BossInfoManagerFragment;
import com.xuhao.myapp.fragment.managerkind.CourseInfoManagerFragment;
import com.xuhao.myapp.fragment.managerkind.IndentInfoManagerFragment;
import com.xuhao.myapp.fragment.managerkind.UserInfoManagerFragment;

import java.util.ArrayList;
import java.util.List;

public class ManagerFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager mManagerVp;
    private List<String> tab;
    private List<Fragment> mViews;
    private View view;
    private MyShowPagerAdpter myShowPagerAdpter;
    private int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_manager, container, false);
        view = inflate;
        initView();
        initData();
        return inflate;
    }

    private void initView() {
        tabLayout = view.findViewById(R.id.manager_kind);
        mManagerVp = view.findViewById(R.id.manager_kind_viewPager);
    }

    private void initData() {
        myShowPagerAdpter = new MyShowPagerAdpter(getChildFragmentManager(), getmViews(), getTit());
        mManagerVp.setAdapter(myShowPagerAdpter);
        tabLayout.setupWithViewPager(mManagerVp);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private List<Fragment> getmViews() {
        mViews = new ArrayList<>();
        mViews.add(new UserInfoManagerFragment());
        mViews.add(new BossInfoManagerFragment());
        mViews.add(new CourseInfoManagerFragment());
        mViews.add(new IndentInfoManagerFragment());
        return mViews;
    }

    private List<String> getTit() {
        tab = new ArrayList<>();
        tab.add("用户信息");
        tab.add("商铺信息");
        tab.add("课程信息");
        tab.add("订单信息");
        return tab;
    }
}
