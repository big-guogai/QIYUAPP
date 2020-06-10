package com.xuhao.myapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xuhao.myapp.R;

import java.util.Objects;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public abstract class BaseLazyFragment extends Fragment {

    private boolean isLoaded = false;
    protected RecyclerView mRecyclerView;
    protected View mRootview;
    protected FrameLayout frameLayout;
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    //根据地址搜索信息（默认成都市）
    protected String newdress = "成都市";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //判断是否加载过视图
        if (mRootview == null) {
            View view = inflater.inflate(R.layout.fragment_course_base, container, false);
            mRecyclerView = view.findViewById(R.id.main_course_kind_base_list);
            //recyleview显示2个item一行
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mRootview = view;
            getData();
        }

        IntentFilter filter = new IntentFilter("action_dress_change_refresh");
        Objects.requireNonNull(getActivity()).registerReceiver(bc, filter);
        //下拉刷新触发事件
        ptrClassicFrameLayout = mRootview.findViewById(R.id.course_kind_refresh);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                        getData();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLastUpdateTimeKey("2019-8-10");
                    }
                }, 2000);
            }
        });
        return mRootview;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isLoaded && isVisibleToUser && getContext() != null) {
            isLoaded = true;
            getData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 正确加载刷新背景色
     */
    protected void refresh_bgcolor() {
        ptrClassicFrameLayout.setBackgroundColor(this.getResources().getColor(R.color.white));
    }

    /**
     * 错误加载刷新背景色
     */
    protected void refresh_bgcolor_wrong() {
        ptrClassicFrameLayout.setBackgroundColor(this.getResources().getColor(R.color.hhh));
    }

    /**
     * 接收地址变更广播
     */
    protected BroadcastReceiver bc = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getData();
        }
    };

    protected abstract void getData();

    @Override
    public void onDestroy() {
        Objects.requireNonNull(getActivity()).unregisterReceiver(bc);
        super.onDestroy();
    }
}
