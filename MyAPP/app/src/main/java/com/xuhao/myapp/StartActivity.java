package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.view.View;

import java.lang.invoke.ConstantCallSite;

public class StartActivity extends AppCompatActivity {
    Button btngetout;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ConstraintLayout constraintLayout = findViewById(R.id.start_welcome_layout);
        Animation animation = AnimationUtils.loadAnimation(StartActivity.this, R.anim.welcome);
        constraintLayout.startAnimation(animation);
        handler.sendEmptyMessageDelayed(-1, 5000);
        btngetout = findViewById(R.id.start_getout);
        //跳过按钮点击事件
        btngetout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}