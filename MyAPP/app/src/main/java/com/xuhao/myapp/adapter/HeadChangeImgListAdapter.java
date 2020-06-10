package com.xuhao.myapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.xuhao.myapp.R;
import com.xuhao.myapp.UserInfoChangeActivity;
import com.xuhao.myapp.View.CircleImageView;
import com.xuhao.myapp.internet.URLManager;
import com.xuhao.myapp.utills.ImageUtiles;

import java.util.List;

public class HeadChangeImgListAdapter extends BaseRecyclerViewAdapter<String> {
    private UserInfoChangeActivity mcontext;

    public HeadChangeImgListAdapter(UserInfoChangeActivity context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
        mcontext = context;
    }

    @Override
    protected void bindData(final BaseViewHolder holder, final String data, int position) {
        final CircleImageView circleImageView = holder.getView(R.id.head_change_popwindow_head_img);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.popWindowSubmit(data);
            }
        });
        Log.i("头像路径", URLManager.HEAD_IMAGE_ADDRESS + data);
        ImageUtiles.loadInternetImg(URLManager.HEAD_IMAGE_ADDRESS + data, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                circleImageView.setImageBitmap(bitmap);
            }
        }, null);
    }
}
