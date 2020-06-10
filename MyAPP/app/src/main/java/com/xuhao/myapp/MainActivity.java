package com.xuhao.myapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.*;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.xuhao.myapp.Bean.UserInfoBean;
import com.xuhao.myapp.View.CircleImageView;
import com.xuhao.myapp.fragment.BaseLazyFragment;
import com.xuhao.myapp.fragment.BossFragment;
import com.xuhao.myapp.fragment.CourseFragment;
import com.xuhao.myapp.fragment.IndentFragment;
import com.xuhao.myapp.fragment.InformationFragment;
import com.xuhao.myapp.fragment.ManagerFragment;
import com.xuhao.myapp.fragment.TrialFragment;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageUtiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView navigationView;
    private Map<String, Fragment> map = new HashMap<>();
    private Fragment nowfragment = new Fragment();
    boolean isCanExit = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                isCanExit = false;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_into, R.anim.left_out);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        final CircleImageView circleImageViewCourseMainHead = findViewById(R.id.main_course_head);
        ///获取本地存储用户头像
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //判断是否登录后存储了头像路径
        if (sp.getLong("userId", 0) != 0) {
            ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + sp.getString("userHead", null),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            //设置课程页面头像图片
                            circleImageViewCourseMainHead.setImageBitmap(bitmap);
                        }
                    }, null);
        } else {
            circleImageViewCourseMainHead.setImageResource(R.mipmap.head);
        }
        checkUserType(sp);

    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CityListLoader.getInstance().loadCityData(this);
        navigationView = findViewById(R.id.main_menu);
        navigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigationView.setLabelVisibilityMode(0);
        checkSavaUser();

        //底部导航栏绑定初始fragment
        CourseFragment courseFragment = new CourseFragment();
        map.put(CourseFragment.class.getSimpleName(), courseFragment);
        switchFragment(courseFragment);
    }

    /**
     * 检查权限启动不同的页面
     */
    private void checkUserType(SharedPreferences sp) {
        if (sp.getInt("userTypeId", 0) == 103) {
            navigationView.getMenu().getItem(1).setTitle("审核");
            navigationView.getMenu().getItem(2).setTitle("管理");
            navigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_main_course:
                                    Fragment fgcourse = map.get(CourseFragment.class.getSimpleName());
                                    if (fgcourse == null) {
                                        fgcourse = new CourseFragment();
                                        map.put(CourseFragment.class.getSimpleName(), fgcourse);
                                    }
                                    switchFragment(fgcourse);
                                    break;
                                case R.id.menu_main_boss:
                                    Fragment fgtrail = map.get(TrialFragment.class.getSimpleName());
                                    if (fgtrail == null) {
                                        fgtrail = new TrialFragment();
                                        map.put(TrialFragment.class.getSimpleName(), fgtrail);
                                    }
                                    switchFragment(fgtrail);
                                    break;
                                case R.id.menu_main_indent:
                                    Fragment fgmanager = map.get(ManagerFragment.class.getSimpleName());
                                    if (fgmanager == null) {
                                        fgmanager = new ManagerFragment();
                                        map.put(ManagerFragment.class.getSimpleName(), fgmanager);
                                    }
                                    switchFragment(fgmanager);
                                    break;
                                case R.id.menu_main_information:
                                    Fragment fginformation = map.get(InformationFragment.class.getSimpleName());
                                    if (fginformation == null) {
                                        fginformation = new InformationFragment();
                                        map.put(InformationFragment.class.getSimpleName(), fginformation);
                                    }
                                    switchFragment(fginformation);
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });
        } else {
            navigationView.getMenu().getItem(1).setTitle("店铺");
            navigationView.getMenu().getItem(2).setTitle("订单");
            navigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_main_course:
                                    Fragment fgcourse = map.get(CourseFragment.class.getSimpleName());
                                    if (fgcourse == null) {
                                        fgcourse = new CourseFragment();
                                        map.put(CourseFragment.class.getSimpleName(), fgcourse);
                                    }
                                    switchFragment(fgcourse);
                                    break;
                                case R.id.menu_main_boss:
                                    Fragment fgboss = map.get(BossFragment.class.getSimpleName());
                                    if (fgboss == null) {
                                        fgboss = new BossFragment();
                                        map.put(BossFragment.class.getSimpleName(), fgboss);
                                    }
                                    switchFragment(fgboss);
                                    break;
                                case R.id.menu_main_indent:
                                    Fragment fgindent = map.get(IndentFragment.class.getSimpleName());
                                    if (fgindent == null) {
                                        fgindent = new IndentFragment();
                                        map.put(IndentFragment.class.getSimpleName(), fgindent);
                                    }
                                    switchFragment(fgindent);
                                    break;
                                case R.id.menu_main_information:
                                    Fragment fginformation = map.get(InformationFragment.class.getSimpleName());
                                    if (fginformation == null) {
                                        fginformation = new InformationFragment();
                                        map.put(InformationFragment.class.getSimpleName(), fginformation);
                                    }
                                    switchFragment(fginformation);
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });
        }
    }

    /**
     * 页面初始化自动登录上次信息
     */
    private void checkSavaUser() {
        SharedPreferences sp = getSharedPreferences("userLoginInfo", MODE_PRIVATE);
        if (sp.getLong("userId", 0) != 0) {
            reLogin(sp);
        }
    }

    /**
     * 信息编辑成功重新登录
     */
    private void reLogin(final SharedPreferences sp) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.USER_LOGIN_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        UserInfoBean bean = new Gson().fromJson(s, UserInfoBean.class);
                        if (bean != null) {
                            userInfosave(bean);
                            Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();

                            //登录成功设置主页头像
                            final CircleImageView circleImageViewCourseMainHead = findViewById(R.id.main_course_head);
                            ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + bean.getUserhead(),
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            //设置课程页面头像图片
                                            circleImageViewCourseMainHead.setImageBitmap(bitmap);
                                        }
                                    }, null);
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                            checkUserType(sharedPreferences);
                        } else {
                            Toast.makeText(MainActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userid", String.valueOf(sp.getLong("userId", 0)));
                map.put("password", Objects.requireNonNull(sp.getString("userPwd", null)));
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }

    /**
     * 存储登录成功获取到的数据
     *
     * @param bean
     */
    private void userInfosave(UserInfoBean bean) {
        //用户基本信息存储
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
        editor.putString("userName", bean.getUsername());
        editor.putLong("userId", bean.getUserid());
        editor.putString("userPwd", bean.getUserpassword());
        editor.putInt("userAge", bean.getUserage());
        editor.putString("userHead", bean.getUserhead());
        editor.putString("usersex", bean.getUsersex());
        editor.putInt("userTypeId", bean.getUsertypeid());
        editor.apply();

        //用户上一次登录存储账号密码头像姓名
        SharedPreferences sp1 = getSharedPreferences("userLoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putLong("userId", bean.getUserid());
        editor1.putString("userPwd", bean.getUserpassword());
        editor1.putString("userHead", bean.getUserhead());
        editor1.putString("userName", bean.getUsername());
        editor1.apply();
    }

    /**
     * fragment展示
     *
     * @param fragment 要显示的fragment
     */
    private void switchFragment(Fragment fragment) {
        if (nowfragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) {
                transaction.add(R.id.main_container, fragment);
            }
            transaction.hide(nowfragment).show(fragment).commit();
            nowfragment = fragment;
        }
    }

    /**
     * 搜索框点击触发
     */
    public void courseSearch(String newdress) {
        Intent intent = new Intent(MainActivity.this, CourseSearchActivity.class);
        intent.putExtra("newdress", newdress);
        startActivity(intent);
    }

    /**
     * 地址选择点击触发
     */
    public void dressPick() {
        Intent intent = new Intent(MainActivity.this, CityListSelectActivity.class);
        startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
    }

    /**
     * 返回键抬起方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isCanExit) {
                finish();
            } else {
                isCanExit = true;
                Toast.makeText(MainActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(-1, 1000);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 城市选择传值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CityListSelectActivity.CITY_SELECT_RESULT_FRAG) {

        }
        if (resultCode == Activity.RESULT_OK) {

        }
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();

        CityInfoBean cityInfoBean = bundle.getParcelable("cityinfo");

        if (null == cityInfoBean) {
            return;
        }

        //发送广播传送数据
        String cs = cityInfoBean.getName();
        String action_update = "action_update_fragment_button_text";
        Intent intentString = new Intent(action_update);
        intentString.putExtra("data", cs);
        sendBroadcast(intentString);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
        //软件退出删除临时信息表
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
