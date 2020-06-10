package com.xuhao.myapp.utills;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

public class ShareUtill {


    private static final String QQ_APP_ID = "1110208541";
    private Tencent tencent;

    public static String getQqAppId() {
        return QQ_APP_ID;
    }

    public Tencent getTencent() {
        return tencent;
    }

    public void setTencent(Tencent tencent) {
        this.tencent = tencent;
    }

    public ShareUtill() {
        super();
    }

    /**
     * 要分享必须先注册(QQ)
     */
    public void regToQQ(Context context) {
        tencent = Tencent.createInstance(QQ_APP_ID, context);
    }

    /**
     * 分享到QQ好友
     * @param activity activity
     * @param url 点击链接
     * @param shareTitle 分享标题
     * @param description 描述
     * @param uiListener uiListener
     */
    public void shareToQQ(Activity activity, String url, String shareTitle, String description, IUiListener uiListener) {
        Bundle qqParams = new Bundle();
        qqParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        qqParams.putString(QQShare.SHARE_TO_QQ_TITLE, shareTitle);
        qqParams.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        qqParams.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        qqParams.putString(QQShare.SHARE_TO_QQ_APP_NAME, "奇育");
        tencent.shareToQQ(activity, qqParams, uiListener);
    }
}
