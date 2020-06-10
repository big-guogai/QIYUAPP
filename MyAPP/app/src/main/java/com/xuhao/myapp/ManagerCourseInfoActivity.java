package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.Bean.BossCourseInfoBean;
import com.xuhao.myapp.internet.URLManager;

import java.util.HashMap;
import java.util.Map;

public class ManagerCourseInfoActivity extends AppCompatActivity {

    private TextView textViewCourseId;
    private TextView textViewBossId;
    private EditText editTextCourseName;
    private EditText editTextCoursePrice;
    private EditText editTextCoursePriceType;
    private EditText editTextCourseIntro;
    private ImageButton imageButtonBack;
    private Button buttonDelete;
    private Button buttonChange;
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
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_course_info);
        initView();
        initData();
    }

    private void initView() {
        textViewCourseId = findViewById(R.id.manager_course_info_course_id);
        textViewBossId = findViewById(R.id.manager_course_info_boss_id);
        editTextCourseName = findViewById(R.id.manager_course_info_course_name);
        editTextCoursePrice = findViewById(R.id.manager_course_info_course_price);
        editTextCoursePriceType = findViewById(R.id.manager_course_info_course_price_type);
        editTextCourseIntro = findViewById(R.id.manager_course_info_course_intro);
        imageButtonBack = findViewById(R.id.manager_course_info_back);
        buttonDelete = findViewById(R.id.manager_course_info_btn_delete);
        buttonChange = findViewById(R.id.manager_course_info_btn_change_info);
        checkBoxTutor = findViewById(R.id.manager_course_info_course_type_tutor);
        checkBoxChild = findViewById(R.id.manager_course_info_course_type_child);
        checkBoxLanguage = findViewById(R.id.manager_course_info_course_type_language);
        checkBoxSkill = findViewById(R.id.manager_course_info_course_type_skill);
        checkBoxIt = findViewById(R.id.manager_course_info_course_type_it);
        checkBoxDesign = findViewById(R.id.manager_course_info_course_type_design);
        checkBoxArtistic = findViewById(R.id.manager_course_info_course_type_artistic);
        checkBoxDiploma = findViewById(R.id.manager_course_info_course_type_diploma);
        checkBoxAnother = findViewById(R.id.manager_course_info_course_type_another);
    }

    private void initData() {
        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);
        editTextCourseIntro.setEnabled(false);
        editTextCoursePriceType.setEnabled(false);
        editTextCoursePrice.setEnabled(false);
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
        getCourseInfo();
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
        //返回按钮点击事件
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //删除按钮点击事件
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ManagerCourseInfoActivity.this).create();
                alertDialog.setTitle("提示");
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setMessage("请确认是否删除课程！");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCourseInfo();
                    }
                });
                alertDialog.show();
            }
        });
        //编辑按钮点击事件
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonChange.getText().toString().equals("信息更改")) {
                    buttonChange.setText("完成");
                    buttonDelete.setEnabled(false);
                    editTextCourseIntro.setEnabled(true);
                    editTextCourseName.setEnabled(true);
                    editTextCoursePrice.setEnabled(true);
                    editTextCoursePriceType.setEnabled(true);
                    checkBoxAnother.setEnabled(true);
                    checkBoxDiploma.setEnabled(true);
                    checkBoxArtistic.setEnabled(true);
                    checkBoxDesign.setEnabled(true);
                    checkBoxIt.setEnabled(true);
                    checkBoxSkill.setEnabled(true);
                    checkBoxLanguage.setEnabled(true);
                    checkBoxChild.setEnabled(true);
                    checkBoxTutor.setEnabled(true);
                } else {
                    checkNullInfo();
                }
            }
        });
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
                Toast.makeText(ManagerCourseInfoActivity.this, "课程类型最多勾选三个！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 页面读取获取全部课程信息
     */
    private void getCourseInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_COURSE_INFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        BossCourseInfoBean newDatas = new Gson().fromJson(s, BossCourseInfoBean.class);
                        if (newDatas != null) {
                            textViewCourseId.setText(newDatas.getCourseId() + "");
                            textViewBossId.setText(newDatas.getBossId() + "");
                            editTextCourseName.setText(newDatas.getCourseName());
                            editTextCoursePrice.setText(newDatas.getCoursePrice() + "");
                            editTextCoursePriceType.setText(newDatas.getCoursePriceType());
                            editTextCourseIntro.setText(newDatas.getCourseInformation());
                            checkCourseType(newDatas.getCourseTypeId1());
                            checkCourseType(newDatas.getCourseTypeId2());
                            checkCourseType(newDatas.getCourseTypeId3());
                        } else {
                            getCourseInfo();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerCourseInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("courseId", courseId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 删除当前课程
     */
    private void deleteCourseInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.DELETE_COURSE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isDelete = new Gson().fromJson(s, Boolean.class);
                        if (isDelete) {
                            finish();
                        } else {
                            Toast.makeText(ManagerCourseInfoActivity.this, "删除失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerCourseInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("courseId", courseId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 更改信息提交需做信息检查
     */
    private void checkNullInfo() {
        if (editTextCourseName.getText().toString().equals("")) {
            Toast.makeText(ManagerCourseInfoActivity.this, "课程名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextCoursePrice.getText().toString().equals("")) {
            Toast.makeText(ManagerCourseInfoActivity.this, "课程价格不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextCoursePriceType.getText().toString().equals("")) {
            Toast.makeText(ManagerCourseInfoActivity.this, "课程单位不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextCourseIntro.getText().toString().equals("")) {
            Toast.makeText(ManagerCourseInfoActivity.this, "课程介绍不能为空！", Toast.LENGTH_SHORT).show();
        } else if (countCheckBox == 0) {
            Toast.makeText(ManagerCourseInfoActivity.this, "课程类型必须选择！", Toast.LENGTH_SHORT).show();
        } else {
            countUpdataCheckBox = 0;
            courseTypeUpdata();
            changeCourseInfo();
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
     * 更改当前课程信息
     */
    private void changeCourseInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.CHANGE_COURSE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isChange = new Gson().fromJson(s, Boolean.class);
                        if (isChange) {
                            buttonChange.setText("信息更改");
                            buttonDelete.setEnabled(true);
                            editTextCourseIntro.setEnabled(false);
                            editTextCourseName.setEnabled(false);
                            editTextCoursePrice.setEnabled(false);
                            editTextCoursePriceType.setEnabled(false);
                            checkBoxAnother.setEnabled(false);
                            checkBoxDiploma.setEnabled(false);
                            checkBoxArtistic.setEnabled(false);
                            checkBoxDesign.setEnabled(false);
                            checkBoxIt.setEnabled(false);
                            checkBoxSkill.setEnabled(false);
                            checkBoxLanguage.setEnabled(false);
                            checkBoxChild.setEnabled(false);
                            checkBoxTutor.setEnabled(false);
                            Toast.makeText(ManagerCourseInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManagerCourseInfoActivity.this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerCourseInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("courseId", courseId + "");
                map.put("courseName", editTextCourseName.getText().toString());
                map.put("coursePrice", editTextCoursePrice.getText().toString());
                map.put("coursePriceType", editTextCoursePriceType.getText().toString());
                map.put("courseIntro", editTextCourseIntro.getText().toString());
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

    /**
     * 从获取到的数据做checkBox匹配
     *
     * @param courseType 课程类型
     */
    private void checkCourseType(String courseType) {
        if (courseType != null && !courseType.equals("")) {
            switch (courseType) {
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
    }
}
