package com.xuhao.myapp.fragment.managerkind;

import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.BossCourseInfoBean;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.ManagerCourseRecycleViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseInfoManagerFragment extends BaseLazyManagerFragment {
    private ManagerCourseRecycleViewAdapter ad;
    private List<BossCourseInfoBean> mBossCourseInfoBean = new ArrayList<>();

    @Override
    protected void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_ALL_COURSE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<BossCourseInfoBean> newDatas = new Gson().fromJson(s, new TypeToken<List<BossCourseInfoBean>>() {
                        }.getType());
                        mBossCourseInfoBean.clear();
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mBossCourseInfoBean.addAll(newDatas);
                            //按店铺ID进行排序
                            BossCourseInfoBean bean;
                            for (int i = 0; i < mBossCourseInfoBean.size(); i++) {
                                for (int j = 0; j < mBossCourseInfoBean.size(); j++) {
                                    if (mBossCourseInfoBean.get(j).getBossId() > mBossCourseInfoBean.get(i).getBossId()) {
                                        bean = mBossCourseInfoBean.get(i);
                                        mBossCourseInfoBean.set(i, mBossCourseInfoBean.get(j));
                                        mBossCourseInfoBean.set(j, bean);
                                    }
                                }
                            }
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.INVISIBLE);
                        } else {
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.VISIBLE);
                            textViewNullText.setText("暂无课程信息");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"网络错误，请重试",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        QiYuApplication.queue.add(stringRequest);
        ad = new ManagerCourseRecycleViewAdapter(getContext(),mBossCourseInfoBean, R.layout.manager_course_item);
        recyclerView.setAdapter(ad);
    }
}
