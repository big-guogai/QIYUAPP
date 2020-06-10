package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.Bean.UserInfoBean;
import com.xuhao.myapp.View.CircleImageView;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageUtiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextUserID;
    private TextInputEditText textInputEditTextUserPwd;
    private TextInputLayout textInputLayoutUserID;
    private TextInputLayout textInputLayoutUserPwd;
    private Button buttonLogin;
    private CircleImageView circleImageViewLoginingHead;
    private TextView textViewLoginingUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        circleImageViewLoginingHead = findViewById(R.id.login_head);
        textViewLoginingUserName = findViewById(R.id.login_user_name_text);
        textInputEditTextUserID = findViewById(R.id.login_user_id);
        textInputEditTextUserPwd = findViewById(R.id.login_user_password);
        textInputLayoutUserID = findViewById(R.id.login_layout_user_id);
        textInputLayoutUserPwd = findViewById(R.id.login_layout_user_password);

        //手机号验证错误文本改变去除错误提示
        TextWatcher textWatcherID = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutUserID.setErrorEnabled(false);
                SharedPreferences sp = getSharedPreferences("userLoginInfo", MODE_PRIVATE);
                String a = String.valueOf((sp.getLong("userId", 0)));
                String b = String.valueOf(textInputEditTextUserID.getText());
                if (!b.equals(a)) {
                    circleImageViewLoginingHead.setImageResource(R.mipmap.head);
                    textViewLoginingUserName.setText("暂未登陆");
                    textInputEditTextUserPwd.setText("");
                } else {
                    ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + sp.getString("userHead", null), new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap bitmap) {
                            circleImageViewLoginingHead.setImageBitmap(bitmap);
                        }
                    }, null);
                    String username = sp.getString("userName", null);
                    textViewLoginingUserName.setText(username);
                    textInputEditTextUserPwd.setText(sp.getString("userPwd", null));
                }
            }
        };
        textInputEditTextUserID.addTextChangedListener(textWatcherID);
        //密码验证错误文本改变去除错误提示
        TextWatcher textWatcherPwd = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutUserPwd.setErrorEnabled(false);
            }
        };
        textInputEditTextUserPwd.addTextChangedListener(textWatcherPwd);

        //返回按钮点击事件
        ImageButton imageButtonback = findViewById(R.id.login_button_back);
        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //定义注册跳转按钮
        TextView textregistered = findViewById(R.id.login_not_registered);
        //下划线
        textregistered.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textregistered.getPaint().setAntiAlias(true);
        //注册跳转点击事件
        textregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
            }
        });

        //登录按钮触发事件
        buttonLogin = findViewById(R.id.login_button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginFormat();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLastLoginInfo();
    }

    /**
     * 判断输入格式并做登录访问
     */
    private void checkLoginFormat() {
        if (isRightPhone(Objects.requireNonNull(textInputEditTextUserID.getText()).toString())) {
            if (textInputEditTextUserPwd.length() >= 6) {
                checkData(textInputEditTextUserID.getText().toString().trim(), textInputEditTextUserPwd.getText().toString().trim());
            } else {
                textInputLayoutUserPwd.setError("密码必须不少于六位数！");
            }
        } else {
            textInputLayoutUserID.setError("请仔细检查手机号格式是否正确！");
        }
    }

    /**
     * 判断手机号码格式
     *
     * @param id 输入的手机号码
     * @return boolean
     */
    private static boolean isRightPhone(String id) {
        Pattern p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
        Matcher m = p.matcher(id);
        return m.matches();
    }

    /**
     * 用户登录数据获取，获取到数据表明输入正确
     *
     * @param userid   手机号
     * @param password 密码
     */
    private void checkData(final String userid, final String password) {
        buttonLogin.setText("登录中");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_LOGIN_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        UserInfoBean bean = new Gson().fromJson(s, UserInfoBean.class);
                        if (bean != null) {
                            userInfosave(bean);
                            Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                            buttonLogin.setText("登录");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        buttonLogin.setText("登录");
                        Toast.makeText(LoginActivity.this, "请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userid", userid);
                map.put("password", password);
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

    /**
     * 展示用户上一次的信息
     */
    private void getLastLoginInfo() {
        SharedPreferences sp = getSharedPreferences("userLoginInfo", Context.MODE_PRIVATE);
        //判断是否登录后存储了用户信息
        if (sp.getLong("userId", 0) != 0) {
            ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + sp.getString("userHead", null), new Response.Listener<Bitmap>() {

                @Override
                public void onResponse(Bitmap bitmap) {
                    circleImageViewLoginingHead.setImageBitmap(bitmap);
                }
            }, null);
            String username = sp.getString("userName", null);
            textViewLoginingUserName.setText(username);
            String userid = Long.toString(sp.getLong("userId", 0));
            textInputEditTextUserID.setText(userid);
            textInputEditTextUserPwd.setText(sp.getString("userPwd", null));
        } else {
            circleImageViewLoginingHead.setImageResource(R.mipmap.head);
            textViewLoginingUserName.setText("暂未登录");
            textInputEditTextUserID.setText("");
            textInputEditTextUserPwd.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
