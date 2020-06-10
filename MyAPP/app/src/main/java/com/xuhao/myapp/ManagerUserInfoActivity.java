package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class ManagerUserInfoActivity extends AppCompatActivity {

    private CircleImageView circleImageViewHead;
    private EditText editTextUserName;
    private TextView textViewUserId;
    private EditText editTextUserPwd;
    private RadioButton radioButtonMan;
    private RadioButton radioButtonWoman;
    private EditText editTextUserAge;
    private TextView textViewUserType;
    private Button buttonDelete;
    private Button buttonChange;
    private ImageButton imageButtonBack;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_info);
        initView();
        initData();
    }

    private void initView() {
        circleImageViewHead = findViewById(R.id.manager_user_info_user_head);
        editTextUserName = findViewById(R.id.manager_user_info_user_name);
        textViewUserId = findViewById(R.id.manager_user_info_user_id);
        editTextUserPwd = findViewById(R.id.manager_user_info_user_pwd);
        radioButtonMan = findViewById(R.id.manager_user_info_sex_man);
        radioButtonWoman = findViewById(R.id.manager_user_info_sex_woman);
        editTextUserAge = findViewById(R.id.manager_user_info_user_age);
        textViewUserType = findViewById(R.id.manager_user_info_user_type);
        buttonDelete = findViewById(R.id.manager_user_info_btn_delete);
        buttonChange = findViewById(R.id.manager_user_info_btn_change_info);
        imageButtonBack = findViewById(R.id.manager_user_info_back);
    }

    private void initData() {
        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 0);
        getUserInfo();
        //默认无法选中更改
        circleImageViewHead.setEnabled(false);
        editTextUserName.setEnabled(false);
        editTextUserPwd.setEnabled(false);
        editTextUserAge.setEnabled(false);
        radioButtonMan.setEnabled(false);
        radioButtonWoman.setEnabled(false);
        //返回按钮点击事件
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //删除账号点击事件
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ManagerUserInfoActivity.this).create();
                alertDialog.setTitle("提示");
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setMessage("请确认是否删除账号！(将会删除该账号所有相关信息)");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUserInfo();
                    }
                });
                alertDialog.show();
            }
        });
        //更改信息点击事件
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonChange.getText().equals("信息更改")) {
                    buttonChange.setText("完成");
                    buttonDelete.setEnabled(false);
                    editTextUserName.setEnabled(true);
                    editTextUserPwd.setEnabled(true);
                    editTextUserAge.setEnabled(true);
                    radioButtonMan.setEnabled(true);
                    radioButtonWoman.setEnabled(true);
                } else {
                    infoCheck();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }

    /**
     * 初始加载获取该条帐号全部信息
     */
    private void getUserInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_USER_INFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        UserInfoBean newDatas = new Gson().fromJson(s, UserInfoBean.class);
                        if (newDatas != null) {
                            ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + newDatas.getUserhead(),
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            circleImageViewHead.setImageBitmap(bitmap);
                                        }
                                    }, null);
                            editTextUserName.setText(newDatas.getUsername());
                            textViewUserId.setText(newDatas.getUserid() + "");
                            editTextUserPwd.setText(newDatas.getUserpassword());
                            if (newDatas.getUsersex().equals("男")) {
                                radioButtonMan.setChecked(true);
                            } else {
                                radioButtonWoman.setChecked(true);
                            }
                            editTextUserAge.setText(newDatas.getUserage() + "");
                            if (newDatas.getUsertypeid() == 102) {
                                textViewUserType.setText("店铺老板");
                            } else if (newDatas.getUsertypeid() == 103) {
                                textViewUserType.setText("管理员");
                            } else {
                                textViewUserType.setText("普通用户");
                            }
                        } else {
                            getUserInfo();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerUserInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 删除相关账号信息。联通名下的店铺信息、课程信息、订单信息
     */
    private void deleteUserInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.DELETE_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isDelete = new Gson().fromJson(s, Boolean.class);
                        if (isDelete) {
                            finish();
                        } else {
                            Toast.makeText(ManagerUserInfoActivity.this, "发送了意料之外的错误，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerUserInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 信息更改后做输入检查
     */
    private void infoCheck() {
        if (editTextUserName.length() == 0) {
            Toast.makeText(ManagerUserInfoActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextUserPwd.length() == 0) {
            Toast.makeText(ManagerUserInfoActivity.this, "用户密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextUserAge.length() == 0) {
            Toast.makeText(ManagerUserInfoActivity.this, "用户年龄不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            userInfoChange();
            buttonChange.setText("信息更改");
            buttonDelete.setEnabled(true);
            editTextUserName.setEnabled(false);
            editTextUserPwd.setEnabled(false);
            editTextUserAge.setEnabled(false);
            radioButtonMan.setEnabled(false);
            radioButtonWoman.setEnabled(false);
        }
    }

    /**
     * 用户账号信息更改
     */
    private void userInfoChange() {
        final String userSex;
        if (radioButtonMan.isChecked()) {
            userSex = "男";
        } else {
            userSex = "女";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.CHANGE_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isChange = new Gson().fromJson(s, Boolean.class);
                        if (isChange) {
                            Toast.makeText(ManagerUserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            getUserInfo();
                            Toast.makeText(ManagerUserInfoActivity.this, "修改失败，退回原信息！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerUserInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                map.put("userName", editTextUserName.getText().toString());
                map.put("userPwd", editTextUserPwd.getText().toString());
                map.put("userSex", userSex);
                map.put("userAge", editTextUserAge.getText().toString());
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

}
