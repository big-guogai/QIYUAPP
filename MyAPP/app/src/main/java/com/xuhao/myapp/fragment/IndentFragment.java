package com.xuhao.myapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.MyShowPagerAdpter;
import com.xuhao.myapp.fragment.coursekind.CourseAllFragment;
import com.xuhao.myapp.fragment.coursekind.CourseAnotherFragment;
import com.xuhao.myapp.fragment.coursekind.CourseAratisticFragment;
import com.xuhao.myapp.fragment.coursekind.CourseChildFragment;
import com.xuhao.myapp.fragment.coursekind.CourseDesignFragment;
import com.xuhao.myapp.fragment.coursekind.CourseDiplomaFragment;
import com.xuhao.myapp.fragment.coursekind.CourseITFragment;
import com.xuhao.myapp.fragment.coursekind.CourseLanguageFragment;
import com.xuhao.myapp.fragment.coursekind.CourseSkillFragment;
import com.xuhao.myapp.fragment.coursekind.CourseTutorFragment;
import com.xuhao.myapp.fragment.indentkind.BossHandleIndentFragment;
import com.xuhao.myapp.fragment.indentkind.BossHistoryIndentFragment;
import com.xuhao.myapp.fragment.indentkind.HistoryIndentFragment;
import com.xuhao.myapp.fragment.indentkind.WaitCallBackIndentFragment;
import com.xuhao.myapp.fragment.indentkind.WaitPayIndentFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.CheckedOutputStream;

public class IndentFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager mIndentVp;
    private List<String> tab;
    private List<Fragment> mViews;
    private View view;
    private MyShowPagerAdpter myShowPagerAdpter;
    private int userTypeId;
    private int count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_indent, container, false);
        view = inflate;
        initView();
        initData();
        return inflate;
    }

    private void initView() {
        tabLayout = view.findViewById(R.id.indent_kind);
        mIndentVp = view.findViewById(R.id.indent_kind_viewPager);
    }

    private void initData() {
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userTypeId = sp.getInt("userTypeId", 0);
        myShowPagerAdpter = new MyShowPagerAdpter(getChildFragmentManager(), getmViews(), getTit());
        mIndentVp.setAdapter(myShowPagerAdpter);
        tabLayout.setupWithViewPager(mIndentVp);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onResume() {
        super.onResume();
        count++;
        if (count>1){
            myShowPagerAdpter.notifyDataSetChanged();
            initData();
        }
    }

    private List<Fragment> getmViews() {
        mViews = new ArrayList<>();
        //用户权限检查
        if (userTypeId == 102) {
            mViews.clear();
            mViews.add(new BossHistoryIndentFragment());
            mViews.add(new BossHandleIndentFragment());
        } else {
            mViews.clear();
            mViews.add(new HistoryIndentFragment());
            mViews.add(new WaitPayIndentFragment());
            mViews.add(new WaitCallBackIndentFragment());
        }
        return mViews;
    }

    private List<String> getTit() {
        tab = new ArrayList<>();
        //用户权限检查
        if (userTypeId == 102) {
            tab.clear();
            tab.add("历史订单");
            tab.add("待处理");
        } else {
            tab.clear();
            tab.add("历史订单");
            tab.add("待付款");
            tab.add("待回应");
        }
        return tab;
    }
}