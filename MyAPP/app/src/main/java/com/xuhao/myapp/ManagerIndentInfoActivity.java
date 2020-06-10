package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;
import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.internet.URLManager;

import java.util.HashMap;
import java.util.Map;

public class ManagerIndentInfoActivity extends AppCompatActivity {

    private TextView textViewIndentId;
    private TextView textViewBossId;
    private TextView textViewUserId;
    private TextView textViewCourseId;
    private TextView textViewCourseUnitPrice;
    private TextView textViewCourseQuantity;
    private TextView textViewIndentPrice;
    private TextView textViewIndentTime;
    private TextView textViewIndentType;
    private ImageButton imageButtonBack;
    private int indentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_indent_info);
        initView();
        initData();
    }

    private void initView() {
        textViewIndentId = findViewById(R.id.manager_indent_info_indent_id);
        textViewBossId = findViewById(R.id.manager_indent_info_boss_id);
        textViewCourseId = findViewById(R.id.manager_indent_info_course_id);
        textViewUserId = findViewById(R.id.manager_indent_info_user_id);
        textViewCourseUnitPrice = findViewById(R.id.manager_indent_info_course_unit_price);
        textViewCourseQuantity = findViewById(R.id.manager_indent_info_course_quantity);
        textViewIndentPrice = findViewById(R.id.manager_indent_info_indent_price);
        textViewIndentTime = findViewById(R.id.manager_indent_info_indent_time);
        textViewIndentType = findViewById(R.id.manager_indent_info_indent_type);
        imageButtonBack = findViewById(R.id.manager_indent_info_back);
    }

    private void initData() {
        Intent intent = getIntent();
        indentId = intent.getIntExtra("indentId", 0);
        getIndentInfo();
        //返回按钮点击事件
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 页面加载获取全部订单信息
     */
    private void getIndentInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_ONE_INDENT_INFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        IndentInfoBean newDatas = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(s, IndentInfoBean.class);
                        if (newDatas != null) {
                            textViewIndentId.setText(newDatas.getIndentid() + "");
                            textViewBossId.setText(newDatas.getBossid() + "");
                            textViewUserId.setText(newDatas.getBuyuserid() + "");
                            textViewCourseId.setText(newDatas.getCourseid() + "");
                            textViewCourseUnitPrice.setText(newDatas.getCourseunitprice() + "");
                            textViewCourseQuantity.setText(newDatas.getCoursequantity() + "");
                            textViewIndentPrice.setText(newDatas.getIndentprice() + "");
                            //日期格式从数据库取出格式出错，暂时使用字符串截取显示
                            String strTime = String.valueOf(newDatas.getIndenttime());
                            textViewIndentTime.setText(strTime.substring(0, strTime.indexOf(".")));
                            textViewIndentType.setText(newDatas.getIndenttype());
                        } else {
                            getIndentInfo();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerIndentInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("indentId", indentId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
