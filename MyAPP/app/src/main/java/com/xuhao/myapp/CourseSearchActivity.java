package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.CourseListBean;
import com.xuhao.myapp.adapter.CourseRecyclerViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class CourseSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imagebuttonback;
    private RecyclerView recyclerView;
    CourseRecyclerViewAdapter ad;
    List<CourseListBean> mRecycleDatas = new ArrayList<>();
    private SearchView msearchView;
    private RelativeLayout relativeLayout;
    private String newdress = "成都市";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);
        relativeLayout = findViewById(R.id.main_course_search_down);
        recyclerView = findViewById(R.id.main_course_search_recyclerview);
        msearchView = findViewById(R.id.main_course_search_search);
        //获取选择的地址
        Intent intent = getIntent();
        newdress = intent.getStringExtra("newdress");
        //recyleview显示2个item一行
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //搜索框是否显示提交按钮
        msearchView.setSubmitButtonEnabled(true);
        //键盘输入法回车设置搜索
        msearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //搜索框字体颜色大小
        int id = msearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = msearchView.findViewById(id);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setHintTextColor(getResources().getColor(R.color.gray));
        //搜索框提示
        msearchView.setQueryHint("请输入关键词");
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮触发方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query);
                //清除焦点
                msearchView.clearFocus();
                return false;
            }
            //搜索内容改变触发
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //返回按钮点击事件
        imagebuttonback = findViewById(R.id.main_course_search_back);
        imagebuttonback.setOnClickListener(this);
    }

    private void getData(final String searchkey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.COURSE_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<CourseListBean> newDatas = new Gson().fromJson(s, new TypeToken<List<CourseListBean>>() {
                        }.getType());
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mRecycleDatas.clear();
                            for (int i = 0; i < newDatas.size(); i++) {
                                mRecycleDatas.add(newDatas.get(i));
                            }
                            relativeLayout.setBackgroundColor(getResources().getColor(R.color.littlewhite));
                            ad.notifyDataSetChanged();
                        } else {
                            mRecycleDatas.clear();
                            ad.notifyDataSetChanged();
                            relativeLayout.setBackgroundResource(R.drawable.noinformation_bg);
                            Toast.makeText(CourseSearchActivity.this, "暂无相关课程信息", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mRecycleDatas.clear();
                        ad.notifyDataSetChanged();
                        relativeLayout.setBackgroundResource(R.drawable.errorloading_bg);
                        Toast.makeText(CourseSearchActivity.this, "请检查网络后重新搜索！", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("searchkey", searchkey);
                map.put("dress", newdress);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
        ad = new CourseRecyclerViewAdapter(this, mRecycleDatas, R.layout.course_kind_list_item);
        recyclerView.setAdapter(ad);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_course_search_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
