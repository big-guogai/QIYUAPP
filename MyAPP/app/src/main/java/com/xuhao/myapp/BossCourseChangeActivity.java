package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class BossCourseChangeActivity extends AppCompatActivity {

    private Button buttonEdit;
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
    private int countCheckBox;
    private int countUpdataCheckBox = 0;
    private String courseTypeId1 = "";
    private String courseTypeId2 = "";
    private String courseTypeId3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_course_change);

        initView();
        initData();

    }

    /**
     * 获取课程类型设置选中
     *
     * @param s 课程类型字符串
     */
    private void checkBoxIsCheck(String s) {
        switch (s) {
            case "家教":
                checkBoxTutor.setChecked(true);
                countCheckBox++;
                break;
            case "幼儿":
                checkBoxChild.setChecked(true);
                countCheckBox++;
                break;
            case "语言":
                checkBoxLanguage.setChecked(true);
                countCheckBox++;
                break;
            case "技能":
                checkBoxSkill.setChecked(true);
                countCheckBox++;
                break;
            case "IT":
                checkBoxIt.setChecked(true);
                countCheckBox++;
                break;
            case "设计":
                checkBoxDesign.setChecked(true);
                countCheckBox++;
                break;
            case "艺体":
                checkBoxArtistic.setChecked(true);
                countCheckBox++;
                break;
            case "学历":
                checkBoxDiploma.setChecked(true);
                countCheckBox++;
                break;
            case "其他":
                checkBoxAnother.setChecked(true);
                countCheckBox++;
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        editTextCourseName.setText(intent.getStringExtra("courseName"));
        editTextCourseInformation.setText(intent.getStringExtra("courseInformation"));
        editTextCoursePrice.setText(intent.getDoubleExtra("coursePrice", 0) + "");
        editTextCoursePriceType.setText(intent.getStringExtra("coursePriceType"));
        for (int i = 1; i <= 3; i++) {
            String s = "courseTypeId" + i;
            if (intent.getStringExtra(s) != null) {
                checkBoxIsCheck(intent.getStringExtra(s));
            }
        }
        //表单默认不可填写
        editTextCoursePriceType.setEnabled(false);
        editTextCoursePrice.setEnabled(false);
        editTextCourseInformation.setEnabled(false);
        editTextCourseName.setEnabled(false);
        checkBoxAnother.setEnabled(false);
        checkBoxDiploma.setEnabled(false);
        checkBoxArtistic.setEnabled(false);
        checkBoxDesign.setEnabled(false);
        checkBoxIt.setEnabled(false);
        checkBoxSkill.setEnabled(false);
        checkBoxLanguage.setEnabled(false);
        checkBoxChild.setEnabled(false);
        checkBoxTutor.setEnabled(false);

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

        //编辑按钮点击事件
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(buttonEdit.getText()).equals("编辑")) {
                    editTextCoursePriceType.setEnabled(true);
                    editTextCoursePrice.setEnabled(true);
                    editTextCourseInformation.setEnabled(true);
                    editTextCourseName.setEnabled(true);
                    checkBoxAnother.setEnabled(true);
                    checkBoxDiploma.setEnabled(true);
                    checkBoxArtistic.setEnabled(true);
                    checkBoxDesign.setEnabled(true);
                    checkBoxIt.setEnabled(true);
                    checkBoxSkill.setEnabled(true);
                    checkBoxLanguage.setEnabled(true);
                    checkBoxChild.setEnabled(true);
                    checkBoxTutor.setEnabled(true);
                    buttonEdit.setText("完成");
                } else {
                    checkInfo();
                }
            }
        });
        //返回按钮点击事件
        ImageButton imageButtonback = findViewById(R.id.boss_course_change_back);
        imageButtonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        editTextCourseName = findViewById(R.id.boss_course_change_course_name);
        editTextCourseInformation = findViewById(R.id.boss_course_change_course_information);
        editTextCoursePrice = findViewById(R.id.boss_course_change_course_price);
        editTextCoursePriceType = findViewById(R.id.boss_course_change_course_price_type);
        checkBoxTutor = findViewById(R.id.boss_course_change_course_type_tutor);
        checkBoxChild = findViewById(R.id.boss_course_change_course_type_child);
        checkBoxLanguage = findViewById(R.id.boss_course_change_course_type_language);
        checkBoxSkill = findViewById(R.id.boss_course_change_course_type_skill);
        checkBoxIt = findViewById(R.id.boss_course_change_course_type_it);
        checkBoxDesign = findViewById(R.id.boss_course_change_course_type_design);
        checkBoxArtistic = findViewById(R.id.boss_course_change_course_type_artistic);
        checkBoxDiploma = findViewById(R.id.boss_course_change_course_type_diploma);
        checkBoxAnother = findViewById(R.id.boss_course_change_course_type_another);
        buttonEdit = findViewById(R.id.boss_course_change_change);
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
                Toast.makeText(BossCourseChangeActivity.this, "课程类型最多勾选三个！", Toast.LENGTH_SHORT).show();
            }
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
     * 检查表单信息格式
     */
    private void checkInfo() {
        if (editTextCourseName.getText().toString().trim().equals("")) {
            Toast.makeText(BossCourseChangeActivity.this, "课程名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextCourseInformation.getText().toString().trim().equals("")) {
            Toast.makeText(BossCourseChangeActivity.this, "课程介绍不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextCoursePrice.getText().toString().trim().equals("")) {
            Toast.makeText(BossCourseChangeActivity.this, "课程价格不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextCoursePriceType.getText().toString().trim().equals("")) {
            Toast.makeText(BossCourseChangeActivity.this, "价格单位不能为空！", Toast.LENGTH_SHORT).show();
        } else if (countCheckBox == 0) {
            Toast.makeText(BossCourseChangeActivity.this, "课程类型必须选择", Toast.LENGTH_SHORT).show();
        } else {
            submitData();
        }
    }

    /**
     * 提交编辑后的数据
     */
    private void submitData() {
        courseTypeUpdata();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.BOSS_COURSE_INFO_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isEdit = new Gson().fromJson(s, Boolean.class);
                        if (isEdit) {
                            Toast.makeText(BossCourseChangeActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
                            editTextCoursePriceType.setEnabled(false);
                            editTextCoursePrice.setEnabled(false);
                            editTextCourseInformation.setEnabled(false);
                            editTextCourseName.setEnabled(false);
                            checkBoxAnother.setEnabled(false);
                            checkBoxDiploma.setEnabled(false);
                            checkBoxArtistic.setEnabled(false);
                            checkBoxDesign.setEnabled(false);
                            checkBoxIt.setEnabled(false);
                            checkBoxSkill.setEnabled(false);
                            checkBoxLanguage.setEnabled(false);
                            checkBoxChild.setEnabled(false);
                            checkBoxTutor.setEnabled(false);
                            buttonEdit.setText("完成");
                        } else {
                            Toast.makeText(BossCourseChangeActivity.this, "编辑失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent intent = getIntent();
                Map<String, String> map = new HashMap<>();
                map.put("courseId", String.valueOf(intent.getIntExtra("courseId", 0)));
                map.put("courseName", editTextCourseName.getText().toString());
                map.put("courseInformation", editTextCourseInformation.getText().toString());
                map.put("coursePrice", editTextCoursePrice.getText().toString());
                map.put("coursePriceType", editTextCoursePriceType.getText().toString());
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
