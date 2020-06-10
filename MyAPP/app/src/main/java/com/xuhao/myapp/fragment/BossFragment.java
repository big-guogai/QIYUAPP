package com.xuhao.myapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.BossCourseInfoBean;
import com.xuhao.myapp.Bean.BossInfoBean;
import com.xuhao.myapp.BossCourseAddActivity;
import com.xuhao.myapp.BossMainInfoActivity;
import com.xuhao.myapp.BossRegisteredActivity;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.BossCourseRecyleViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BossFragment extends Fragment {
    TextView textViewBossRegistered;
    private RelativeLayout relativeLayoutBossHave;
    private TextView textViewBossName;
    private BossInfoBean bean;
    private RecyclerView recyclerView;
    BossCourseRecyleViewAdapter ad;
    List<BossCourseInfoBean> mRecycleDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_boss, container, false);
        relativeLayoutBossHave = inflate.findViewById(R.id.boss_layout_down);
        textViewBossName = inflate.findViewById(R.id.boss_boss_name);
        recyclerView = inflate.findViewById(R.id.boss_layout_down_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        //店铺信息点击事件
        TextView textViewBossInformation = inflate.findViewById(R.id.boss_boss_information);
        textViewBossInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BossMainInfoActivity.class);
                intent.putExtra("bossId", bean.getBossId());
                intent.putExtra("bossName", bean.getBossName());
                intent.putExtra("bossInformation", bean.getBossInformation());
                intent.putExtra("bossPosition", bean.getBossPosition());
                intent.putExtra("bossTelephone", bean.getBossTelephone());
                intent.putExtra("bossReceiptType", bean.getBossReceiptType());
                intent.putExtra("bossReceiptId", bean.getBossReceiptId());
                startActivity(intent);
            }
        });

        //注册店铺点击事件
        textViewBossRegistered = inflate.findViewById(R.id.boss_click_update_boss);
        textViewBossRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BossRegisteredActivity.class);
                startActivity(intent);
            }
        });

        //添加课程商品点击事件
        Button buttonAddCourse = inflate.findViewById(R.id.boss_layout_down_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BossCourseAddActivity.class);
                intent.putExtra("bossId", bean.getBossId());
                startActivity(intent);
            }
        });
        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginLevel();
    }

    /**
     * 查看账号等级显示相应信息
     */
    private void loginLevel() {
        SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getInt("userTypeId", 0) == 101) { //权限为未拥有店铺的普通用户
            textViewBossRegistered.setText(R.string.click_update_boss);
            textViewBossRegistered.setEnabled(true);
            relativeLayoutBossHave.setVisibility(View.INVISIBLE);
            textViewBossRegistered.setVisibility(View.VISIBLE);
        } else if (sp.getInt("userTypeId", 0) == 102) { //权限为店铺的显示内容
            relativeLayoutBossHave.setVisibility(View.VISIBLE);
            textViewBossRegistered.setVisibility(View.INVISIBLE);
            getBossInfo();
        } else if (sp.getInt("userTypeId", 0) == 1011) { //权限为提交了审核信息的用户
            textViewBossRegistered.setText("您已上传店铺申请，请等待审核");
            textViewBossRegistered.setEnabled(false);
            relativeLayoutBossHave.setVisibility(View.INVISIBLE);
            textViewBossRegistered.setVisibility(View.VISIBLE);
        } else if (sp.getInt("userTypeId", 0) == 1012) { //权限为申请被驳回的用户
            textViewBossRegistered.setText("您的申请已被驳回，请检查信息正确并合法之后再次点击申请或联系客服咨询");
            textViewBossRegistered.setEnabled(true);
            relativeLayoutBossHave.setVisibility(View.INVISIBLE);
            textViewBossRegistered.setVisibility(View.VISIBLE);
        } else {
            textViewBossRegistered.setVisibility(View.VISIBLE);
            textViewBossRegistered.setText("登录后才能查看您的店铺信息！");
            relativeLayoutBossHave.setVisibility(View.INVISIBLE);
            textViewBossRegistered.setEnabled(false);
        }
    }

    /**
     * 权限为店铺时获得的店铺信息
     */
    private void getBossInfo() {
        SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String userId = String.valueOf(sp.getLong("userId", 0));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_BOSS_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        bean = new Gson().fromJson(s, BossInfoBean.class);
                        textViewBossName.setText(bean.getBossName());
                        getBossCourseInfo();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 权限为店铺获取的拥有的课程信息
     */
    private void getBossCourseInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_BOSS_COURSE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<BossCourseInfoBean> newDatas = new Gson().fromJson(s, new TypeToken<List<BossCourseInfoBean>>() {
                        }.getType());
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mRecycleDatas.clear();
                            mRecycleDatas.addAll(newDatas);
                            ad.notifyDataSetChanged();
                        } else {
                            mRecycleDatas.clear();
                            ad.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mRecycleDatas.clear();
                ad.notifyDataSetChanged();
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("bossId", String.valueOf(bean.getBossId()));
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
        ad = new BossCourseRecyleViewAdapter(getContext(), mRecycleDatas, R.layout.boss_course_list_item);
        recyclerView.setAdapter(ad);
    }
}