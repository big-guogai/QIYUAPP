package com.xuhao.myapp.fragment.managerkind;

import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.ManagerIndentRecycleViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndentInfoManagerFragment extends BaseLazyManagerFragment {
    private ManagerIndentRecycleViewAdapter ad;
    private List<IndentInfoBean> mIndentInfoBean = new ArrayList<>();

    @Override
    protected void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_ALL_INDENT_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<IndentInfoBean> newDatas = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(s, new TypeToken<List<IndentInfoBean>>() {
                        }.getType());
                        mIndentInfoBean.clear();
                        if (newDatas != null && !newDatas.isEmpty()) {
                            mIndentInfoBean.addAll(newDatas);
                            //按订单生成时间进行排序
                            IndentInfoBean bean;
                            for (int i = 0; i < mIndentInfoBean.size(); i++) {
                                for (int j = 0; j < mIndentInfoBean.size(); j++) {
                                    if (mIndentInfoBean.get(j).getIndenttime().getTime() < mIndentInfoBean.get(i).getIndenttime().getTime()) {
                                        bean = mIndentInfoBean.get(i);
                                        mIndentInfoBean.set(i, mIndentInfoBean.get(j));
                                        mIndentInfoBean.set(j, bean);
                                    }
                                }
                            }
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.INVISIBLE);
                        } else {
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.VISIBLE);
                            textViewNullText.setText("暂无已支付订单信息");
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
        ad = new ManagerIndentRecycleViewAdapter(getContext(), mIndentInfoBean, R.layout.manager_indent_item);
        recyclerView.setAdapter(ad);
    }
}
