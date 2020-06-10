package com.xuhao.myapp.fragment.indentkind;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.xuhao.myapp.adapter.HistoryIndentRecycleViewAdapter;
import com.xuhao.myapp.internet.URLManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class WaitPayIndentFragment extends BaseLazyIndentFragment {
    private String userid;
    HistoryIndentRecycleViewAdapter ad;
    List<IndentInfoBean> mRecycleDetas = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void getData() {
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("userInfo", MODE_PRIVATE);
        if (sp.getLong("userId", 0) != 0) {
            userid = String.valueOf(sp.getLong("userId", 0));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.HISTORY_INDENT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            List<IndentInfoBean> newDatas = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(s, new TypeToken<List<IndentInfoBean>>() {
                            }.getType());
                            if (newDatas != null && !newDatas.isEmpty()) {
                                mRecycleDetas.clear();
                                for (int i = 0; i < newDatas.size(); i++) {
                                    if (newDatas.get(i).getIndenttype().equals("待支付")) {
                                        mRecycleDetas.add(newDatas.get(i));
                                    }
                                }
                                IndentInfoBean bean;
                                for (int i = 0; i < mRecycleDetas.size(); i++) {
                                    for (int j = 0; j < mRecycleDetas.size(); j++) {
                                        if (mRecycleDetas.get(j).getIndenttime().getTime() < mRecycleDetas.get(i).getIndenttime().getTime()){
                                            bean = mRecycleDetas.get(i);
                                            mRecycleDetas.set(i,mRecycleDetas.get(j));
                                            mRecycleDetas.set(j,bean);
                                        }
                                    }
                                }
                                if(mRecycleDetas.size() == 0){
                                    relativeLayoutNullText.setVisibility(View.VISIBLE);
                                    textViewNullText.setText("无未支付订单");
                                }else {
                                    relativeLayoutNullText.setVisibility(View.INVISIBLE);
                                }
                                ad.notifyDataSetChanged();
                            } else {
                                relativeLayoutNullText.setVisibility(View.VISIBLE);
                                textViewNullText.setText("无订单记录");
                                mRecycleDetas.clear();
                                ad.notifyDataSetChanged();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    mRecycleDetas.clear();
                    ad.notifyDataSetChanged();
                    Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", userid);
                    return map;
                }
            };
            QiYuApplication.queue.add(stringRequest);
            ad = new HistoryIndentRecycleViewAdapter(getContext(), mRecycleDetas, R.layout.indent_history_item);
            recyclerView.setAdapter(ad);
        }else {
            relativeLayoutNullText.setVisibility(View.VISIBLE);
            textViewNullText.setText("请登录后查看");
        }
    }
}
