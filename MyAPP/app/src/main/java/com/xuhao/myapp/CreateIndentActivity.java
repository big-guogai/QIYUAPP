package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsSpinner;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.CourseInfoBean;
import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.adapter.CourseinfoViewPagerAdapter;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageUtiles;
import com.xuhao.myapp.utills.PayResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateIndentActivity extends AppCompatActivity {

    private int indentId;
    private ImageButton imageButtonBack;
    private TextView textViewBossName;
    private TextView textViewCourseName;
    private TextView textViewCourseUnitPrice;
    private TextView textViewBuyQuantity;
    private TextView textViewIndentAllPrice;
    private TextView textViewBossTelePhone;
    private RelativeLayout relativeLayoutPay;
    private Button buttonPay;
    private long bossTelephone;
    private long userId;
    private int bossId;
    private int courseId;
    private int buyCount;
    private String bossName;
    private String courseName;
    private double courseUnitprice;
    private double indentAllPrice;
    private String coursePriceType;
    private IndentInfoBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_indent);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        initView();
        initData();
    }

    private void initView() {
        imageButtonBack = findViewById(R.id.create_indent_back);
        textViewBossName = findViewById(R.id.create_indent_boss_name);
        textViewCourseName = findViewById(R.id.create_indent_course_name);
        textViewCourseUnitPrice = findViewById(R.id.create_indent_course_unit_price);
        textViewBuyQuantity = findViewById(R.id.create_indent_buy_quantity);
        textViewIndentAllPrice = findViewById(R.id.create_indent_all_price);
        textViewBossTelePhone = findViewById(R.id.create_indent_boss_telephone);
        buttonPay = findViewById(R.id.create_indent_btn_pay);
        relativeLayoutPay = findViewById(R.id.create_indent_layout_down_pay);
    }

    private void initData() {
        //下划线
        textViewBossTelePhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textViewBossTelePhone.getPaint().setAntiAlias(true);
        //电话咨询监听
        textViewBossTelePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent();
                intentCall.setAction(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + bossTelephone));
                startActivity(intentCall);
            }
        });
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sp.getLong("userId", 0);
        Intent intent = getIntent();
        indentId = intent.getIntExtra("indentId", 0);
        getIndentInfo();
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationAliPay();
            }
        });
    }

    /**
     * 页面生成获取订单基本信息
     */
    private void getIndentInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_INDENT_INFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        IndentInfoBean newDatas = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(s, IndentInfoBean.class);
                        if (newDatas != null) {
                            bean = newDatas;
                            bossId = bean.getBossid();
                            bossName = bean.getBossname();
                            courseName = bean.getCoursename();
                            courseUnitprice = bean.getCourseunitprice();
                            coursePriceType = bean.getCoursePriceType();
                            indentAllPrice = bean.getIndentprice();
                            buyCount = bean.getCoursequantity();
                            bossTelephone = bean.getBossTelePhone();
                            textViewBossTelePhone.setText(bossTelephone + "");
                            textViewBossName.setText(bossName);
                            textViewCourseName.setText(courseName);
                            textViewCourseUnitPrice.setText(courseUnitprice + "");
                            textViewBuyQuantity.setText(buyCount + "");
                            textViewIndentAllPrice.setText(indentAllPrice + "");
                            if (bean.getIndenttype().equals("待支付")){
                                relativeLayoutPay.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(CreateIndentActivity.this, "发生了意料之外的错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(CreateIndentActivity.this, "请检查网络后刷新页面！", Toast.LENGTH_SHORT).show();
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
     * 获取商品信息，调取支付接口
     */
    private void applicationAliPay() {
        //生成订单描述
        final String indentIntroduction = bossName + "-" + courseName + "-" + buyCount + coursePriceType;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.COURSE_BUY_ALIPAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        final String orderInfo = new Gson().fromJson(s, String.class);
                        if (!orderInfo.equals("")) {
                            final Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(CreateIndentActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo, true);
                                    Log.i("msp", result.toString());
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;
                                    handler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else {
                            Toast.makeText(CreateIndentActivity.this, "支付请求失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CreateIndentActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("price", indentAllPrice + "");
                map.put("indentIntroduction", indentIntroduction);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(CreateIndentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        paySuccessChangeType();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(CreateIndentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 支付成功后更改订单状态
     */
    private void paySuccessChangeType() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.PAY_SUCCESS_CHANGE_INDENT_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean ischange = new Gson().fromJson(s, Boolean.class);
                        if (ischange) {
                            finish();
                        } else {
                            paySuccessChangeType();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CreateIndentActivity.this, "网络错误！正在更新", Toast.LENGTH_SHORT).show();
                paySuccessChangeType();
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
