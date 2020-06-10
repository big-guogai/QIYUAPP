package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.internet.URLManager;

import java.util.HashMap;
import java.util.Map;

public class BossMainInfoActivity extends AppCompatActivity {

    private EditText editTextBossId;
    private EditText editTextBossName;
    private EditText editTextBossInformation;
    private EditText editTextBossPosition;
    private EditText editTextBossTelephone;
    private Spinner spinner;
    private String receiptType;
    private EditText editTextBossReceiptId;
    private Button buttonEdit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_main_info);
        initView();
        initData();

    }

    /**
     * 表单信息检查
     */
    private void checkInfo() {
        if (editTextBossName.getText().toString().trim().equals("")) {
            Toast.makeText(BossMainInfoActivity.this, "店铺名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossInformation.getText().toString().trim().equals("")) {
            Toast.makeText(BossMainInfoActivity.this, "店铺信息不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossPosition.getText().toString().trim().equals("")) {
            Toast.makeText(BossMainInfoActivity.this, "店铺位置不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossTelephone.getText().toString().trim().equals("")) {
            Toast.makeText(BossMainInfoActivity.this, "店铺联系电话不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossReceiptId.getText().toString().trim().equals("")) {
            Toast.makeText(BossMainInfoActivity.this, "店铺收款信息不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            updataInfo();
        }
    }

    /**
     * 上传编辑信息
     */
    private void updataInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.BOSS_INFO_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isEdit = new Gson().fromJson(s, Boolean.class);
                        if (isEdit) {
                            Toast.makeText(BossMainInfoActivity.this, "编辑成功！", Toast.LENGTH_SHORT).show();
                            buttonEdit.setText("编辑");
                            editTextBossId.setEnabled(false);
                            editTextBossTelephone.setEnabled(false);
                            editTextBossPosition.setEnabled(false);
                            editTextBossInformation.setEnabled(false);
                            editTextBossName.setEnabled(false);
                            spinner.setEnabled(false);
                            editTextBossReceiptId.setEnabled(false);
                        } else {
                            Toast.makeText(BossMainInfoActivity.this, "编辑失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(BossMainInfoActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent intent = getIntent();
                Map<String, String> map = new HashMap<>();
                map.put("bossId", editTextBossId.getText().toString());
                map.put("bossName", editTextBossName.getText().toString());
                map.put("bossInformation", editTextBossInformation.getText().toString());
                map.put("bossPosition", editTextBossPosition.getText().toString());
                map.put("bossTelephone", editTextBossTelephone.getText().toString());
                map.put("bossReceiptType", receiptType);
                map.put("bossReceiptId", editTextBossReceiptId.getText().toString());
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    private void initView() {
        editTextBossId = findViewById(R.id.boss_main_info_boss_id);
        editTextBossName = findViewById(R.id.boss_main_info_boss_name);
        editTextBossInformation = findViewById(R.id.boss_main_info_boss_information);
        editTextBossPosition = findViewById(R.id.boss_main_info_boss_position);
        editTextBossTelephone = findViewById(R.id.boss_main_info_boss_telephone);
        spinner = findViewById(R.id.boss_main_info_receipt_spinner);
        editTextBossReceiptId = findViewById(R.id.boss_main_info_receipt_id);
        buttonEdit = findViewById(R.id.boss_main_info_edit);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        editTextBossId.setText(intent.getIntExtra("bossId", 0) + "");
        editTextBossName.setText(intent.getStringExtra("bossName"));
        editTextBossInformation.setText(intent.getStringExtra("bossInformation"));
        editTextBossPosition.setText(intent.getStringExtra("bossPosition"));
        editTextBossTelephone.setText(intent.getLongExtra("bossTelephone", 0) + "");
        receiptType = intent.getStringExtra("bossReceiptType");
        editTextBossReceiptId.setText(intent.getStringExtra("bossReceiptId"));
        editTextBossId.setEnabled(false);
        editTextBossTelephone.setEnabled(false);
        editTextBossPosition.setEnabled(false);
        editTextBossInformation.setEnabled(false);
        editTextBossName.setEnabled(false);
        spinner.setEnabled(false);
        editTextBossReceiptId.setEnabled(false);
        spinnerSet();

        //返回按钮点击事件
        ImageButton imageButtonBack = findViewById(R.id.boss_main_info_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //编辑按钮点击事件
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(buttonEdit.getText()).equals("编辑")) {
                    editTextBossTelephone.setEnabled(true);
                    editTextBossInformation.setEnabled(true);
                    editTextBossName.setEnabled(true);
                    editTextBossPosition.setEnabled(true);
                    editTextBossReceiptId.setEnabled(true);
                    spinner.setEnabled(true);
                    buttonEdit.setText("完成");
                } else {
                    checkInfo();
                }
            }
        });
    }

    /**
     * 收款类型下拉框
     */
    private void spinnerSet() {
        String[] curs = getResources().getStringArray(R.array.receipt_type);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.receipt_spinner, curs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (receiptType.equals("银行卡")) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receiptType = adapter.getItem(position);
                if (position == 0) {
                    editTextBossReceiptId.setHint("银行卡号");
                    InputFilter[] filters = {new InputFilter.LengthFilter(19)};
                    editTextBossReceiptId.setFilters(filters);
                } else {
                    editTextBossReceiptId.setHint("支付宝账号");
                    InputFilter[] filters = {new InputFilter.LengthFilter(11)};
                    editTextBossReceiptId.setFilters(filters);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
