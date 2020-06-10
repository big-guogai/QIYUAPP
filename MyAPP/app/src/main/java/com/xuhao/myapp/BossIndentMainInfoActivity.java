package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.internet.URLManager;

import java.util.HashMap;
import java.util.Map;

public class BossIndentMainInfoActivity extends AppCompatActivity {

    private TextView textViewUserName;
    private TextView textViewUserId;
    private TextView textViewCourseName;
    private TextView textViewCourseUnitPrice;
    private TextView textViewCourseQuantity;
    private TextView textViewIndentPrice;
    private ImageButton imageButtonBack;
    private Button buttonCallPhone;
    private Button buttonHandleSuccess;
    private RelativeLayout relativeLayoutHandle;
    private int indentId;
    private long userTelePhone;
    private int callCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_indent_main_info);
        initView();
        initData();
    }

    private void initView() {
        imageButtonBack = findViewById(R.id.boss_indent_info_back);
        textViewUserName = findViewById(R.id.boss_indent_info_user_name);
        textViewUserId = findViewById(R.id.boss_indent_info_user_id);
        textViewCourseName = findViewById(R.id.boss_indent_info_course_name);
        textViewCourseUnitPrice = findViewById(R.id.boss_indent_info_course_unit_price);
        textViewCourseQuantity = findViewById(R.id.boss_indent_info_buy_quantity);
        textViewIndentPrice = findViewById(R.id.boss_indent_info_all_price);
        buttonCallPhone = findViewById(R.id.boss_indent_info_btn_call_phone);
        buttonHandleSuccess = findViewById(R.id.boss_indent_info_btn_handle_success);
        relativeLayoutHandle = findViewById(R.id.boss_indent_info_layout_down_handle);
    }

    private void initData() {
        relativeLayoutHandle.setVisibility(View.INVISIBLE);
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
        //电话确认点击事件
        buttonCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent();
                intentCall.setAction(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + userTelePhone));
                startActivity(intentCall);
                callCount++;
            }
        });
        //处理成功点击事件
        buttonHandleSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callCount >= 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(BossIndentMainInfoActivity.this).create();
                    alertDialog.setTitle("提示");
                    alertDialog.setIcon(R.drawable.ic_alert);
                    alertDialog.setMessage("请确认是否已处理咨询客户该订单");
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "还在沟通", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "处理结束", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handleSuccessChangeType();
                        }
                    });
                    alertDialog.show();
                } else {
                    Toast.makeText(BossIndentMainInfoActivity.this, "您确定您做了电话回访吗？！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 获取订单信息
     */
    private void getIndentInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_INDENT_INFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        IndentInfoBean newDatas = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(s, IndentInfoBean.class);
                        if (newDatas != null) {
                            userTelePhone = newDatas.getBuyuserid();
                            textViewUserName.setText(newDatas.getUsername());
                            textViewUserId.setText(newDatas.getBuyuserid() + "");
                            textViewCourseName.setText(newDatas.getCoursename());
                            textViewCourseUnitPrice.setText(newDatas.getCourseunitprice() + "");
                            textViewCourseQuantity.setText(newDatas.getCoursequantity() + "");
                            textViewIndentPrice.setText(newDatas.getIndentprice() + "");
                            if (newDatas.getIndenttype().equals("待处理")){
                                relativeLayoutHandle.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(BossIndentMainInfoActivity.this, "发生了意料之外的错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(BossIndentMainInfoActivity.this, "网络错误，请重试！！", Toast.LENGTH_SHORT).show();
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

    /**
     * 处理成功更改订单状态
     */
    private void handleSuccessChangeType() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.HANDLE_SUCCESS_CHANGE_INFENT_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean ischange = new Gson().fromJson(s, Boolean.class);
                        if (ischange) {
                            finish();
                        } else {
                            Toast.makeText(BossIndentMainInfoActivity.this, "发生意料之外的错误，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(BossIndentMainInfoActivity.this, "网络错误，请重试！！", Toast.LENGTH_SHORT).show();
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
