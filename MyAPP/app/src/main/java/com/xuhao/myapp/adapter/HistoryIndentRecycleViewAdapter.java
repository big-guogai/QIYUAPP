package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.CreateIndentActivity;
import com.xuhao.myapp.QiYuApplication;
import com.xuhao.myapp.R;
import com.xuhao.myapp.StartActivity;
import com.xuhao.myapp.fragment.indentkind.BaseLazyIndentFragment;
import com.xuhao.myapp.internet.URLManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryIndentRecycleViewAdapter extends BaseRecyclerViewAdapter<IndentInfoBean> {

    private Context mContext;

    public HistoryIndentRecycleViewAdapter(Context context, List<IndentInfoBean> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(final BaseViewHolder holder, final IndentInfoBean data, int position) {
        TextView textViewIndentTime = holder.getView(R.id.indent_history_indent_time);
        TextView textViewBossName = holder.getView(R.id.indent_history_boss_name);
        TextView textViewCourseNmaeQuality = holder.getView(R.id.indent_history_course_name_quality);
        TextView textViewPrice = holder.getView(R.id.indent_history_indent_price);
        TextView textViewType = holder.getView(R.id.indent_history_indent_type);
        Button buttonDelete = holder.getView(R.id.indent_history_delete_indent);
        Button buttonGoPay = holder.getView(R.id.indent_history_go_pay);
        if (data.getIndenttype().equals("待支付")) {
            buttonGoPay.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);
        } else {
            buttonDelete.setVisibility(View.INVISIBLE);
            buttonGoPay.setVisibility(View.INVISIBLE);
        }
        TextView textViewIndentInfo = holder.getView(R.id.indent_history_indent_information);
        //日期格式从数据库取出格式出错，暂时使用字符串截取显示
        String strTime = String.valueOf(data.getIndenttime());
        textViewIndentTime.setText(strTime.substring(0, strTime.indexOf(".")));
        textViewBossName.setText(data.getBossname());
        textViewCourseNmaeQuality.setText(data.getCoursename() + "×" + data.getCoursequantity());
        textViewPrice.setText(data.getIndentprice() + "");
        textViewType.setText(data.getIndenttype());
        //取消订单按钮点击事件
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("提示");
                alertDialog.setIcon(R.drawable.ic_alert);
                alertDialog.setMessage("请确认是否取消该订单！");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "我还要买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "我要放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteIndent(data.getIndentid());
                    }
                });
                alertDialog.show();
            }
        });
        //去支付按钮点击事件
        buttonGoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CreateIndentActivity.class);
                intent.putExtra("indentId", data.getIndentid());
                mContext.startActivity(intent);
            }
        });
        //订单详情点击事件
        textViewIndentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CreateIndentActivity.class);
                intent.putExtra("indentId", data.getIndentid());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 取消订单方法
     *
     * @param indentId
     */
    private void deleteIndent(final int indentId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.CANCLE_NO_PAY_INDENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Boolean isCancle = new Gson().fromJson(s,Boolean.class);
                        if (isCancle){
                            Toast.makeText(mContext,"订单取消成功",Toast.LENGTH_SHORT).show();
                            //发送取消订单刷新广播
                            Intent intent = new Intent("cancle_no_pay_indent_refresh");
                            mContext.sendBroadcast(intent);
                        }else {
                            Toast.makeText(mContext,"发生意料之外的错误，请重试！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext,"网络错误，请重试！",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("indentId", indentId + "");
                return map;
            }
        };
        QiYuApplication.queue.add(stringRequest);
    }
}
