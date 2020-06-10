package com.xuhao.myapp.fragment.coursekind;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.CourseRecyclerViewAdapter;
import com.xuhao.myapp.Bean.CourseListBean;
import com.xuhao.myapp.fragment.BaseLazyFragment;
import com.xuhao.myapp.fragment.CourseFragment;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CourseTutorFragment extends BaseLazyFragment {

    CourseRecyclerViewAdapter ad;
    List<CourseListBean> mRecycleDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        frameLayout = view.findViewById(R.id.course_kind_base_bg);
        return view;
    }

    @Override
    protected void getData() {
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("userDress",Context.MODE_PRIVATE);
        newdress = sp.getString("newdress","成都市");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.COURSE_KIND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<CourseListBean> newDatas = new Gson().fromJson(s, new TypeToken<List<CourseListBean>>() {
                        }.getType());
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mRecycleDatas.clear();
                            mRecycleDatas.addAll(newDatas);
                            refresh_bgcolor();
                            ad.notifyDataSetChanged();
                        } else {
                            mRecycleDatas.clear();
                            refresh_bgcolor_wrong();
                            ad.notifyDataSetChanged();
                            frameLayout.setBackgroundResource(R.drawable.noinformation_bg);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mRecycleDatas.clear();
                        ad.notifyDataSetChanged();
                        refresh_bgcolor_wrong();
                        frameLayout.setBackgroundResource(R.drawable.errorloading_bg);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("typeid", "201");
                map.put("dress", newdress);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
        ad = new CourseRecyclerViewAdapter(getContext(), mRecycleDatas, R.layout.course_kind_list_item);
        mRecyclerView.setAdapter(ad);
    }
}
