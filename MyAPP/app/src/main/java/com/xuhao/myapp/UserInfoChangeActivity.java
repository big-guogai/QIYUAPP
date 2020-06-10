package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuhao.myapp.Bean.UserInfoBean;
import com.xuhao.myapp.View.CircleImageView;
import com.xuhao.myapp.adapter.HeadChangeImgListAdapter;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageUtiles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserInfoChangeActivity extends AppCompatActivity {

    private static int year;
    private static int month;
    private static int day;
    private Button buttonEdit;
    private ImageButton imageButtonBack;
    private CircleImageView circleImageViewHead;
    private EditText editTextName;
    private EditText editTextAge;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMan;
    private RadioButton radioButtonWoman;

    private Window window;
    private View popView;
    private PopupWindow popupWindow;
    private Button buttonPopWindowCancle;
    private RecyclerView recyclerView;

    private List<String> headUrlList = new ArrayList<>();
    private HeadChangeImgListAdapter ad;
    private String headImgUrl = null;
    private String userSex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);
        initView();
        initData();

        //头像选择点击事件
        circleImageViewHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow(v);
            }
        });

        //返回按钮点击事件
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //年龄文本点击事件
        editTextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        //编辑按钮点击事件
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonEdit.getText().toString().equals("编辑")) {
                    buttonEdit.setText("完成");
                    circleImageViewHead.setEnabled(true);
                    editTextName.setEnabled(true);
                    editTextAge.setEnabled(true);
                    radioButtonMan.setEnabled(true);
                    radioButtonWoman.setEnabled(true);
                } else if (buttonEdit.getText().toString().equals("完成")) {
                    checkInfo();
                }
            }
        });
    }

    /**
     * popWindow初始化生成
     *
     * @param view
     */
    public void popWindow(View view) {
        //背景变暗
        window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.7f;
        window.setAttributes(params);

        popView = getLayoutInflater().inflate(R.layout.head_choose_popwindow, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable colorDrawable = new ColorDrawable();
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        recyclerView = popView.findViewById(R.id.head_choose_popwindow_img_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        getHeadListData();
        buttonPopWindowCancle = popView.findViewById(R.id.head_choose_popwindow_btn_canCle);
        buttonPopWindowCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (window != null) {
                    WindowManager.LayoutParams params1 = window.getAttributes();
                    params1.alpha = 1.0f;
                    window.setAttributes(params1);
                }
            }
        });
    }

    /**
     * 更改头像图片列表点击
     *
     * @param url 图片资源路径
     */
    public void popWindowSubmit(String url) {
        ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                circleImageViewHead.setImageBitmap(bitmap);
            }
        }, null);
        headImgUrl = url;
        popupWindow.dismiss();
    }

    /**
     * 获取头像图片列表
     */
    private void getHeadListData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.HEAD_URL_LEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<String> newDatas = new Gson().fromJson(s, new TypeToken<List<String>>() {
                        }.getType());
                        if (newDatas != null && !newDatas.isEmpty()) {
                            headUrlList.clear();
                            headUrlList.addAll(newDatas);
                            ad.notifyDataSetChanged();
                        } else {
                            headUrlList.clear();
                            ad.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                headUrlList.clear();
                ad.notifyDataSetChanged();
                Toast.makeText(UserInfoChangeActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        QiYuApplication.queue.add(stringRequest);
        ad = new HeadChangeImgListAdapter(UserInfoChangeActivity.this, headUrlList, R.layout.head_change_popwindow_item);
        recyclerView.setAdapter(ad);
    }

    private void initView() {
        buttonEdit = findViewById(R.id.user_info_change_edit);
        circleImageViewHead = findViewById(R.id.user_info_change_head_change);
        editTextAge = findViewById(R.id.user_info_change_age_change);
        editTextName = findViewById(R.id.user_info_change_name_change);
        radioGroup = findViewById(R.id.user_info_change_sex_change);
        radioButtonMan = findViewById(R.id.user_info_change_sex_man);
        radioButtonWoman = findViewById(R.id.user_info_change_sex_woman);
        imageButtonBack = findViewById(R.id.user_info_change_button_back);
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        headImgUrl = sp.getString("userHead", null);
        ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + sp.getString("userHead", null), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                circleImageViewHead.setImageBitmap(bitmap);
            }
        }, null);
        circleImageViewHead.setEnabled(false);
        editTextName.setText(sp.getString("userName", null));
        editTextName.setEnabled(false);
        editTextAge.setText(String.valueOf(sp.getInt("userAge", 0)));
        editTextAge.setEnabled(false);
        radioButtonMan.setEnabled(false);
        radioButtonWoman.setEnabled(false);
        String userSex = sp.getString("usersex", null);
        assert userSex != null;
        if (userSex.equals("男")) {
            radioButtonMan.setChecked(true);
        } else if (userSex.equals("女")) {
            radioButtonWoman.setChecked(true);
        }

    }

    /**
     * 日期选择器编辑年龄
     */
    private void showDatePicker() {
        if (year == 0) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(UserInfoChangeActivity.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            public void onDateSet(DatePicker datePicker, int myYear, int myMonth, int myDay) {
                UserInfoChangeActivity.year = myYear;
                UserInfoChangeActivity.month = myMonth;
                UserInfoChangeActivity.day = myDay;
                Calendar calendar = Calendar.getInstance();
                int nowyear = calendar.get(Calendar.YEAR);
                editTextAge.setText(Integer.toString(nowyear - myYear));
            }
        }, year, month, day).show();
    }


    /**
     * 检查表单数据输入
     */
    private void checkInfo() {
        if (String.valueOf((editTextName.getText())).trim().equals("")) {
            Toast.makeText(UserInfoChangeActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf((editTextAge.getText())).trim().equals("")) {
            Toast.makeText(UserInfoChangeActivity.this, "年龄不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            submitData();
        }
    }

    /**
     * 编辑完成提交数据
     */
    private void submitData() {
        if (radioButtonMan.isChecked()) {
            userSex = "男";
        } else {
            userSex = "女";
        }
        final SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_INFO_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isEdit = new Gson().fromJson(s, Boolean.class);
                        if (isEdit) {
                            Toast.makeText(UserInfoChangeActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
                            reLogin();
                        } else {
                            Toast.makeText(UserInfoChangeActivity.this, "修改失败请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserInfoChangeActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(sp.getLong("userId", 0)));
                map.put("userName", editTextName.getText().toString());
                map.put("userSex", userSex);
                map.put("userAge", editTextAge.getText().toString());
                map.put("userHead", headImgUrl);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 信息编辑成功重新登录
     */
    private void reLogin() {
        final SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_LOGIN_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        UserInfoBean bean = new Gson().fromJson(s, UserInfoBean.class);
                        if (bean != null) {
                            userInfosave(bean);
                            Toast.makeText(UserInfoChangeActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UserInfoChangeActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(UserInfoChangeActivity.this, "请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userid", String.valueOf(sp.getLong("userId", 0)));
                map.put("password", Objects.requireNonNull(sp.getString("userPwd", null)));
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 存储登录成功获取到的数据
     *
     * @param bean
     */
    private void userInfosave(UserInfoBean bean) {
        //用户基本信息存储
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
        editor.putString("userName", bean.getUsername());
        editor.putLong("userId", bean.getUserid());
        editor.putString("userPwd", bean.getUserpassword());
        editor.putInt("userAge", bean.getUserage());
        editor.putString("userHead", bean.getUserhead());
        editor.putString("usersex", bean.getUsersex());
        editor.putInt("userTypeId", bean.getUsertypeid());
        editor.apply();

        //用户上一次登录存储账号密码头像姓名
        SharedPreferences sp1 = getSharedPreferences("userLoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putLong("userId", bean.getUserid());
        editor1.putString("userPwd", bean.getUserpassword());
        editor1.putString("userHead", bean.getUserhead());
        editor1.putString("userName", bean.getUsername());
        editor1.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
