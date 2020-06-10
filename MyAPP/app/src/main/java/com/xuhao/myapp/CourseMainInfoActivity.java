package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.CourseInfoBean;
import com.xuhao.myapp.adapter.CourseinfoViewPagerAdapter;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.DataBaseHelper;
import com.xuhao.myapp.utills.HBDatabaseDao;
import com.xuhao.myapp.utills.ImageUtiles;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseMainInfoActivity extends AppCompatActivity {

    private long userId;
    private int bossId;
    private int courseid;
    private String coursePriceType;
    private Button buttonPay;
    private Button buttonQuantityPlus;
    private Button buttonQuantitySubStract;
    private TextView textPhoneCall;
    private ImageButton imageButtonback;
    private ViewPager viewPager;
    //定义一个存放获取到的图片集合
    private List<ImageView> a = new ArrayList<>();
    //定义获取图片变量循环
    private int i;
    //定义存放获取到的店铺信息集合
    List<CourseInfoBean> myInfoDatas = new ArrayList<>();
    CourseinfoViewPagerAdapter adapter;
    //定义价格显示文本
    private TextView textPrice;
    //定义课程名显示文本
    private TextView textCourseName;
    //定义店铺名按钮
    private TextView textBossName;
    //定义店铺信息显示文本
    private TextView textBossInfo;
    //定义店铺信息显示布局
    private LinearLayout layoutBossInfo;
    //定义课程信息显示文本
    private TextView textCourseInfo;
    //定义店铺位置信息显示文本
    private TextView textBossPosition;
    //定义店铺联系电话显示文本
    private TextView textBossTelePhone;
    //定义购买数量选择文本
    private TextView textBuyQuantity;
    //定义购买数量计数
    private TextView textBuyCount;
    //定义支付总金额显示文本
    private TextView textPayAllPrice;
    //课程单价
    private double courseUnitPrice = 0;
    //店铺信息标识
    private int coutBossInfo = 0;
    //静态点容器
    private LinearLayout point_container;
    private View point;

    //异步线程计数标识
    private int count = 0;
    //轮播图片计数
    private int countimg = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    --count;
                    if (count == 0) {
                        adapter = new CourseinfoViewPagerAdapter(CourseMainInfoActivity.this, a);
                        viewPager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main_info);
        initView();
        initData();
        updateHistoryBrowse();
    }

    /**
     * 页面创建插入课程ID更新历史浏览数据库
     */
    private void updateHistoryBrowse(){
        HBDatabaseDao db = new HBDatabaseDao(CourseMainInfoActivity.this);
        db.add(courseid);
    }

    private void initView() {
        viewPager = findViewById(R.id.course_main_info_imageView_viewPager);
        point_container = findViewById(R.id.guide_point_cont);
        textPrice = findViewById(R.id.course_main_info_price);
        textCourseName = findViewById(R.id.course_main_info_course_name);
        textBossName = findViewById(R.id.course_main_info_boss_name);
        textBossInfo = findViewById(R.id.course_main_info_boss_info);
        layoutBossInfo = findViewById(R.id.course_main_info_layout_boss_info);
        textCourseInfo = findViewById(R.id.course_main_info_course_info_text);
        textBossPosition = findViewById(R.id.course_main_info_boss_position_text);
        textBossTelePhone = findViewById(R.id.course_main_info_telephone_text);
        textBuyQuantity = findViewById(R.id.course_main_info_buy_quantity_type);
        textBuyCount = findViewById(R.id.course_main_info_buy_quantity_count);
        textPayAllPrice = findViewById(R.id.course_main_info_all_price_text);
        buttonPay = findViewById(R.id.course_main_info_pay_btn);

        //定义购买数量加按钮
        buttonQuantityPlus = findViewById(R.id.course_main_info_buy_quantity_plus);
        //定义购买数量减按钮
        buttonQuantitySubStract = findViewById(R.id.course_main_info_buy_quantity_substract);
        //定义电话咨询
        textPhoneCall = findViewById(R.id.course_main_info_telephone_call);
        //返回按钮
        imageButtonback = findViewById(R.id.course_main_info_up_back);
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sp.getLong("userId", 0);
        //下划线
        textPhoneCall.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textPhoneCall.getPaint().setAntiAlias(true);
        //viewpager绑定监听器
        viewPager.addOnPageChangeListener(pageChangeListener);
        //获取传送的课程ID
        Intent intent = getIntent();
        courseid = intent.getIntExtra("courseid", 1);
        //返回按钮点击事件
        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //店铺名点击触发店铺信息显示
        textBossName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coutBossInfo == 0) {
                    //店铺信息可见
                    layoutBossInfo.setVisibility(View.VISIBLE);
                    //店铺名收起靠右按钮
                    textBossName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_expand_less_black_24dp), null);
                    coutBossInfo = 1;
                } else {
                    //店铺信息不可见
                    layoutBossInfo.setVisibility(View.INVISIBLE);
                    //店铺名下拉靠右按钮
                    textBossName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_expand_more_black_24dp), null);
                    coutBossInfo = 0;
                }
            }
        });
        //购买数量加
        buttonQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(textBuyCount.getText().toString());
                if (i < 99) {
                    textBuyCount.setText(Integer.toString(i + 1));
                    //总价格
                    int j = ((int) courseUnitPrice) * Integer.parseInt(textBuyCount.getText().toString());
                    textPayAllPrice.setText(" " + Integer.toString(j));
                } else {
                    Toast.makeText(CourseMainInfoActivity.this, "已达购买数量上限！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //购买数量减
        buttonQuantitySubStract.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(textBuyCount.getText().toString());
                if (i > 1) {
                    textBuyCount.setText(Integer.toString(i - 1));
                    //总价格
                    int j = ((int) courseUnitPrice) * Integer.parseInt(textBuyCount.getText().toString());
                    textPayAllPrice.setText(" " + Integer.toString(j));
                } else {
                    Toast.makeText(CourseMainInfoActivity.this, "购买数量不可小于1！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //电话咨询监听
        textPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent();
                intentCall.setAction(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + textBossTelePhone.getText()));
                startActivity(intentCall);
            }
        });
        //购买按钮点击事件
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserTypePay();
            }
        });
        getData(courseid);
    }

    /**
     * 用户权限检查
     */
    private void checkUserTypePay() {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        switch (sp.getInt("userTypeId", 0)) {
            case 101: {
                createIndent();
                break;
            }
            case 102: {
                Toast.makeText(CourseMainInfoActivity.this, "店铺老板被禁止购买课程！", Toast.LENGTH_SHORT).show();
                break;
            }
            case 103: {
                Toast.makeText(CourseMainInfoActivity.this, "管理员被禁止购买课程！", Toast.LENGTH_SHORT).show();
                break;
            }
            case 1011: {
                createIndent();
                break;
            }
            case 1012: {
                createIndent();
                break;
            }
            case 0: {
                Toast.makeText(CourseMainInfoActivity.this, "请您先登录！", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
    }

    /**
     * 通过id获取课程全部信息
     *
     * @param courseid 课程ID
     */
    protected void getData(final int courseid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.COURSE_MAININFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        final List<CourseInfoBean> newDatas = new Gson().fromJson(s, new TypeToken<List<CourseInfoBean>>() {
                        }.getType());
                        if (newDatas != null && !newDatas.isEmpty()) {
                            myInfoDatas = newDatas;
                            bossId = myInfoDatas.get(0).getBossId();
                            courseUnitPrice = myInfoDatas.get(0).getCoursePrice();
                            textPrice.setText(" " + myInfoDatas.get(0).getCoursePrice() + " (" + myInfoDatas.get(0).getCoursePriceType() + ")");
                            textCourseName.setText(myInfoDatas.get(0).getCourseName());
                            textBossName.setText(myInfoDatas.get(0).getBossName());
                            textBossInfo.setText(myInfoDatas.get(0).getBossInfomation());
                            textCourseInfo.setText("\u3000\u3000" + myInfoDatas.get(0).getCourseInfomation());
                            textBossPosition.setText("\u3000\u3000" + myInfoDatas.get(0).getBossPosition());
                            textBossTelePhone.setText("\u3000\u3000" + myInfoDatas.get(0).getBossTelePhone());
                            textBuyQuantity.setText(textBuyQuantity.getText() + " (" + myInfoDatas.get(0).getCoursePriceType() + ")");
                            coursePriceType = myInfoDatas.get(0).getCoursePriceType();
                            textPayAllPrice.setText(" " + myInfoDatas.get(0).getCoursePrice());
                            //临时变量存储图片路径
                            String aa = null;
                            for (i = 1; i <= 5; i++) {

                                switch (i) {
                                    case 1:
                                        aa = myInfoDatas.get(0).getImgUrl1();
                                        break;
                                    case 2:
                                        aa = myInfoDatas.get(0).getImgUrl2();
                                        break;
                                    case 3:
                                        aa = myInfoDatas.get(0).getImgUrl3();
                                        break;
                                    case 4:
                                        aa = myInfoDatas.get(0).getImgUrl4();
                                        break;
                                    case 5:
                                        aa = myInfoDatas.get(0).getImgUrl5();
                                }
                                if (aa != null && !aa.equals("")) {
                                    count++;
                                    String bb = getEncoderUrl(aa);
                                    Log.i("第一张图片URL", URLManager.IMAGE_ADDRESS + bb);
                                    ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + bb, new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            ImageView b = new ImageView(CourseMainInfoActivity.this);
                                            b.setScaleType(ImageView.ScaleType.FIT_XY);
                                            b.setImageBitmap(bitmap);
                                            a.add(b);
                                            //图片计数+1
                                            countimg++;
                                            //小圆点对象
                                            point = new View(CourseMainInfoActivity.this);
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18, 18);
                                            params.leftMargin = 20;
                                            //第一张图默认选中
                                            if (countimg == 1) {
                                                point.setBackgroundResource(R.drawable.point_pressed);
                                            } else {
                                                point.setBackgroundResource(R.drawable.point_unpressed);
                                            }
                                            point_container.addView(point, params);
                                            handler.sendEmptyMessage(0);
                                        }
                                    }, null);
                                }
                            }
                        } else {
                            myInfoDatas.clear();
                            Toast.makeText(CourseMainInfoActivity.this, "暂无相关课程信息", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        myInfoDatas.clear();
                        Toast.makeText(CourseMainInfoActivity.this, "请检查网络后刷新页面！", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("courseid", Integer.toString(courseid));
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 中文路径转码
     *
     * @param url 获取到的路径
     * @return 转码后的路径
     */
    public String getEncoderUrl(String url) {
        //把路径按/分割成数组
        String[] urlArray = url.split("/");
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < urlArray.length; i++) {
            if (i == 0) {
                res.append(urlArray[0]);
            } else {
                //对路径转码
                try {
                    res.append(URLEncoder.encode(urlArray[i], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //转码后的字符串中间再加上斜杠线
            if (i < (urlArray.length - 1)) {
                res.append("/");
            }
        }

        return res.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * viewpager操作触发事件
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        /**
         * 换入新的viewpager页面触发
         * @param i viewpager页面位置标识
         */
        @Override
        public void onPageSelected(int i) {
            for (int j = 0; j < countimg; j++) {
                if ((i % countimg) == j) {
                    point_container.getChildAt(j).setBackgroundResource(R.drawable.point_pressed);
                } else {
                    point_container.getChildAt(j).setBackgroundResource(R.drawable.point_unpressed);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    /**
     * 点击购买生成订单页面跳转
     */
    private void createIndent() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.CREATE_INDENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        int indentid = new Gson().fromJson(s,int.class);
                        if (indentid != 0) {
                            Intent intent = new Intent(CourseMainInfoActivity.this, CreateIndentActivity.class);
                            intent.putExtra("indentId", indentid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CourseMainInfoActivity.this, "生成订单异常，请重试！", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CourseMainInfoActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                map.put("bossId", bossId + "");
                map.put("courseId", courseid + "");
                map.put("courseUnitPrice", courseUnitPrice + "");
                map.put("courseQuantity", String.valueOf(textBuyCount.getText()));
                map.put("indentPrice", String.valueOf(textPayAllPrice.getText()));
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
