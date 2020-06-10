package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xuhao.myapp.Bean.UserInfoBean;
import com.xuhao.myapp.ManagerUserInfoActivity;
import com.xuhao.myapp.R;

import java.util.List;

public class ManagerUserRecycleViewAdapter extends BaseRecyclerViewAdapter<UserInfoBean> {

    private Context mContext;

    public ManagerUserRecycleViewAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final UserInfoBean data, int position) {
        TextView textViewUserId = holder.getView(R.id.manager_user_item_user_id);
        TextView textViewUserName = holder.getView(R.id.manager_user_item_user_name);
        TextView textViewUserType = holder.getView(R.id.manager_user_item_user_type);
        TextView textViewHandleInfo = holder.getView(R.id.manager_user_item_handle_info);
        textViewUserId.setText(data.getUserid() + "");
        textViewUserName.setText(data.getUsername());
        if (data.getUsertypeid() == 103) {
            textViewUserType.setText("管理员");
        }else if (data.getUsertypeid() == 102){
            textViewUserType.setText("店铺拥有者");
        }else {
            textViewUserType.setText("普通用户");
        }
        textViewHandleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ManagerUserInfoActivity.class);
                intent.putExtra("userId",data.getUserid());
                mContext.startActivity(intent);
            }
        });
    }
}
