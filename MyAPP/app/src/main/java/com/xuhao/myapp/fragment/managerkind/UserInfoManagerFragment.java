package com.xuhao.myapp.fragment.managerkind;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.UserInfoBean;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.adapter.ManagerUserRecycleViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserInfoManagerFragment extends BaseLazyManagerFragment {
    private List<UserInfoBean> mUserInfoBean = new ArrayList<>();
    private ManagerUserRecycleViewAdapter ad;

    @Override
    protected void getData() {
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final long userId = sp.getLong("userId", 0);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_ALL_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<UserInfoBean> newDatas = new Gson().fromJson(s, new TypeToken<List<UserInfoBean>>() {
                        }.getType());
                        mUserInfoBean.clear();
                        if (newDatas != null && !newDatas.isEmpty()) {
                            for (int i = 0; i < newDatas.size(); i++) {
                                if (newDatas.get(i).getUserid() != userId) {
                                    mUserInfoBean.add(newDatas.get(i));
                                }
                            }
                            //按手机号进行排序
                            UserInfoBean bean;
                            for (int i = 0; i < mUserInfoBean.size(); i++) {
                                for (int j = 0; j < mUserInfoBean.size(); j++) {
                                    if (mUserInfoBean.get(j).getUserid() > mUserInfoBean.get(i).getUserid()){
                                        bean = mUserInfoBean.get(i);
                                        mUserInfoBean.set(i,mUserInfoBean.get(j));
                                        mUserInfoBean.set(j,bean);
                                    }
                                }
                            }
                            ad.notifyDataSetChanged();
                            if (mUserInfoBean.size() == 0){
                                relativeLayoutNullText.setVisibility(View.VISIBLE);
                                textViewNullText.setText("暂无其余用户信息");
                            }else {
                                relativeLayoutNullText.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            ad.notifyDataSetChanged();
                            relativeLayoutNullText.setVisibility(View.VISIBLE);
                            textViewNullText.setText("暂无其余用户信息");
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
        ad = new ManagerUserRecycleViewAdapter(getContext(),mUserInfoBean, R.layout.manager_user_item);
        recyclerView.setAdapter(ad);
    }
}
