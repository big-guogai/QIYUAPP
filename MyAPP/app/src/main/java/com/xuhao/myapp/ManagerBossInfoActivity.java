package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.Bean.BossInfoBean;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageConpressUtl;
import com.xuhao.myapp.utills.ImageUtiles;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ManagerBossInfoActivity extends AppCompatActivity {

    private int countImg = 0;
    private Bitmap bitmap = null;
    private TextView textViewBossId;
    private EditText editTextBossName;
    private TextView textViewUserId;
    private EditText editTextBossIntro;
    private EditText editTextBossTelePhone;
    private EditText editTextBossPosition;
    private ImageView imageViewPhotoOne;
    private ImageView imageViewPhotoTwo;
    private ImageView imageViewPhotoThree;
    private ImageView imageViewPhotoFour;
    private ImageView imageViewPhotoFive;
    private ImageView imageViewBossDocument;
    private ImageButton imageButtonPhotoPlus;
    private ImageButton imageButtonPhotoReduce;
    private ImageButton imageButtonDocumentPlus;
    private ImageButton imageButtonDocumentReduce;
    private TextView textViewReceiptType;
    private TextView textViewReceiptId;
    private ImageButton imageButtonBack;
    private Button buttonDelete;
    private Button buttonChange;
    private int bossId;
    private int PHOTO_REQUEST_GALLERY_MAIN1 = 1;
    private int PHOTO_REQUEST_GALLERY_MAIN2 = 2;
    private int PHOTO_REQUEST_GALLERY_MAIN3 = 3;
    private int PHOTO_REQUEST_GALLERY_MAIN4 = 4;
    private int PHOTO_REQUEST_GALLERY_MAIN5 = 5;
    private int PHOTO_REQUEST_GALLERY_MAIN6 = 6;
    private String imageMain1;
    private String imageMain2;
    private String imageMain3;
    private String imageMain4;
    private String imageMain5;
    private String imageDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_boss_info);
        initView();
        initData();
    }

    private void initView() {
        textViewBossId = findViewById(R.id.manager_boss_info_boss_id);
        editTextBossName = findViewById(R.id.manager_boss_info_boss_name);
        textViewUserId = findViewById(R.id.manager_boss_info_user_id);
        editTextBossIntro = findViewById(R.id.manager_boss_info_boss_Introduction);
        editTextBossTelePhone = findViewById(R.id.manager_boss_info_boss_telephone);
        editTextBossPosition = findViewById(R.id.manager_boss_info_boss_position);
        imageViewPhotoOne = findViewById(R.id.manager_boss_info_boss_photo_main1);
        imageViewPhotoTwo = findViewById(R.id.manager_boss_info_boss_photo_main2);
        imageViewPhotoThree = findViewById(R.id.manager_boss_info_boss_photo_main3);
        imageViewPhotoFour = findViewById(R.id.manager_boss_info_boss_photo_main4);
        imageViewPhotoFive = findViewById(R.id.manager_boss_info_boss_photo_main5);
        imageViewBossDocument = findViewById(R.id.manager_boss_info_boss_document);
        imageButtonPhotoPlus = findViewById(R.id.manager_boss_info_boss_photo_plus);
        imageButtonPhotoReduce = findViewById(R.id.manager_boss_info_boss_photo_reduce);
        imageButtonDocumentPlus = findViewById(R.id.manager_boss_info_boss_document_plus);
        imageButtonDocumentReduce = findViewById(R.id.manager_boss_info_boss_document_reduce);
        textViewReceiptType = findViewById(R.id.manager_boss_info_receipt_type);
        textViewReceiptId = findViewById(R.id.manager_boss_info_receipt_id);
        imageButtonBack = findViewById(R.id.manager_boss_info_back);
        buttonDelete = findViewById(R.id.manager_boss_info_btn_delete);
        buttonChange = findViewById(R.id.manager_boss_info_btn_change_info);
    }

    private void initData() {
        Intent intent = getIntent();
        bossId = intent.getIntExtra("bossId", 0);
        getBossInfo();
        editTextBossName.setEnabled(false);
        editTextBossTelePhone.setEnabled(false);
        editTextBossIntro.setEnabled(false);
        editTextBossPosition.setEnabled(false);
        imageButtonDocumentReduce.setVisibility(View.GONE);
        imageButtonDocumentPlus.setVisibility(View.GONE);
        imageButtonPhotoReduce.setVisibility(View.GONE);
        imageButtonPhotoPlus.setVisibility(View.GONE);
        //店铺照片加号点击事件
        imageButtonPhotoPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewPhotoOne.getDrawable() == null) {
                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN1);
                } else if (imageViewPhotoTwo.getDrawable() == null) {
                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN2);
                } else if (imageViewPhotoThree.getDrawable() == null) {
                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN3);
                } else if (imageViewPhotoFour.getDrawable() == null) {
                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN4);
                } else if (imageViewPhotoFive.getDrawable() == null) {
                    imagePlus(PHOTO_REQUEST_GALLERY_MAIN5);
                }
            }
        });
        //店铺照片减号点击事件
        imageButtonPhotoReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewPhotoFive.getDrawable() != null) {
                    countImg--;
                    imageViewPhotoFive.setImageDrawable(null);
                    imageViewPhotoFive.setVisibility(View.GONE);
                    imageButtonPhotoPlus.setVisibility(View.VISIBLE);
                } else if (imageViewPhotoFour.getDrawable() != null) {
                    countImg--;
                    imageViewPhotoFour.setImageDrawable(null);
                    imageViewPhotoFour.setVisibility(View.GONE);
                } else if (imageViewPhotoThree.getDrawable() != null) {
                    countImg--;
                    imageViewPhotoThree.setImageDrawable(null);
                    imageViewPhotoThree.setVisibility(View.GONE);
                } else if (imageViewPhotoTwo.getDrawable() != null) {
                    countImg--;
                    imageViewPhotoTwo.setImageDrawable(null);
                    imageViewPhotoTwo.setVisibility(View.GONE);
                } else if (imageViewPhotoOne.getDrawable() != null) {
                    countImg--;
                    imageViewPhotoOne.setImageDrawable(null);
                    imageViewPhotoOne.setVisibility(View.GONE);
                    imageButtonPhotoReduce.setVisibility(View.GONE);
                }
            }
        });
        //店铺证明加号点击事件
        imageButtonDocumentPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePlus(PHOTO_REQUEST_GALLERY_MAIN6);
            }
        });
        //店铺证明减号点击事件
        imageButtonDocumentReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewBossDocument.setImageDrawable(null);
                imageViewBossDocument.setVisibility(View.GONE);
                imageButtonDocumentReduce.setVisibility(View.GONE);
                imageButtonDocumentPlus.setVisibility(View.VISIBLE);
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
                AlertDialog alertDialog = new AlertDialog.Builder(ManagerBossInfoActivity.this).create();
                alertDialog.setTitle("提示");
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setMessage("请确认是否删除店铺！(将会删除该店铺所有相关信息，以及更改店铺老板权限)");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBossInfo();
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
                    editTextBossName.setEnabled(true);
                    editTextBossPosition.setEnabled(true);
                    editTextBossIntro.setEnabled(true);
                    editTextBossTelePhone.setEnabled(true);

                    if (imageViewPhotoFive.getDrawable() != null) {
                        imageButtonPhotoPlus.setVisibility(View.GONE);
                        imageButtonPhotoReduce.setVisibility(View.VISIBLE);
                    } else {
                        imageButtonPhotoPlus.setVisibility(View.VISIBLE);
                        imageButtonPhotoReduce.setVisibility(View.VISIBLE);
                    }
                    imageButtonDocumentReduce.setVisibility(View.VISIBLE);
                } else {
                    checkNullInfo();
                }
            }
        });
    }

    /**
     * bitmap图片转字符串
     *
     * @param bitmap 图片资源
     * @return 返回字符串传回服务器
     */
    private static String bitmapToString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream btString = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, btString);
        byte[] bytes = btString.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 通过店铺ID获取店铺表相应全部信息
     */
    private void getBossInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_BOSS_INFO,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {
                        final BossInfoBean newDatas = new Gson().fromJson(s, BossInfoBean.class);
                        if (newDatas != null) {
                            textViewBossId.setText(newDatas.getBossId() + "");
                            textViewUserId.setText(newDatas.getUserId() + "");
                            editTextBossName.setText(newDatas.getBossName());
                            editTextBossIntro.setText(newDatas.getBossInformation());
                            editTextBossTelePhone.setText(newDatas.getBossTelephone() + "");
                            editTextBossPosition.setText(newDatas.getBossPosition());
                            textViewReceiptType.setText(newDatas.getBossReceiptType());
                            textViewReceiptId.setText(newDatas.getBossReceiptId());
                            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + newDatas.getBossPhotoOne(),
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            imageViewPhotoOne.setImageBitmap(bitmap);
                                            imageViewPhotoOne.setVisibility(View.VISIBLE);
                                            countImg++;
                                        }
                                    }, null);
                            if (newDatas.getBossPhotoTwo() != null && !newDatas.getBossPhotoTwo().equals("")) {
                                ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + newDatas.getBossPhotoTwo(),
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                imageViewPhotoTwo.setImageBitmap(bitmap);
                                                imageViewPhotoTwo.setVisibility(View.VISIBLE);
                                                countImg++;
                                            }
                                        }, null);
                                if (newDatas.getBossPhotoThree() != null && !newDatas.getBossPhotoThree().equals("")) {
                                    ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + newDatas.getBossPhotoThree(),
                                            new Response.Listener<Bitmap>() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    imageViewPhotoThree.setImageBitmap(bitmap);
                                                    imageViewPhotoThree.setVisibility(View.VISIBLE);
                                                    countImg++;
                                                }
                                            }, null);
                                    if (newDatas.getBossPhotoFour() != null && !newDatas.getBossPhotoFour().equals("")) {
                                        ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + newDatas.getBossPhotoFour(),
                                                new Response.Listener<Bitmap>() {
                                                    @Override
                                                    public void onResponse(Bitmap bitmap) {
                                                        imageViewPhotoFour.setImageBitmap(bitmap);
                                                        imageViewPhotoFour.setVisibility(View.VISIBLE);
                                                        countImg++;
                                                    }
                                                }, null);
                                        if (newDatas.getBossPhotoFive() != null && !newDatas.getBossPhotoFive().equals("")) {
                                            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + newDatas.getBossPhotoFive(),
                                                    new Response.Listener<Bitmap>() {
                                                        @Override
                                                        public void onResponse(Bitmap bitmap) {
                                                            imageViewPhotoFive.setImageBitmap(bitmap);
                                                            imageViewPhotoFive.setVisibility(View.VISIBLE);
                                                            countImg++;
                                                        }
                                                    }, null);
                                        }
                                    }
                                }
                            }
                            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + newDatas.getBossDocument(),
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            imageViewBossDocument.setImageBitmap(bitmap);
                                            imageViewBossDocument.setVisibility(View.VISIBLE);
                                        }
                                    }, null);


                        } else {
                            getBossInfo();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("bossId", bossId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 通过店铺ID删除店铺一切相关信息并更改老板权限
     */
    private void deleteBossInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.DELETE_BOSS_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isDelete = new Gson().fromJson(s, Boolean.class);
                        if (isDelete) {
                            finish();
                        } else {
                            Toast.makeText(ManagerBossInfoActivity.this, "删除信息失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerBossInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("bossId", bossId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 表单更改后提交格式检查
     */
    private void checkNullInfo() {
        if (editTextBossName.getText().toString().equals("")) {
            Toast.makeText(ManagerBossInfoActivity.this, "店铺名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossIntro.getText().toString().equals("")) {
            Toast.makeText(ManagerBossInfoActivity.this, "店铺介绍不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossPosition.getText().toString().equals("")) {
            Toast.makeText(ManagerBossInfoActivity.this, "店铺地址不能为空！", Toast.LENGTH_SHORT).show();
        } else if (editTextBossTelePhone.getText().toString().equals("")) {
            Toast.makeText(ManagerBossInfoActivity.this, "店铺联系方式不能为空！", Toast.LENGTH_SHORT).show();
        } else if (imageViewPhotoOne.getDrawable() == null) {
            Toast.makeText(ManagerBossInfoActivity.this, "店铺照片不能为空！", Toast.LENGTH_SHORT).show();
        } else if (imageViewBossDocument.getDrawable() == null) {
            Toast.makeText(ManagerBossInfoActivity.this, "店铺证明材料不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            switch (countImg) {
                case 1:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewPhotoOne.getDrawable()).getBitmap());
                    imageMain2 = "";
                    imageMain3 = "";
                    imageMain4 = "";
                    imageMain5 = "";
                    break;
                case 2:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewPhotoOne.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewPhotoTwo.getDrawable()).getBitmap());
                    imageMain3 = "";
                    imageMain4 = "";
                    imageMain5 = "";
                    break;
                case 3:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewPhotoOne.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewPhotoTwo.getDrawable()).getBitmap());
                    imageMain3 = bitmapToString(((BitmapDrawable) imageViewPhotoThree.getDrawable()).getBitmap());
                    imageMain4 = "";
                    imageMain5 = "";
                    break;
                case 4:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewPhotoOne.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewPhotoTwo.getDrawable()).getBitmap());
                    imageMain3 = bitmapToString(((BitmapDrawable) imageViewPhotoThree.getDrawable()).getBitmap());
                    imageMain4 = bitmapToString(((BitmapDrawable) imageViewPhotoFour.getDrawable()).getBitmap());
                    imageMain5 = "";
                    break;
                case 5:
                    imageMain1 = bitmapToString(((BitmapDrawable) imageViewPhotoOne.getDrawable()).getBitmap());
                    imageMain2 = bitmapToString(((BitmapDrawable) imageViewPhotoTwo.getDrawable()).getBitmap());
                    imageMain3 = bitmapToString(((BitmapDrawable) imageViewPhotoThree.getDrawable()).getBitmap());
                    imageMain4 = bitmapToString(((BitmapDrawable) imageViewPhotoFour.getDrawable()).getBitmap());
                    imageMain5 = bitmapToString(((BitmapDrawable) imageViewPhotoFive.getDrawable()).getBitmap());
                    break;
                default:
                    break;
            }
            imageDocument = bitmapToString(((BitmapDrawable) imageViewBossDocument.getDrawable()).getBitmap());
            changeBossInfo();
        }
    }

    /**
     * 通过店铺ID更改店铺基本信息
     */
    private void changeBossInfo() {
        Log.i("bossId", bossId + "");
        Log.i("bossPhoto1", imageMain1);
        Log.i("bossPhoto2", imageMain2);
        Log.i("bossPhoto3", imageMain3);
        Log.i("bossPhoto4", imageMain4);
        Log.i("bossPhoto5", imageMain5);
        Log.i("bossPhoto6", imageDocument);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URLManager.CHANGE_BOSS_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isChange = new Gson().fromJson(s, Boolean.class);
                        if (isChange) {
                            buttonChange.setText("信息更改");
                            buttonDelete.setEnabled(true);
                            imageButtonDocumentPlus.setVisibility(View.GONE);
                            imageButtonDocumentReduce.setVisibility(View.GONE);
                            imageButtonPhotoPlus.setVisibility(View.GONE);
                            imageButtonPhotoReduce.setVisibility(View.GONE);
                            editTextBossName.setEnabled(false);
                            editTextBossPosition.setEnabled(false);
                            editTextBossIntro.setEnabled(false);
                            editTextBossTelePhone.setEnabled(false);
                            System.gc();
                            Toast.makeText(ManagerBossInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManagerBossInfoActivity.this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ManagerBossInfoActivity.this, "网络错误，请重试！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("bossId", bossId + "");
                map.put("bossName", editTextBossName.getText().toString());
                map.put("bossInfo", editTextBossIntro.getText().toString());
                map.put("bossPosition", editTextBossPosition.getText().toString());
                map.put("bossTelePhone", editTextBossTelePhone.getText().toString());
                map.put("bossPhotoOne", imageMain1);
                map.put("bossPhotoTwo", imageMain2);
                map.put("bossPhotoThree", imageMain3);
                map.put("bossPhotoFour", imageMain4);
                map.put("bossPhotoFive", imageMain5);
                map.put("bossDocument", imageDocument);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY_MAIN1) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {
                    if (uri != null) {
                        bitmap = ImageConpressUtl.compressBitmap(ManagerBossInfoActivity.this, uri, 250, 150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewPhotoOne.setImageBitmap(bitmap);
                    imageViewPhotoOne.setVisibility(View.VISIBLE);
                    imageButtonPhotoReduce.setVisibility(View.VISIBLE);
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
                        bitmap = ImageConpressUtl.compressBitmap(ManagerBossInfoActivity.this, uri, 250, 150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewPhotoTwo.setImageBitmap(bitmap);
                    imageViewPhotoTwo.setVisibility(View.VISIBLE);
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
                        bitmap = ImageConpressUtl.compressBitmap(ManagerBossInfoActivity.this, uri, 250, 150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewPhotoThree.setImageBitmap(bitmap);
                    imageViewPhotoThree.setVisibility(View.VISIBLE);
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
                        bitmap = ImageConpressUtl.compressBitmap(ManagerBossInfoActivity.this, uri, 250, 150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewPhotoFour.setImageBitmap(bitmap);
                    imageViewPhotoFour.setVisibility(View.VISIBLE);
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
                        bitmap = ImageConpressUtl.compressBitmap(ManagerBossInfoActivity.this, uri, 250, 150);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    countImg++;
                    imageViewPhotoFive.setImageBitmap(bitmap);
                    imageViewPhotoFive.setVisibility(View.VISIBLE);
                    imageButtonPhotoPlus.setVisibility(View.GONE);
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
                        bitmap = ImageConpressUtl.compressBitmap(ManagerBossInfoActivity.this, uri, 500, 300);
                    } else {
                        bitmap = data.getParcelableExtra("data");
                    }
                    imageViewBossDocument.setImageBitmap(bitmap);
                    imageViewBossDocument.setVisibility(View.VISIBLE);
                    imageButtonDocumentReduce.setVisibility(View.VISIBLE);
                    imageButtonDocumentPlus.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
