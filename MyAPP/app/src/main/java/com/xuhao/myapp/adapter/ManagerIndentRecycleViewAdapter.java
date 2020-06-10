package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xuhao.myapp.Bean.IndentInfoBean;
import com.xuhao.myapp.ManagerIndentInfoActivity;
import com.xuhao.myapp.R;

import java.util.List;

public class ManagerIndentRecycleViewAdapter extends BaseRecyclerViewAdapter<IndentInfoBean> {
    private Context mContext;
    public ManagerIndentRecycleViewAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final IndentInfoBean data, int position) {
        TextView textViewIndentId = holder.getView(R.id.manager_indent_item_indent_id);
        TextView textViewIndentTime = holder.getView(R.id.manager_indent_item_indent_time);
        TextView textViewBuyUserId = holder.getView(R.id.manager_indent_item_buy_user_id);
        TextView textViewCourseId = holder.getView(R.id.manager_indent_item_course_id);
        TextView textViewIndentPrice = holder.getView(R.id.manager_indent_item_indent_price);
        TextView textViewIndentType = holder.getView(R.id.manager_indent_item_indent_type);
        TextView textViewHandleInfo = holder.getView(R.id.manager_indent_item_handle_info);
        textViewIndentId.setText(data.getIndentid() + "");
        //日期格式从数据库取出格式出错，暂时使用字符串截取显示
        String strTime = String.valueOf(data.getIndenttime());
        textViewIndentTime.setText(strTime.substring(0, strTime.indexOf(".")));
        textViewBuyUserId.setText("客户ID："+data.getBuyuserid() + "");
        textViewCourseId.setText("课程ID："+data.getCourseid() + "");
        textViewIndentPrice.setText(data.getIndentprice() + "");
        textViewIndentType.setText(data.getIndenttype());
        textViewHandleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ManagerIndentInfoActivity.class);
                intent.putExtra("indentId",data.getIndentid());
                mContext.startActivity(intent);
            }
        });
    }
}
