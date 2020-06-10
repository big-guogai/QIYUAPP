package com.xuhao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xuhao.myapp.HistoryBrowseActivity;
import com.xuhao.myapp.MainActivity;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.UserInfoChangeActivity;
import com.xuhao.myapp.utills.ImageUtiles;
import com.xuhao.myapp.LoginActivity;
import com.xuhao.myapp.R;
import com.xuhao.myapp.SettingActivity;
import com.xuhao.myapp.View.CircleImageView;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ShareUtill;
import com.xuhao.myapp.utills.UpdateAPK;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InformationFragment extends Fragment {

    private View mview;
    private TextView textViewIndentChange;
    private TextView textViewBossChange;
    private TextView textViewSetting;
    private TextView textViewHistoryBrowse;
    private TextView textViewCallservice;
    private TextView textViewUseHelp;
    private TextView textViewFeedBack;
    private TextView textViewCheckUpdata;
    private TextView textViewAboutUS;
    private TextView textViewWeChatShare;
    private TextView textViewQQShare;
    private TextView textViewPYQShare;
    private Button buttonloginname;
    private Window window;
    private View popView;
    private PopupWindow popupWindow;
    private Button buttonPopWindowYes;
    private CircleImageView circleImageViewUserInfoHead;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_information, container, false);
        mview = inflate;
        initView();
        initData();
        return inflate;
    }

    private void initView() {
        circleImageViewUserInfoHead = mview.findViewById(R.id.user_info_head_img);
        textViewIndentChange = mview.findViewById(R.id.user_info_center_my_indent);
        textViewBossChange = mview.findViewById(R.id.user_info_center_my_boss);
        textViewSetting = mview.findViewById(R.id.user_info_down_setting);
        buttonloginname = mview.findViewById(R.id.user_info_head_login);
        textViewHistoryBrowse = mview.findViewById(R.id.user_info_center_history_watch);
        textViewCallservice = mview.findViewById(R.id.user_info_down_call_human_service);
        textViewUseHelp = mview.findViewById(R.id.user_info_down_use_help);
        textViewFeedBack = mview.findViewById(R.id.user_info_down_feed_back);
        textViewCheckUpdata = mview.findViewById(R.id.user_info_down_check_new_apk);
        textViewAboutUS = mview.findViewById(R.id.user_info_down_about_us);
        textViewPYQShare = mview.findViewById(R.id.user_info_recommend_friend_weiXin_circle);
        textViewQQShare = mview.findViewById(R.id.user_info_recommend_friend_qq_friend);
        textViewWeChatShare = mview.findViewById(R.id.user_info_recommend_friend_weiXin_friend);
    }

    private void initData() {
        //头像点击事件
        circleImageViewUserInfoHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                if (sp.getLong("userId", 0) == 0) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    Toast.makeText(getContext(), "您还没有登录呢，先登录吧！", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), UserInfoChangeActivity.class);
                    startActivity(intent);
                }
            }
        });

        //我的订单点击事件
        textViewIndentChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.navigationView.setSelectedItemId(R.id.menu_main_indent);
            }
        });

        //我的店铺点击事件
        textViewBossChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.navigationView.setSelectedItemId(R.id.menu_main_boss);
            }
        });

        //历史浏览点击事件
        textViewHistoryBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryBrowseActivity.class);
                startActivity(intent);
            }
        });

        //设置条目点击事件
        textViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        //登录按钮点击事件
        buttonloginname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //呼叫人工客户点击事件
        textViewCallservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent();
                intentCall.setAction(Intent.ACTION_DIAL);
                if (textViewCallservice.getText() == "意见反馈") {
                    intentCall.setData(Uri.parse("tel:17738750029"));
                } else {
                    intentCall.setData(Uri.parse("tel:15680663820"));
                }
                startActivity(intentCall);
            }
        });
        //使用帮助点击事件
        textViewUseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window = Objects.requireNonNull(getActivity()).getWindow();
                final WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = 0.7f;
                window.setAttributes(params);
                popView = getLayoutInflater().inflate(R.layout.use_help_popwindow, null);
                popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                ColorDrawable colorDrawable = new ColorDrawable();
                popupWindow.setBackgroundDrawable(colorDrawable);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(mview, Gravity.CENTER, 0, 0);
                buttonPopWindowYes = popView.findViewById(R.id.use_help_popwindow_btn_canCle);
                buttonPopWindowYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (window != null) {
                            WindowManager.LayoutParams params1 = window.getAttributes();
                            params1.alpha = 1.0f;
                            window.setAttributes(params1);
                        }
                    }
                });
            }
        });
        //意见反馈点击事件
        textViewFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewFeedBack.getText() == "意见反馈") {
                    window = Objects.requireNonNull(getActivity()).getWindow();
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.alpha = 0.7f;
                    window.setAttributes(params);
                    popView = getLayoutInflater().inflate(R.layout.feed_back_user_popwindow, null);
                    popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(true);
                    ColorDrawable colorDrawable = new ColorDrawable();
                    popupWindow.setBackgroundDrawable(colorDrawable);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(mview, Gravity.CENTER, 0, 0);
                    final EditText editText = popView.findViewById(R.id.feed_back_user_popwindow_edit_feed_text);
                    buttonPopWindowYes = popView.findViewById(R.id.feed_back_user_popwindow_btn_submit);
                    buttonPopWindowYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //提交反馈文本与用户信息拼凑传入服务器存在服务器并借宿窗口返回传递信息
                            if (!editText.getText().toString().trim().equals("")) {
                                SharedPreferences sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                String feedText = "";
                                if (sp.getLong("userId", 0) != 0) {
                                    feedText = sp.getLong("userId", 0) + ")" + editText.getText().toString();
                                } else {
                                    feedText = "游客：" + editText.getText().toString();
                                }
                                saveFeedText(feedText);
                            } else {
                                Toast.makeText(getActivity(), "不能提交空文本！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (window != null) {
                                WindowManager.LayoutParams params1 = window.getAttributes();
                                params1.alpha = 1.0f;
                                window.setAttributes(params1);
                            }
                        }
                    });
                } else {
                    window = Objects.requireNonNull(getActivity()).getWindow();
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.alpha = 0.7f;
                    window.setAttributes(params);
                    popView = getLayoutInflater().inflate(R.layout.feed_back_manager_popwindow, null);
                    popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(true);
                    ColorDrawable colorDrawable = new ColorDrawable();
                    popupWindow.setBackgroundDrawable(colorDrawable);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(mview, Gravity.CENTER, 0, 0);
                    TextView textView = popView.findViewById(R.id.feed_back_manager_popwindow_feed_text);
                    getFeedText(textView);
                    buttonPopWindowYes = popView.findViewById(R.id.feed_back_manager_popwindow_btn_canCle);
                    buttonPopWindowYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (window != null) {
                                WindowManager.LayoutParams params1 = window.getAttributes();
                                params1.alpha = 1.0f;
                                window.setAttributes(params1);
                            }
                        }
                    });
                }
            }
        });
        //检查更新点击事件
        textViewCheckUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UpdateAPK updateAPK = new UpdateAPK(getActivity());
                final String clientVersion = updateAPK.getAppVersionName(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_SERVICE_VERSION_CODE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                String serverVersion = new Gson().fromJson(s, String.class);
                                updateAPK.showNoticeDialog(serverVersion, clientVersion);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                }) {
                };
                QiYuApplication.queue.add(stringRequest);
            }
        });
        //关于我们点击事件
        textViewAboutUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window = Objects.requireNonNull(getActivity()).getWindow();
                final WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = 0.7f;
                window.setAttributes(params);
                popView = getLayoutInflater().inflate(R.layout.about_us_popwindow, null);
                popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                ColorDrawable colorDrawable = new ColorDrawable();
                popupWindow.setBackgroundDrawable(colorDrawable);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(mview, Gravity.CENTER, 0, 0);
                buttonPopWindowYes = popView.findViewById(R.id.about_us_popwindow_btn_canCle);
                buttonPopWindowYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (window != null) {
                            WindowManager.LayoutParams params1 = window.getAttributes();
                            params1.alpha = 1.0f;
                            window.setAttributes(params1);
                        }
                    }
                });
            }
        });
        //QQ分享点击事件
        textViewQQShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToQQFriend();
            }
        });
        //微信好友分享点击事件
        textViewWeChatShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxFriend();
            }
        });
        //微信朋友圈分享点击事件
        textViewPYQShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.startdown);
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(Objects.requireNonNull(getActivity()).getContentResolver(), bitmap, null, null));
                shareToPYQ(uri);
            }
        });
    }

    /**
     * qq好友分享
     */
    @SuppressLint("IntentReset")
    private void shareToQQFriend() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, "奇育app下载：www.baidu.com");
        startActivity(intent);
    }

    /**
     * 微信好友分享
     */
    private void shareToWxFriend() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, "奇育app下载：www.baidu.com");
        startActivity(intent);
    }

    /**
     * 分享信息到朋友圈(注意：微信朋友圈现不支持文本内容分享，可以利用二维码以及图片做相关分享)
     *
     * @param uri ，图片路径;
     */
    private void shareToPYQ(Uri uri) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent);
    }

    /**
     * 管理员账号获取用户反馈文档
     *
     * @param textView 文本展示控件
     */
    private void getFeedText(final TextView textView) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.GET_FEED_BACK_TEXT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String feedText = new Gson().fromJson(s, String.class);
                if (feedText != null && !feedText.equals("")) {
                    textView.setText(feedText);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                textView.setText("网络错误，请重试！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 意见反馈传入服务器存储
     *
     * @param feedText 反馈文本
     */
    private void saveFeedText(final String feedText) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.SAVE_FEED_BACK_TEXT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Boolean isSave = new Gson().fromJson(s, Boolean.class);
                if (isSave) {
                    Toast.makeText(getActivity(), "您的反馈提交成功！", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(getActivity(), "提交失败请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("feedText", feedText);
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 身份权限检查做相关展示操作
     */
    private void checkId() {
        SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getInt("userTypeId", 0) == 103) {
            textViewBossChange.setText("店铺申请");
            textViewIndentChange.setText("信息管理");
            textViewCallservice.setText("呼叫程序猿");
            textViewFeedBack.setText("用户反馈");
        } else {
            textViewIndentChange.setText("我的订单");
            textViewBossChange.setText("我的店铺");
            textViewCallservice.setText("拨打人工客服");
            textViewFeedBack.setText("意见反馈");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkId();
        SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //判断是否登录后存储了用户信息
        if (sp.getLong("userId", 0) != 0) {
            ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + sp.getString("userHead", null), new Response.Listener<Bitmap>() {

                @Override
                public void onResponse(Bitmap bitmap) {
                    circleImageViewUserInfoHead.setImageBitmap(bitmap);
                }
            }, null);
            String username = sp.getString("userName", null);
            buttonloginname.setText(username);
            buttonloginname.setEnabled(false);
        } else {
            circleImageViewUserInfoHead.setImageResource(R.mipmap.head);
            buttonloginname.setText(R.string.text_login);
            buttonloginname.setEnabled(true);
        }
    }
}