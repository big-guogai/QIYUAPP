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
import com.xuhao.myapp.Bean.BossInfoBean;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.ManagerBossRecycleViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BossInfoManagerFragment extends BaseLazyManagerFragment {

    private ManagerBossRecycleViewAdapter ad;
    private List<BossInfoBean> mBossInfoBean = new ArrayList<>();

    @Override
    protected void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_ALL_BOSS_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<BossInfoBean> newDatas = new Gson().fromJson(s, new TypeToken<List<BossInfoBean>>() {
                        }.getType());
                        mBossInfoBean.clear();
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mBossInfoBean.addAll(newDatas);
                            //按店铺ID进行排序
                            BossInfoBean bean;
                            for (int i = 0; i < mBossInfoBean.size(); i++) {
                                for (int j = 0; j < mBossInfoBean.size(); j++) {
                                    if (mBossInfoBean.get(j).getBossId() > mBossInfoBean.get(i).getBossId()){
                                        bean = mBossInfoBean.get(i);
                                        mBossInfoBean.set(i,mBossInfoBean.get(j));
                                        mBossInfoBean.set(j,bean);
                                    }
                                }
                            }
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.INVISIBLE);
                        } else {
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.VISIBLE);
                            textViewNullText.setText("暂无商铺信息");
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
        ad = new ManagerBossRecycleViewAdapter(getContext(),mBossInfoBean, R.layout.manager_boss_item);
        recyclerView.setAdapter(ad);
    }
}
