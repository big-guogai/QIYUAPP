package com.xuhao.myapp.internet;


import com.android.volley.toolbox.StringRequest;

import java.text.Bidi;

public class URLManager {
    // 云服务器地址；
    private static final String SERVICE_ADDRESS = "http://123.56.248.183:8080/QiyuServers/";
    //本地wifi地址；
//    private static final String SERVICE_ADDRESS = "http://192.168.155.1:8080/QiyuServers/";
    // 本地服务器地址；
//    private static final String SERVICE_ADDRESS = "http://192.168.137.1:8080/QiyuServers/";
    //获取所有课程地址
    public static final String COURSE_ALL = SERVICE_ADDRESS + "servlet/CourseAllItemServlet";
    //获取分类课程地址
    public static final String COURSE_KIND = SERVICE_ADDRESS + "servlet/CourseKindItemServlet";
    //获取搜索课程地址
    public static final String COURSE_SEARCH = SERVICE_ADDRESS + "servlet/CourseSearchItemServlet";
    //获取课程主要信息地址
    public static final String COURSE_MAININFO = SERVICE_ADDRESS + "servlet/CourseMainInfoServlet";
    //登录获取用户信息地址
    public static final String USER_LOGIN_INFO = SERVICE_ADDRESS + "servlet/LoginUserInfoServlet";
    //用户注册返回信息地址
    public static final String USER_REGISTERED_INFO = SERVICE_ADDRESS + "servlet/RegisteredServlet";
    //用户上传商铺审核返回信息地址
    public static final String USER_REGISTERED_BOSS_CHECK_INFO = SERVICE_ADDRESS + "servlet/SaveCheckBossInfoServlet";
    //用户获取自己拥有的店铺信息地址
    public static final String USER_BOSS_INFO = SERVICE_ADDRESS + "servlet/GetBossInfoServlet";
    //商户用户获取自己拥有的课程信息地址
    public static final String USER_BOSS_COURSE_INFO = SERVICE_ADDRESS + "servlet/GetBossCourseInfoServlet";
    //商户添加课程信息地址
    public static final String USER_BOSS_COURSE_ADD_INFO = SERVICE_ADDRESS + "servlet/AddBossCourseInfoServlet";
    //管理员获取审核信息地址
    public static final String MANAGER_TRIAL_INFO = SERVICE_ADDRESS + "servlet/GetCheckBossInfoServlet";
    //管理员驳回申请审核地址
    public static final String MANAGER_REFUSE_APPLICETION = SERVICE_ADDRESS + "servlet/RefuseBossApplicationServlet";
    //管理员通过申请审核地址
    public static final String MANAGER_PASS_APPLICATION = SERVICE_ADDRESS + "servlet/PassBossApplicationServlet";
    //获取头像路径列表地址
    public static final String HEAD_URL_LEST = SERVICE_ADDRESS + "servlet/GetHeadUrlListServlet";
    //编辑用户个人信息地址
    public static final String USER_INFO_EDIT = SERVICE_ADDRESS + "servlet/EditUserInfoServlet";
    //编辑店铺信息地址
    public static final String BOSS_INFO_EDIT = SERVICE_ADDRESS + "servlet/EditBossInfoServlet";
    //编辑店铺课程信息地址
    public static final String BOSS_COURSE_INFO_EDIT = SERVICE_ADDRESS + "servlet/EditBossCourseInfoServlet";
    //获取支付宝订单商品信息地址
    public static final String COURSE_BUY_ALIPAY = SERVICE_ADDRESS + "servlet/ApplicationAliPayServlet";
    //图片地址
    public static final String IMAGE_ADDRESS = SERVICE_ADDRESS + "img/";
    //头像地址
    public static final String HEAD_IMAGE_ADDRESS = SERVICE_ADDRESS + "img/head/";
    //获取历史订单地址
    public static final String HISTORY_INDENT = SERVICE_ADDRESS + "servlet/GetHistoryIndentServlet";
    //创建新的订单地址
    public static final String CREATE_INDENT = SERVICE_ADDRESS + "servlet/CreateIndentServlet";
    //获取相关订单信息地址
    public static final String GET_INDENT_INFO = SERVICE_ADDRESS + "servlet/GetIndentInfoServlet";
    //支付成功改变订单状态地址
    public static final String PAY_SUCCESS_CHANGE_INDENT_TYPE = SERVICE_ADDRESS + "servlet/PaySuccessChangeTypeServlet";
    //取消未支付订单地址
    public static final String CANCLE_NO_PAY_INDENT = SERVICE_ADDRESS + "servlet/CancleIndentServlet";
    //店铺获取订单信息地址
    public static final String BOSS_INDENT_INFO = SERVICE_ADDRESS + "servlet/GetBossIndentServlet";
    //处理成功更改订单状态地址
    public static final String HANDLE_SUCCESS_CHANGE_INFENT_TYPE = SERVICE_ADDRESS + "servlet/HandleSuccessChangeTypeServlet";
    //管理员获取用户表信息地址
    public static final String GET_ALL_USER_INFO = SERVICE_ADDRESS + "servlet/GetAllUserInfoServlet";
    //管理员获取店铺表信息地址
    public static final String GET_ALL_BOSS_INFO = SERVICE_ADDRESS + "servlet/GetAllBossInfoServlet";
    //管理员获取课程商品表信息地址
    public static final String GET_ALL_COURSE_INFO = SERVICE_ADDRESS + "servlet/GetAllCourseInfoServlet";
    //管理员获取订单表信息地址
    public static final String GET_ALL_INDENT_INFO = SERVICE_ADDRESS + "servlet/GetAllIndentInfoServlet";
    //管理员获取单个帐号信息地址
    public static final String GET_USER_INFO = SERVICE_ADDRESS + "servlet/GetUserInfoServlet";
    //管理员删除单个账号信息地址
    public static final String DELETE_USER_INFO = SERVICE_ADDRESS + "servlet/DeleteUserInfoServlet";
    //管理员更改单个账号信息地址
    public static final String CHANGE_USER_INFO = SERVICE_ADDRESS + "servlet/ChangeUserInfoServlet";
    //管理员获取单个店铺信息地址
    public static final String GET_BOSS_INFO = SERVICE_ADDRESS + "servlet/GetOneBossInfoServlet";
    //管理员删除单个店铺信息地址
    public static final String DELETE_BOSS_INFO = SERVICE_ADDRESS + "servlet/DeleteBossInfoServlet";
    //管理员更改单个店铺信息地址
    public static final String CHANGE_BOSS_INFO = SERVICE_ADDRESS + "servlet/ChangeBossInfoServlet";
    //管理员获取单个课程信息地址
    public static final String GET_COURSE_INFO = SERVICE_ADDRESS + "servlet/GetCourseInfoServlet";
    //管理员删除单个课程信息地址
    public static final String DELETE_COURSE_INFO = SERVICE_ADDRESS + "servlet/DeleteCourseInfoServlet";
    //管理员更改单个课程信息地址
    public static final String CHANGE_COURSE_INFO = SERVICE_ADDRESS + "servlet/ChangeCourseInfoServlet";
    //管理员获取单个订单信息地址
    public static final String GET_ONE_INDENT_INFO = SERVICE_ADDRESS + "servlet/GetOneIndentInfoServlet";
    //获取历史浏览记录课程信息地址
    public static final String GET_HISTORY_BROWSE = SERVICE_ADDRESS + "servlet/GetHistoryBrowseServlet";
    //用户意见反馈文本存储地址
    public static final String SAVE_FEED_BACK_TEXT = SERVICE_ADDRESS + "servlet/SaveFeedBackServlet";
    //管理员查看用户反馈地址
    public static final String GET_FEED_BACK_TEXT = SERVICE_ADDRESS + "servlet/GetFeedBackTextServlet";
    //获取服务器存储安装包版本号
    public static final String GET_SERVICE_VERSION_CODE = SERVICE_ADDRESS + "servlet/GetServiceApkVersionServlet";
    //服务器存储安装包文件路径
    public static final String APK_FILE_URL = SERVICE_ADDRESS + "apk/";
}
