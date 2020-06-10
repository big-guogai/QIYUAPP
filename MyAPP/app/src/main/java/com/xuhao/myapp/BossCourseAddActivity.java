package com.xuhao.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class BossCourseAddActivity extends AppCompatActivity {

    private int bossid;
    private EditText editTextCourseName;
    private EditText editTextCourseInformation;
    private EditText editTextCoursePrice;
    private EditText editTextCoursePriceType;
    private CheckBox checkBoxTutor;
    private CheckBox checkBoxChild;
    private CheckBox checkBoxLanguage;
    private CheckBox checkBoxSkill;
    private CheckBox checkBoxIt;
    private CheckBox checkBoxDesign;
    private CheckBox checkBoxArtistic;
    private CheckBox checkBoxDiploma;
    private CheckBox checkBoxAnother;
    private String courseTypeId1 = "";
    private String courseTypeId2 = "";
    private String courseTypeId3 = "";
    private int countCheckBox = 0;
    private int countUpdataCheckBox = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_course_add);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //课程类型CheckBox点击事件
        checkBoxTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxTutor);
            }
        });
        checkBoxSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxSkill);
            }
        });
        checkBoxLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxLanguage);
            }
        });
        checkBoxIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxIt);
            }
        });
        checkBoxDiploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxDiploma);
            }
        });
        checkBoxDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxDesign);
            }
        });
        checkBoxChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxChild);
            }
        });
        checkBoxArtistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxArtistic);
            }
        });
        checkBoxAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(checkBoxAnother);
            }
        });

        //提交按钮点击事件
        Button buttonAdd = findViewById(R.id.boss_course_add_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfoAdd();
            }
        });

        //返回按钮点击事件
        ImageButton imageButtonBack = findViewById(R.id.boss_course_add_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化定义
     */
    private void initView() {
        editTextCourseName = findViewById(R.id.boss_course_add_course_name);
        editTextCourseInformation = findViewById(R.id.boss_course_add_course_information);
        editTextCoursePrice = findViewById(R.id.boss_course_add_course_price);
        editTextCoursePriceType = findViewById(R.id.boss_course_add_course_price_type);
        checkBoxAnother = findViewById(R.id.boss_course_add_course_type_another);
        checkBoxArtistic = findViewById(R.id.boss_course_add_course_type_artistic);
        checkBoxChild = findViewById(R.id.boss_course_add_course_type_child);
        checkBoxDesign = findViewById(R.id.boss_course_add_course_type_design);
        checkBoxDiploma = findViewById(R.id.boss_course_add_course_type_diploma);
        checkBoxIt = findViewById(R.id.boss_course_add_course_type_it);
        checkBoxLanguage = findViewById(R.id.boss_course_add_course_type_language);
        checkBoxSkill = findViewById(R.id.boss_course_add_course_type_skill);
        checkBoxTutor = findViewById(R.id.boss_course_add_course_type_tutor);
        Intent intent = getIntent();
        bossid = intent.getIntExtra("bossId", 0);
    }

    /**
     * 课程类型最多三个，选中判定
     *
     * @param checkBox 被点击的CheckBox
     */
    private void checkCheckBox(CheckBox checkBox) {
        if (countCheckBox < 3) {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(false);
                countCheckBox--;
            } else {
                checkBox.setChecked(true);
                countCheckBox++;
            }
        } else {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(false);
                countCheckBox--;
            } else {
                checkBox.setChecked(false);
                Toast.makeText(BossCourseAddActivity.this, "课程类型最多勾选三个！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 信息检查上传
     */
    private void checkInfoAdd() {
        if (String.valueOf(editTextCourseName.getText()).trim().equals("")) {
            Toast.makeText(BossCourseAddActivity.this, "课程名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextCourseInformation.getText()).trim().equals("")) {
            Toast.makeText(BossCourseAddActivity.this, "课程介绍不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextCoursePrice.getText()).trim().equals("")) {
            Toast.makeText(BossCourseAddActivity.this, "课程价格不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextCoursePriceType.getText()).trim().equals("")) {
            Toast.makeText(BossCourseAddActivity.this, "价格单位不能为空！", Toast.LENGTH_SHORT).show();
        } else if (countCheckBox == 0) {
            Toast.makeText(BossCourseAddActivity.this, "请勾选您的课程类型！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BossCourseAddActivity.this, "信息正在上传", Toast.LENGTH_SHORT).show();
            addCourseInfo();
        }
    }

    /**
     * 获取选中的Checkbox
     */
    private void courseTypeUpdata() {
        String couseType;
        for (int j = 1; j <= 9; j++) {
            switch (j) {
                case 1:
                    if (checkBoxTutor.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "家教";
                        ifcount(couseType);
                    }
                    break;
                case 2:
                    if (checkBoxChild.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "幼儿";
                        ifcount(couseType);
                    }
                    break;
                case 3:
                    if (checkBoxLanguage.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "语言";
                        ifcount(couseType);
                    }
                    break;
                case 4:
                    if (checkBoxSkill.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "技能";
                        ifcount(couseType);
                    }
                    break;
                case 5:
                    if (checkBoxIt.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "IT";
                        ifcount(couseType);
                    }
                    break;
                case 6:
                    if (checkBoxDesign.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "设计";
                        ifcount(couseType);
                    }
                    break;
                case 7:
                    if (checkBoxArtistic.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "艺体";
                        ifcount(couseType);
                    }
                    break;
                case 8:
                    if (checkBoxDiploma.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "学历";
                        ifcount(couseType);
                    }
                    break;
                case 9:
                    if (checkBoxAnother.isChecked()) {
                        countUpdataCheckBox++;
                        couseType = "其他";
                        ifcount(couseType);
                    }
                    break;
            }
        }

    }

    /**
     * 做判断，给courseTypeId赋值
     *
     * @param s courseType的值
     */
    private void ifcount(String s) {
        if (countUpdataCheckBox == 1) {
            courseTypeId1 = s;
        } else if (countUpdataCheckBox == 2) {
            courseTypeId2 = s;
        } else if (countUpdataCheckBox == 3) {
            courseTypeId3 = s;
        }
    }

    /**
     * 提交表单信息处理
     */
    private void addCourseInfo() {
        courseTypeUpdata();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_BOSS_COURSE_ADD_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isAdd = new Gson().fromJson(s, Boolean.class);
                        if (isAdd) {
                            Toast.makeText(BossCourseAddActivity.this, "课程录入成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(BossCourseAddActivity.this, "上传错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(BossCourseAddActivity.this, "错误！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("bossId", String.valueOf(bossid));
                map.put("courseName", editTextCourseName.getText().toString());
                map.put("coursePrice", editTextCoursePrice.getText().toString());
                map.put("coursePriceType", editTextCoursePriceType.getText().toString());
                map.put("courseInformation", editTextCourseInformation.getText().toString());
                map.put("courseTypeId1", courseTypeId1);
                map.put("courseTypeId2", courseTypeId2);
                map.put("courseTypeId3", courseTypeId3);
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
