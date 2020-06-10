package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xuhao.myapp.Bean.BossCourseInfoBean;
import com.xuhao.myapp.BossCourseChangeActivity;
import com.xuhao.myapp.R;

import java.util.List;

public class BossCourseRecyleViewAdapter extends BaseRecyclerViewAdapter<BossCourseInfoBean> {
    private Context mContext;

    public BossCourseRecyleViewAdapter(Context context, List<BossCourseInfoBean> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final BossCourseInfoBean data, int position) {
        TextView textViewCourseName = holder.getView(R.id.boss_course_list_course_name);
        TextView textViewCourseId = holder.getView(R.id.boss_course_list_course_id);
        TextView textViewCourseInformation = holder.getView(R.id.boss_course_list_course_information);
        TextView textViewCourseType = holder.getView(R.id.boss_course_list_course_type);
        TextView textViewCoursePrice = holder.getView(R.id.boss_course_list_course_price);
        TextView textViewCoursePriceType = holder.getView(R.id.boss_course_list_course_priceType);
        //更改课程信息点击事件
        TextView textViewCourseInfoChange = holder.getView(R.id.boss_course_list_course_info_change);
        textViewCourseInfoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BossCourseChangeActivity.class);
                intent.putExtra("courseId", data.getCourseId());
                intent.putExtra("courseName", data.getCourseName());
                intent.putExtra("courseInformation", data.getCourseInformation());
                intent.putExtra("courseTypeId1", data.getCourseTypeId1());
                intent.putExtra("courseTypeId2", data.getCourseTypeId2());
                intent.putExtra("courseTypeId3", data.getCourseTypeId3());
                intent.putExtra("coursePrice", data.getCoursePrice());
                intent.putExtra("coursePriceType", data.getCoursePriceType());
                mContext.startActivity(intent);
            }
        });
        textViewCourseName.setText(data.getCourseName());
        textViewCourseId.setText(data.getCourseId() + "");
        textViewCourseInformation.setText("\u3000\u3000" + data.getCourseInformation());
        String courseType;
        if (data.getCourseTypeId2() == null) {
            courseType = data.getCourseTypeId1();
        } else if (data.getCourseTypeId3() == null) {
            courseType = data.getCourseTypeId1() + "、" + data.getCourseTypeId2();
        } else {
            courseType = data.getCourseTypeId1() + "、" + data.getCourseTypeId2() + "、" + data.getCourseTypeId3();
        }
        textViewCourseType.setText("课程类型：" + courseType);
        textViewCoursePrice.setText((Double) data.getCoursePrice() + "");
        String coursePriceType = "(" + data.getCoursePriceType() + ")";
        textViewCoursePriceType.setText(coursePriceType);
    }
}
