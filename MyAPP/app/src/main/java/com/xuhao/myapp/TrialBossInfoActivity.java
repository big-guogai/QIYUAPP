package com.xuhao.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageUtiles;

import java.util.HashMap;
import java.util.Map;

public class TrialBossInfoActivity extends AppCompatActivity {

    private EditText editTextUserId;
    private EditText editTextBossName;
    private EditText editTextBossInformation;
    private EditText editTextBossPosition;
    private EditText editTextBossTelephone;
    private ImageView imageViewPhotoOne;
    private ImageView imageViewPhotoTwo;
    private ImageView imageViewPhotoThree;
    private ImageView imageViewPhotoFour;
    private ImageView imageViewPhotoFive;
    private ImageView imageViewDocument;
    private Button buttonRefuse;
    private Button buttonPass;
    private ImageButton imageButtonBack;
    private TextView textViewCall;
    private Spinner spinner;
    private String receiptType;
    private EditText editTextReceiptId;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_boss_info);
        initView();
        initData();
        spinnerSet();

        //返回按钮点击事件
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //电话审核点击事件
        textViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent();
                intentCall.setAction(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + editTextBossTelephone.getText()));
                startActivity(intentCall);
            }
        });

        //驳回按钮点击事件
        buttonRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(TrialBossInfoActivity.this).create();
                alertDialog.setTitle("提示");
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setMessage("请确认是否驳回！");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuseApplicetion();
                    }
                });
                alertDialog.show();
            }
        });

        //通过按钮点击事件
        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(TrialBossInfoActivity.this).create();
                alertDialog.setTitle("提示");
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setMessage("请确认是否通过！");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (count == 0){
                            passApplication();
                            count++;
                        }
                    }
                });
                alertDialog.show();
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
        Intent intent = getIntent();
        if (intent.getStringExtra("bossReceiptType").equals("银行卡")) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }
        spinner.setEnabled(false);
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

    private void initView() {
        editTextBossInformation = findViewById(R.id.trial_boss_info_boss_information);
        editTextBossName = findViewById(R.id.trial_boss_info_boss_name);
        editTextBossPosition = findViewById(R.id.trial_boss_info_boss_position);
        editTextBossTelephone = findViewById(R.id.trial_boss_info_boss_telephone);
        editTextUserId = findViewById(R.id.trial_boss_info_user_id);
        imageViewPhotoOne = findViewById(R.id.trial_boss_info_boss_photo_main1);
        imageViewPhotoTwo = findViewById(R.id.trial_boss_info_boss_photo_main2);
        imageViewPhotoThree = findViewById(R.id.trial_boss_info_boss_photo_main3);
        imageViewPhotoFour = findViewById(R.id.trial_boss_info_boss_photo_main4);
        imageViewPhotoFive = findViewById(R.id.trial_boss_info_boss_photo_main5);
        imageViewDocument = findViewById(R.id.trial_boss_info_boss_document);
        buttonPass = findViewById(R.id.trial_boss_info_btn_pass);
        buttonRefuse = findViewById(R.id.trial_boss_info_btn_refuse);
        imageButtonBack = findViewById(R.id.trial_boss_info_back);
        textViewCall = findViewById(R.id.trial_boss_info_boss_telephone_call);
        spinner = findViewById(R.id.trial_boss_info_receipt_spinner);
        editTextReceiptId = findViewById(R.id.trial_boss_info_receipt_id);
    }

    private void initData() {
        Intent intent = getIntent();
        editTextUserId.setText(String.valueOf(intent.getLongExtra("userId", 0)));
        editTextBossName.setText(intent.getStringExtra("bossName"));
        editTextBossInformation.setText(intent.getStringExtra("bossInformation"));
        editTextBossPosition.setText(intent.getStringExtra("bossPosition"));
        editTextBossTelephone.setText(String.valueOf(intent.getLongExtra("bossTelephone", 0)));

        ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoOne"), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageViewPhotoOne.setImageBitmap(bitmap);
            }
        }, null);
        if (intent.getStringExtra("bossPhotoTwo") == null) {
        } else if (intent.getStringExtra("bossPhotoThree") == null) {
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoTwo"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoTwo.setImageBitmap(bitmap);
                }
            }, null);
            imageViewPhotoTwo.setVisibility(View.VISIBLE);
        } else if (intent.getStringExtra("bossPhotoFour") == null) {
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoTwo"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoTwo.setImageBitmap(bitmap);
                    imageViewPhotoTwo.setVisibility(View.VISIBLE);
                }
            }, null);
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoThree"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoThree.setImageBitmap(bitmap);
                    imageViewPhotoThree.setVisibility(View.VISIBLE);
                }
            }, null);
        } else if (intent.getStringExtra("bossPhotoFive") == null) {
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoTwo"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoTwo.setImageBitmap(bitmap);
                    imageViewPhotoTwo.setVisibility(View.VISIBLE);
                }
            }, null);
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoThree"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoThree.setImageBitmap(bitmap);
                    imageViewPhotoThree.setVisibility(View.VISIBLE);
                }
            }, null);
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoFour"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoFour.setImageBitmap(bitmap);
                    imageViewPhotoFour.setVisibility(View.VISIBLE);
                }
            }, null);
        } else {
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoTwo"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoTwo.setImageBitmap(bitmap);
                    imageViewPhotoTwo.setVisibility(View.VISIBLE);
                }
            }, null);
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoThree"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoThree.setImageBitmap(bitmap);
                    imageViewPhotoThree.setVisibility(View.VISIBLE);
                }
            }, null);
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoFour"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoFour.setImageBitmap(bitmap);
                    imageViewPhotoFour.setVisibility(View.VISIBLE);
                }
            }, null);
            ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossPhotoFive"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageViewPhotoFive.setImageBitmap(bitmap);
                    imageViewPhotoFive.setVisibility(View.VISIBLE);
                }
            }, null);
        }
        ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + intent.getStringExtra("bossDocument"), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageViewDocument.setImageBitmap(bitmap);
                imageViewDocument.setVisibility(View.VISIBLE);
            }
        }, null);
        editTextReceiptId.setText(intent.getStringExtra("bossReceiptId"));
    }

    /**
     * 驳回申请
     */
    private void refuseApplicetion() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.MANAGER_REFUSE_APPLICETION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isRefuse = new Gson().fromJson(s, Boolean.class);
                        if (isRefuse) {
                            Toast.makeText(TrialBossInfoActivity.this, "申请已驳回", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(TrialBossInfoActivity.this, "驳回失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(TrialBossInfoActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent intent = getIntent();
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(intent.getLongExtra("userId", 0)));
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 通过申请
     */
    private void passApplication() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.MANAGER_PASS_APPLICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isPass = new Gson().fromJson(s, Boolean.class);
                        if (isPass) {
                            Toast.makeText(TrialBossInfoActivity.this, "审核通过，已处理！", Toast.LENGTH_SHORT).show();
                            count--;
                            finish();
                        } else {
                            Toast.makeText(TrialBossInfoActivity.this, "审核失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(TrialBossInfoActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent intent = getIntent();
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(intent.getLongExtra("userId", 0)));
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
