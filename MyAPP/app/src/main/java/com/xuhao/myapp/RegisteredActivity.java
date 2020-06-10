package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.Bean.UserInfoBean;
import com.xuhao.myapp.internet.URLManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisteredActivity extends AppCompatActivity {

    private static int year;
    private static int month;
    private static int day;
    private Button buttonRegistered;
    private TextInputLayout textInputLayoutUserId;
    private TextInputEditText textInputEditTextUserId;
    private TextInputLayout textInputLayoutUserName;
    private TextInputEditText textInputEditTextUserName;
    private TextInputLayout textInputLayoutUserFirstPwd;
    private TextInputEditText textInputEditTextUserFirstPwd;
    private TextInputLayout textInputLayoutUserCheckPwd;
    private TextInputEditText textInputEditTextUserCheckPwd;
    private TextInputLayout textInputLayoutTextAge;
    private TextInputEditText textInputEditTextAge;
    private RadioGroup radioGroup;
    private String radioCheckText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        radioGroup = findViewById(R.id.registered_sex_radio);
        textInputLayoutUserId = findViewById(R.id.registered_layout_user_id);
        textInputEditTextUserId = findViewById(R.id.registered_user_id);
        textInputLayoutUserName = findViewById(R.id.registered_layout_user_name);
        textInputEditTextUserName = findViewById(R.id.registered_user_name);
        textInputLayoutUserFirstPwd = findViewById(R.id.registered_layout_user_password);
        textInputEditTextUserFirstPwd = findViewById(R.id.registered_user_password);
        textInputLayoutUserCheckPwd = findViewById(R.id.registered_layout_user_inspection_password);
        textInputEditTextUserCheckPwd = findViewById(R.id.registered_user_inspection_password);
        textInputLayoutTextAge = findViewById(R.id.registered_layout_age_data);

        //用户账号错误提示在编辑框内容改变时消失
        TextWatcher textWatcherUserId = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutUserId.setErrorEnabled(false);
            }
        };
        textInputEditTextUserId.addTextChangedListener(textWatcherUserId);
        //用户名错误提示在编辑框内容改变时消失
        TextWatcher textWatcherUserName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutUserName.setErrorEnabled(false);
            }
        };
        textInputEditTextUserName.addTextChangedListener(textWatcherUserName);
        //用户第一次密码错误提示在编辑框内容改变时消失
        TextWatcher textWatcherUserFristPwd = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutUserFirstPwd.setErrorEnabled(false);
            }
        };
        textInputEditTextUserFirstPwd.addTextChangedListener(textWatcherUserFristPwd);
        //用户第二次密码错误提示在编辑框内容改变时消失
        TextWatcher textWatcherUserCheckPwd = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayoutUserCheckPwd.setErrorEnabled(false);
            }
        };
        textInputEditTextUserCheckPwd.addTextChangedListener(textWatcherUserCheckPwd);
        //年龄编辑点击事件
        textInputEditTextAge = findViewById(R.id.registered_age_data);
        textInputEditTextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayoutTextAge.setErrorEnabled(false);
                showDatePicker();
            }
        });

        //返回按钮点击事件
        ImageButton imageButtonback = findViewById(R.id.registered_button_back);
        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //注册按钮点击事件
        buttonRegistered = findViewById(R.id.registered_btn_registered);
        buttonRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegistered.setText("注册中");
                buttonRegistered.setEnabled(false);
                checkRightRegistered();
            }
        });
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
        new DatePickerDialog(RegisteredActivity.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            public void onDateSet(DatePicker datePicker, int myYear, int myMonth, int myDay) {
                RegisteredActivity.year = myYear;
                RegisteredActivity.month = myMonth;
                RegisteredActivity.day = myDay;
                Calendar calendar = Calendar.getInstance();
                int nowyear = calendar.get(Calendar.YEAR);
                textInputEditTextAge.setText(Integer.toString(nowyear - myYear));
            }
        }, year, month, day).show();
    }

    /**
     * 注册检查输入格式并获取数据
     */
    private void checkRightRegistered() {
        if (isRightPhone((Objects.requireNonNull(textInputEditTextUserId.getText())).toString())) {
            if (textInputEditTextUserName.length() != 0) {
                if (textInputEditTextUserFirstPwd.length() != 0) {
                    if (textInputEditTextUserCheckPwd.length() != 0) {
                        if (textInputEditTextAge.length() != 0) {
                            if (textInputEditTextUserFirstPwd.length() >= 6) {
                                if (textInputEditTextUserCheckPwd.length() >= 6) {
                                    if (Objects.requireNonNull(textInputEditTextUserFirstPwd.getText()).toString().equals(Objects.requireNonNull(textInputEditTextUserCheckPwd.getText()).toString())) {
                                        getData();
                                    } else {
                                        buttonRegistered.setText("注册");
                                        buttonRegistered.setEnabled(true);
                                        textInputLayoutUserFirstPwd.setError("两次密码输入不一致！");
                                        textInputLayoutUserCheckPwd.setError("两次密码输入不一致！");
                                    }
                                } else {
                                    buttonRegistered.setText("注册");
                                    buttonRegistered.setEnabled(true);
                                    textInputLayoutUserCheckPwd.setError("密码必须不少于六位！");
                                }
                            } else {
                                buttonRegistered.setText("注册");
                                buttonRegistered.setEnabled(true);
                                textInputLayoutUserFirstPwd.setError("密码必须不少于六位！");
                            }
                        } else {
                            buttonRegistered.setText("注册");
                            buttonRegistered.setEnabled(true);
                            textInputLayoutTextAge.setError("请选择您的出生日期！");
                        }
                    } else {
                        buttonRegistered.setText("注册");
                        buttonRegistered.setEnabled(true);
                        textInputLayoutUserCheckPwd.setError("请确认您的密码！");
                    }
                } else {
                    buttonRegistered.setText("注册");
                    buttonRegistered.setEnabled(true);
                    textInputLayoutUserFirstPwd.setError("请输入您的密码！");
                }
            } else {
                buttonRegistered.setText("注册");
                buttonRegistered.setEnabled(true);
                textInputLayoutUserName.setError("请输入您的昵称！");
            }
        } else {
            buttonRegistered.setText("注册");
            buttonRegistered.setEnabled(true);
            textInputLayoutUserId.setError("请输入正确的手机号码格式！");

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
     * 注册成功存储临时文件
     */
    private void saveRegisteredInfo() {
        long userid = Long.parseLong(Objects.requireNonNull(textInputEditTextUserId.getText()).toString());
        SharedPreferences sp = getSharedPreferences("userLoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("userId", userid);
        editor.putString("userPwd", Objects.requireNonNull(textInputEditTextUserFirstPwd.getText()).toString());
        editor.putString("userHead", "head/head2_null.png");
        editor.putString("userName", Objects.requireNonNull(textInputEditTextUserName.getText()).toString());
        editor.apply();
    }

    /**
     * 注册获取数据
     */
    private void getData() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
            if (rb.isChecked()) {
                radioCheckText = (String) rb.getText();
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_REGISTERED_INFO,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        Boolean isRegistered = new Gson().fromJson(s, boolean.class);
                        if (isRegistered) {
                            saveRegisteredInfo();
                            Toast.makeText(RegisteredActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            buttonRegistered.setText("注册");
                            buttonRegistered.setEnabled(true);
                            textInputLayoutUserId.setError("此手机号已被注册，请确认更改！");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        buttonRegistered.setText("注册");
                        buttonRegistered.setEnabled(true);
                        Toast.makeText(RegisteredActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(textInputEditTextUserId.getText()));
                map.put("userName", String.valueOf(textInputEditTextUserName.getText()));
                map.put("userPwd", String.valueOf(textInputEditTextUserFirstPwd.getText()));
                map.put("userAge", String.valueOf(textInputEditTextAge.getText()));
                map.put("userSex", String.valueOf(radioCheckText));
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
