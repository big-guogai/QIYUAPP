package com.xuhao.myapp.utills;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhao.myapp.R;
import com.xuhao.myapp.internet.URLManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class UpdateAPK {
    private Context mContext; //上下文

    private String apkUrl = "apk下载地址"; //apk下载地址
    @SuppressLint("SdCardPath")
    private static final String savePath = "/sdcard/updateAPK/"; //apk保存到SD卡的路径
    private static final String saveFileName = savePath + "奇育.apk"; //完整路径名

    private ProgressBar mProgress; //下载进度条控件
    private TextView tv_progress;//下载进度百分比
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progress; //下载进度
    private boolean cancelFlag = false; //取消下载标志位
    private String updateDescription = "更新描述:"; //更新内容描述信息
    private boolean forceUpdate = false; //是否强制更新

    private AlertDialog alertDialog1, alertDialog2; //表示提示对话框、进度条对话框

    /**
     * 构造函数
     */
    public UpdateAPK(Context context) {
        this.mContext = context;
    }

    /**
     * 显示更新对话框
     */
    public void showNoticeDialog(String serverVersion, String clientVersion) {
        //如果版本最新，则不需要更新
        if (!isNeedUpdate(serverVersion, clientVersion)) {
            Toast.makeText(mContext, "当前版本已是最新,版本号：" + clientVersion, Toast.LENGTH_SHORT).show();
            return;
        }
        apkUrl = URLManager.APK_FILE_URL + "qiyu_" + serverVersion + ".apk";
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("发现新版本 ：" + serverVersion);
        dialog.setMessage(updateDescription + getAppVersionName(mContext));
        dialog.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                showDownloadDialog();
            }
        });
        //是否强制更新
        if (!forceUpdate) {
            dialog.setNegativeButton("待会更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
        }
        alertDialog1 = dialog.create();
        alertDialog1.setCancelable(false);
        alertDialog1.show();
    }

    /**
     * 服务器获取到的版本号与本地版本号对比确定是否需要更新
     *
     * @param serverVersion 服务的版本号
     * @param clientVersion 本地版本号
     * @return 布尔
     */
    private Boolean isNeedUpdate(String serverVersion, String clientVersion) {
        boolean isNeed = false;
        try {
            if (serverVersion == null || serverVersion.equals("")) {
                Toast.makeText(mContext, "服务器获取版本号错误，请重试！", Toast.LENGTH_SHORT).show();
            } else {
                String[] service = serverVersion.split("\\.");
                String[] current = clientVersion.split("\\.");
                for (int i = 0; i < current.length; i++) {
                    int s = Integer.parseInt(service[i]);
                    int c = Integer.parseInt(current[i]);
                    isNeed = c < s;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "比较版本号出错", Toast.LENGTH_SHORT).show();
        }
        return isNeed;
    }

    /**
     * 显示进度条对话框
     */
    private void showDownloadDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("正在更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_apk_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_apk_progress);
        tv_progress = (TextView) v.findViewById(R.id.tv_apk_progress);
        dialog.setView(v);
        //如果是强制更新，则不显示取消按钮
        if (!forceUpdate) {
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                    cancelFlag = false;
                }
            });
        }
        alertDialog2 = dialog.create();
        alertDialog2.setCancelable(false);
        alertDialog2.show();

        //下载apk
        downloadAPK();
    }

    /**
     * 下载apk的线程
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String apkFile = saveFileName;
                    File ApkFile = new File(apkFile);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWNLOADING);
                        if (numread <= 0) {
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWNLOADED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!cancelFlag); //点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 更新UI的handler
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOADING:
                    mProgress.setProgress(progress);
                    tv_progress.setText(progress + "%");//显示下载进度百分比
                    break;
                case DOWNLOADED:
                    if (alertDialog2 != null)
                        alertDialog2.dismiss();
                    installAPK();
                    break;
                case DOWNLOAD_FAILED:
                    Toast.makeText(mContext, "网络断开，请稍候再试", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 下载完成后自动安装apk
     */
    private void installAPK() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    /**
     * 获取当前app version code
     */
    public long getAppVersionCode(Context context) {
        long appVersionCode = 0;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CommonTools", e.getMessage());
        }
        return appVersionCode;
    }

    /**
     * 获取当前app version name
     */
    public String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CommonTools", e.getMessage());
        }
        return appVersionName;
    }
}
