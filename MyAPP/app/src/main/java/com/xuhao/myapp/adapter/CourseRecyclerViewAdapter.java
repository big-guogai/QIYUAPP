package com.xuhao.myapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.xuhao.myapp.CourseMainInfoActivity;
import com.xuhao.myapp.utills.ImageUtiles;
import com.xuhao.myapp.R;
import com.xuhao.myapp.Bean.CourseListBean;
import com.xuhao.myapp.internet.URLManager;

import java.util.List;


public class CourseRecyclerViewAdapter extends BaseRecyclerViewAdapter<CourseListBean> {

    private Context mContext;

    public CourseRecyclerViewAdapter(Context context, List<CourseListBean> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final CourseListBean data, int position) {
        final ImageView imageView = holder.getView(R.id.course_kind_list_item_img);
        TextView title = holder.getView(R.id.course_kind_list_item_title);
        //Items条目点击事件
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseMainInfoActivity.class);
                intent.putExtra("courseid", data.getCourseId());
                mContext.startActivity(intent);
            }
        });
        //设置展示图片和标题
        ImageUtiles.loadInternetImg(URLManager.IMAGE_ADDRESS + data.getImgUrl(), new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, null);
        title.setText(data.getTitle());
    }
}
