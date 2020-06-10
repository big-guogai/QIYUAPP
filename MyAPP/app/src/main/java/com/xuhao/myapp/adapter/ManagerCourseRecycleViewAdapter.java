package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xuhao.myapp.Bean.BossCourseInfoBean;
import com.xuhao.myapp.ManagerCourseInfoActivity;
import com.xuhao.myapp.R;

import java.util.List;

public class ManagerCourseRecycleViewAdapter extends BaseRecyclerViewAdapter<BossCourseInfoBean> {
    private Context mContext;

    public ManagerCourseRecycleViewAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final BossCourseInfoBean data, int position) {
        TextView textViewCourseId = holder.getView(R.id.manager_course_item_course_id);
        TextView textViewCourseBossId = holder.getView(R.id.manager_course_item_course_boss_id);
        TextView textViewCourseName = holder.getView(R.id.manager_course_item_course_name);
        TextView textViewCoursePriceAndType = holder.getView(R.id.manager_course_item_course_price_and_type);
        TextView textViewHandleInfo = holder.getView(R.id.manager_course_item_handle_info);
        textViewCourseId.setText(data.getCourseId() + "");
        textViewCourseBossId.setText("店铺ID：" + data.getBossId() + "");
        textViewCourseName.setText(data.getCourseName());
        textViewCoursePriceAndType.setText(data.getCoursePrice() + "(" + data.getCoursePriceType() + ")");
        textViewHandleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ManagerCourseInfoActivity.class);
                intent.putExtra("courseId", data.getCourseId());
                mContext.startActivity(intent);
            }
        });
    }
}
