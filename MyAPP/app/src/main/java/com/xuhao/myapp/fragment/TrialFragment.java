package com.xuhao.myapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.CheckBossInfoBean;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.TrialRecyclerViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrialFragment extends Fragment {

    private TextView textViewNoApplication;
    private RecyclerView recyclerView;
    private TrialRecyclerViewAdapter ad;
    private List<CheckBossInfoBean> mRecycleDatas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trial, container, false);
        textViewNoApplication = view.findViewById(R.id.trial_text_no_boss_application);
        recyclerView = view.findViewById(R.id.trial_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 获取店铺申请表单
     */
    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.MANAGER_TRIAL_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<CheckBossInfoBean> newDatas = new Gson().fromJson(s, new TypeToken<List<CheckBossInfoBean>>() {
                        }.getType());
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mRecycleDatas.clear();
                            mRecycleDatas.addAll(newDatas);
                            ad.notifyDataSetChanged();
                            textViewNoApplication.setVisibility(View.GONE);
                        } else {
                            mRecycleDatas.clear();
                            ad.notifyDataSetChanged();
                            textViewNoApplication.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mRecycleDatas.clear();
                ad.notifyDataSetChanged();
                Toast.makeText(getContext(), "错误", Toast.LENGTH_SHORT).show();
                textViewNoApplication.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        QiYuApplication.queue.add(stringRequest);
        ad = new TrialRecyclerViewAdapter(getContext(), mRecycleDatas, R.layout.trial_list_item);
        recyclerView.setAdapter(ad);
    }
}
