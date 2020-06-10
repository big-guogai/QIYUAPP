package com.xuhao.myapp.fragment.managerkind;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhao.myapp.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public abstract class BaseLazyManagerFragment extends Fragment {

    private boolean isLoaded = false;
    protected RecyclerView recyclerView;
    public PtrClassicFrameLayout ptrClassicFrameLayout;
    protected View mView;
    protected RelativeLayout relativeLayoutNullText;
    protected TextView textViewNullText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_lazy_indent, container, false);
        mView = view;
        relativeLayoutNullText = view.findViewById(R.id.base_lazy_indent_text_layout);
        textViewNullText = view.findViewById(R.id.base_lazy_indent_text);
        recyclerView = view.findViewById(R.id.main_indent_kind_base_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        ptrClassicFrameLayout = view.findViewById(R.id.indent_kind_refresh);
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
        getData();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isLoaded && isVisibleToUser && getContext() != null) {
            isLoaded = true;
            getData();
        }
    }
    protected abstract void getData();

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
