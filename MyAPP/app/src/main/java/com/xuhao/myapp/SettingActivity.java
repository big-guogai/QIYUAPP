package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //返回按钮点击事件
        ImageButton buttonback = findViewById(R.id.setting_button_back);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //注销点击事件
        TextView textView = findViewById(R.id.setting_logout_account);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAccount();
                finish();
            }
        });
        TextView textView1Clear = findViewById(R.id.setting_clear);
        textView1Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Toast.makeText(SettingActivity.this,"清理成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 账户注销清空存储的基本信息
     */
    private void logoutAccount() {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(SettingActivity.this, "注销成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
