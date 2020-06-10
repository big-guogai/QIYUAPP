package com.xuhao.myapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Base64;
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
import com.xuhao.myapp.utills.ImageConpressUtl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class BossRegisteredActivity extends AppCompatActivity {

    private long userId = 0;
    private Bitmap bitmap = null;
    private int countImg = 0;
    private int PHOTO_REQUEST_GALLERY_MAIN1 = 1;
    private int PHOTO_REQUEST_GALLERY_MAIN2 = 2;
    private int PHOTO_REQUEST_GALLERY_MAIN3 = 3;
    private int PHOTO_REQUEST_GALLERY_MAIN4 = 4;
    private int PHOTO_REQUEST_GALLERY_MAIN5 = 5;
    private int PHOTO_REQUEST_GALLERY_MAIN6 = 6;
    private ImageView imageViewMain1;
    private ImageView imageViewMain2;
    private ImageView imageViewMain3;
    private ImageView imageViewMain4;
    private ImageView imageViewMain5;
    private ImageView imageViewDocument;
    private EditText editTextBossName;
    private EditText editTextBossIntroduction;
    private EditText editTextTelephone;
    private EditText editTextlocal;
    private EditText editTextPosition;
    private String mainPosition;
    private String imageMain1;
    private String imageMain2;
    private String imageMain3;
    private String imageMain4;
    private String imageMain5;
    private String imageDocument;
    private Spinner spinner;
    private String receiptType;
    private EditText editTextReceiptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_registered);
        initView();
        spinnerSet();
        //返回按钮点击事件
        ImageButton imageButtonBack = findViewById(R.id.boss_registered_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //提交按钮点击事件
        Button buttonSubmit = findViewById(R.id.boss_registered_submit_boss_info);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegistered();
            }
        });

        //上传图片按钮点击事件
        ImageButton imageButtonUPhotoPlus = findViewById(R.id.boss_registered_boss_photo_plus);
        imageButtonUPhotoPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewMain1.getDrawable() != null) {
                    if (imageViewMain2.getDrawable() != null) {
                        if (imageViewMain3.getDrawable() != null) {
                            if (imageViewMain4.getDrawable() != null) {
                                if (imageViewMain5.getDrawable() != null) {
                                    Toast.makeText(BossRegisteredActivity.this, "最多上传五张图片！", Toast.LENGTH_SHORT).show();
                                } else {
                                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN5);
                                }
                            } else {
                                imagePlus(PHOTO_REQUEST_GALLERY_MAIN4);
                            }
                        } else {
                            imagePlus(PHOTO_REQUEST_GALLERY_MAIN3);
                        }
                    } else {
                        imagePlus(PHOTO_REQUEST_GALLERY_MAIN2);
                    }
                } else {
                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN1);
                }
            }
        });

        //上传营业执照许可证按钮点击事件
        final ImageButton imageButtonDocument = findViewById(R.id.boss_registered_boss_document_plus);
        imageButtonDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonDocument.setVisibility(View.GONE);
                imagePlus(PHOTO_REQUEST_GALLERY_MAIN6);
            }
        });

        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sp.getLong("userId", 0);
    }

    private void initView() {
        imageViewMain1 = findViewById(R.id.boss_registered_boss_photo_main1);
        imageViewMain2 = findViewById(R.id.boss_registered_boss_photo_main2);
        imageViewMain3 = findViewById(R.id.boss_registered_boss_photo_main3);
        imageViewMain4 = findViewById(R.id.boss_registered_boss_photo_main4);
        imageViewMain5 = findViewById(R.id.boss_registered_boss_photo_main5);
        editTextBossName = findViewById(R.id.boss_registered_boss_name);
        editTextBossIntroduction = findViewById(R.id.boss_registered_boss_Introduction);
        editTextTelephone = findViewById(R.id.boss_registered_boss_telephone);
        editTextlocal = findViewById(R.id.boss_registered_boss_local);
        editTextPosition = findViewById(R.id.boss_registered_boss_position);
        imageViewDocument = findViewById(R.id.boss_registered_boss_document);
        spinner = findViewById(R.id.boss_registered_receipt_spinner);
        editTextReceiptId = findViewById(R.id.boss_registered_receipt_id);
    }

    /**
     * 收款类型下拉框
     */
    private void spinnerSet() {
        String[] curs = getResources().getStringArray(R.array.receipt_type);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.receipt_spinner, curs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receiptType = adapter.getItem(position);
                if (position == 0) {
                    editTextReceiptId.setHint("银行卡号");
                    InputFilter[] filters = {new InputFilter.LengthFilter(19)};
                    editTextReceiptId.setFilters(filters);
                } else {
                    editTextReceiptId.setHint("支付宝账号");
                    InputFilter[] filters = {new InputFilter.LengthFilter(11)};
                    editTextReceiptId.setFilters(filters);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 检查信息注册
     */
    private void checkRegistered() {
        if (String.valueOf(editTextBossName.getText()).trim().equals("")) {
            Toast.makeText(BossRegisteredActivity.this, "店铺名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextBossIntroduction.getText()).trim().equals("")) {
            Toast.makeText(BossRegisteredActivity.this, "店铺介绍不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextTelephone.getText()).trim().equals("")) {
            Toast.makeText(BossRegisteredActivity.this, "店铺联系方式不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextlocal.getText()).trim().equals("")) {
            Toast.makeText(BossRegisteredActivity.this, "店铺地址不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextPosition.getText()).trim().equals("")) {
            Toast.makeText(BossRegisteredActivity.this, "店铺详细地址不能为空！", Toast.LENGTH_SHORT).show();
        } else if (imageViewMain1.getDrawable() == null) {
            Toast.makeText(BossRegisteredActivity.this, "店铺照片不能为空！", Toast.LENGTH_SHORT).show();
        } else if (imageViewDocument.getDrawable() == null) {
            Toast.makeText(BossRegisteredActivity.this, "营业执照不能为空！", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(editTextReceiptId.getText()).trim().equals("")) {
            Toast.makeText(BossRegisteredActivity.this, "收款信息不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            switch (countImg) {
                case 1:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewMain1.getDrawable()).getBitmap());
                    imageMain2 = "";
                    imageMain3 = "";
                    imageMain4 = "";
                    imageMain5 = "";
                    break;
                case 2:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewMain1.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewMain2.getDrawable()).getBitmap());
                    imageMain3 = "";
                    imageMain4 = "";
                    imageMain5 = "";
                    break;
                case 3:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewMain1.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewMain2.getDrawable()).getBitmap());
                    imageMain3 = bitmapToString(((BitmapDrawable) imageViewMain3.getDrawable()).getBitmap());
                    imageMain4 = "";
                    imageMain5 = "";
                    break;
                case 4:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewMain1.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewMain2.getDrawable()).getBitmap());
                    imageMain3 = bitmapToString(((BitmapDrawable) imageViewMain3.getDrawable()).getBitmap());
                    imageMain4 = bitmapToString(((BitmapDrawable) imageViewMain4.getDrawable()).getBitmap());
                    imageMain5 = "";
                    break;
                case 5:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewMain1.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewMain2.getDrawable()).getBitmap());
                    imageMain3 = bitmapToString(((BitmapDrawable) imageViewMain3.getDrawable()).getBitmap());
                    imageMain4 = bitmapToString(((BitmapDrawable) imageViewMain4.getDrawable()).getBitmap());
                    imageMain5 = bitmapToString(((BitmapDrawable) imageViewMain5.getDrawable()).getBitmap());
                    break;
                default:
                    break;
            }
            mainPosition = editTextlocal.getText().toString() + editTextPosition.getText().toString();
            imageDocument = bitmapToString(((BitmapDrawable) imageViewDocument.getDrawable()).getBitmap());
            upData();
        }
    }

    /**
     * 添加图片打开相册
     *
     * @param i 添加标识
     */
    private void imagePlus(int i) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, i);
    }

    /**
     * 上传注册申请表
     */
    private void upData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_REGISTERED_BOSS_CHECK_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isSave = new Gson().fromJson(s, boolean.class);
                        if (isSave) {
                            Toast.makeText(BossRegisteredActivity.this, "信息已上传，三个工作日内审核", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("userTypeId", 1011);
                            editor.apply();
                            finish();
                        } else {
                            Toast.makeText(BossRegisteredActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(BossRegisteredActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId + "");
                map.put("bossName", editTextBossName.getText().toString());
                map.put("bossInformation", editTextBossIntroduction.getText().toString());
                map.put("bossTelephone", editTextTelephone.getText().toString());
                map.put("bossPosition", mainPosition);
                map.put("bossPhotoOne", imageMain1);
                map.put("bossPhotoTwo", imageMain2);
                map.put("bossPhotoThree", imageMain3);
                map.put("bossPhotoFour", imageMain4);
                map.put("bossPhotoFive", imageMain5);
                map.put("bossDocument", imageDocument);
                map.put("bossReceiptType", receiptType);
                map.put("bossReceiptId", editTextReceiptId.getText().toString());
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * bitmap图片转字符串
     *
     * @param bitmap 图片资源
     * @return 返回字符串传回服务器
     */
    public static String bitmapToString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream btString = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, btString);
        byte[] bytes = btString.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY_MAIN1) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(BossRegisteredActivity.this,uri,250,150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewMain1.setImageBitmap(bitmap);
                    imageViewMain1.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY_MAIN2) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(BossRegisteredActivity.this,uri,250,150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewMain2.setImageBitmap(bitmap);
                    imageViewMain2.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY_MAIN3) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(BossRegisteredActivity.this,uri,250,150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewMain3.setImageBitmap(bitmap);
                    imageViewMain3.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY_MAIN4) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(BossRegisteredActivity.this,uri,250,150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewMain4.setImageBitmap(bitmap);
                    imageViewMain4.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY_MAIN5) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(BossRegisteredActivity.this,uri,250,150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewMain5.setImageBitmap(bitmap);
                    imageViewMain5.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY_MAIN6) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(BossRegisteredActivity.this,uri,500,300);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    imageViewDocument.setImageBitmap(bitmap);
                    imageViewDocument.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }
}
