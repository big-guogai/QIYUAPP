package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.BossIndentMainInfoActivity;
import com.xuhao.myapp.R;

import java.util.List;

public class BossIndentRecycleViewAdapter extends BaseRecyclerViewAdapter<IndentInfoBean> {

    private Context mContext;

    public BossIndentRecycleViewAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final IndentInfoBean data, int position) {
        TextView textViewUserName = holder.getView(R.id.indent_boss_user_name);
        TextView textViewIndentTime = holder.getView(R.id.indent_boss_indent_time);
        TextView textViewUserId = holder.getView(R.id.indent_boss_user_id);
        TextView textViewCourseNameQuantity = holder.getView(R.id.indent_boss_course_name_quality);
        TextView textViewIndentPrice = holder.getView(R.id.indent_boss_indent_price);
        TextView textViewIndentType = holder.getView(R.id.indent_boss_indent_type);
        Button btnHandle = holder.getView(R.id.indent_boss_handle_indent);
        TextView textViewIndentInfo = holder.getView(R.id.indent_boss_indent_information);

        textViewUserName.setText(data.getUsername());
        //日期格式从数据库取出格式出错，暂时使用字符串截取显示
        String strTime = String.valueOf(data.getIndenttime());
        textViewIndentTime.setText(strTime.substring(0, strTime.indexOf(".")));
        textViewUserId.setText(data.getBuyuserid() + "");
        textViewCourseNameQuantity.setText(data.getCoursename() + "×" + data.getCoursequantity());
        textViewIndentPrice.setText(data.getIndentprice() + "");
        textViewIndentType.setText(data.getIndenttype());
        if (data.getIndenttype().equals("待处理")){
            btnHandle.setVisibility(View.VISIBLE);
        }else {
            btnHandle.setVisibility(View.INVISIBLE);
        }
        //处理按钮点击事件
        btnHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BossIndentMainInfoActivity.class);
                intent.putExtra("indentId",data.getIndentid());
                mContext.startActivity(intent);
            }
        });
        //订单详情点击事件
        textViewIndentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BossIndentMainInfoActivity.class);
                intent.putExtra("indentId",data.getIndentid());
                mContext.startActivity(intent);
            }
        });
    }
}
