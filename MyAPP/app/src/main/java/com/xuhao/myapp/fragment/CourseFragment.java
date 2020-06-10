package com.xuhao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xuhao.myapp.LoginActivity;
import com.xuhao.myapp.MainActivity;
import com.xuhao.myapp.R;
import com.xuhao.myapp.UserInfoChangeActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CourseFragment extends Fragment {

    private Button dresscity;
    private ViewPager coursekindvp;
    private TabLayout tabLayout;
    private List<String> tab;
    private List<Fragment> mViews;
    private MyShowPagerAdpter myShowPagerAdpter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course, container, false);
        //注册接收地址数据的广播接收者
        IntentFilter filter = new IntentFilter("action_update_fragment_button_text");
        Objects.requireNonNull(getActivity()).registerReceiver(bc, filter);

        tabLayout = view.findViewById(R.id.main_course_kind);
        coursekindvp = view.findViewById(R.id.main_course_viewpager);

        myShowPagerAdpter = new MyShowPagerAdpter(getActivity().getSupportFragmentManager(), getmViews(), getTit());
        coursekindvp.setAdapter(myShowPagerAdpter);
        tabLayout.setupWithViewPager(coursekindvp);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //头像点击事件
        view.findViewById(R.id.main_course_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                if (sp.getLong("userId", 0) == 0) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    Toast.makeText(getContext(), "您还没有登录呢，先登录吧！", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), UserInfoChangeActivity.class);
                    startActivity(intent);
                }
            }
        });

        dresscity = view.findViewById(R.id.main_course_dress);
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("userDress",Context.MODE_PRIVATE);
        dresscity.setText(sp.getString("newdress","成都市"));
        //地区选择点击事件
        dresscity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.dressPick();
            }
        });
        view.findViewById(R.id.main_course_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.courseSearch((String) dresscity.getText());
            }
        });
        return view;
    }

    private List<Fragment> getmViews() {
        mViews = new ArrayList<>();
        mViews.add(new CourseAllFragment());
        mViews.add(new CourseTutorFragment());
        mViews.add(new CourseChildFragment());
        mViews.add(new CourseLanguageFragment());
        mViews.add(new CourseSkillFragment());
        mViews.add(new CourseITFragment());
        mViews.add(new CourseDesignFragment());
        mViews.add(new CourseAratisticFragment());
        mViews.add(new CourseDiplomaFragment());
        mViews.add(new CourseAnotherFragment());
        return mViews;
    }

    private List<String> getTit() {
        tab = new ArrayList<>();
        tab.add("全部");
        tab.add("家教");
        tab.add("幼儿");
        tab.add("语言");
        tab.add("技能");
        tab.add("IT");
        tab.add("设计");
        tab.add("艺体");
        tab.add("学历");
        tab.add("其他");
        return tab;
    }

    private BroadcastReceiver bc = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            dresscity.setText(data);
            //地址变更发送广播刷新页面并临时存储新地址
            SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("userDress",Context.MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
            editor.putString("newdress", data);
            editor.apply();
            String action_dress_refresh = "action_dress_change_refresh";
            Intent intentString = new Intent(action_dress_refresh);
            Objects.requireNonNull(getActivity()).sendBroadcast(intentString);
        }
    };

    @Override
    public void onDestroy() {
        //页面销毁时取消注册BroadcastReceiver
        Objects.requireNonNull(getActivity()).unregisterReceiver(bc);
        super.onDestroy();
    }
}