package com.xuhao.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.xuhao.myapp.utills.HBDatabaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryBrowseActivity extends AppCompatActivity {

    private TextView textViewNullWarnning;
    private ImageButton imageButtonBack;
    private RecyclerView recyclerView;
    private List<Integer> list = new ArrayList<>();
    CourseRecyclerViewAdapter ad;
    List<CourseListBean> mRecycleDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_browse);
        initView();
        initData();
        getHBDatabase();
        getData();
    }

    private void initView() {
        imageButtonBack = findViewById(R.id.history_browse_back);
        recyclerView = findViewById(R.id.history_browse_recyclerview);
        textViewNullWarnning = findViewById(R.id.history_browse_null_warning);
    }

    private void initData() {
        //返回按钮点击事件
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //recyleview显示2个item一行
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    /**
     * 获取历史浏览数据库信息
     */
    private void getHBDatabase() {
        HBDatabaseDao hbDatabaseDao = new HBDatabaseDao(HistoryBrowseActivity.this);
        list = hbDatabaseDao.query();
    }

    private void getData() {
        if (list != null && !list.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i)).append(" ");
            }
            final String s = sb.toString();
            textViewNullWarnning.setVisibility(View.GONE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_HISTORY_BROWSE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            List<CourseListBean> newDatas = new Gson().fromJson(s,new TypeToken<List<CourseListBean>>() {
                            }.getType());
                            if (newDatas != null && !newDatas.isEmpty()){
                                mRecycleDatas.clear();
                                mRecycleDatas.addAll(newDatas);
                                ad.notifyDataSetChanged();
                            }else {
                                mRecycleDatas.clear();
                                ad.notifyDataSetChanged();
                                textViewNullWarnning.setVisibility(View.GONE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    mRecycleDatas.clear();
                    ad.notifyDataSetChanged();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("HBCourseId",s);
                    return map;
                }
            };
            QiYuApplication.queue.add(stringRequest);
            ad = new CourseRecyclerViewAdapter(HistoryBrowseActivity.this, mRecycleDatas, R.layout.course_kind_list_item);
            recyclerView.setAdapter(ad);
        } else {
            textViewNullWarnning.setVisibility(View.VISIBLE);
        }
    }
}
