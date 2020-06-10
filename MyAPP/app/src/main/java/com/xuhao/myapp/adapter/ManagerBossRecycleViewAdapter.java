package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xuhao.myapp.Bean.BossInfoBean;
import com.xuhao.myapp.ManagerBossInfoActivity;
import com.xuhao.myapp.R;

import java.util.List;

public class ManagerBossRecycleViewAdapter extends BaseRecyclerViewAdapter<BossInfoBean> {
    private Context mContext;

    public ManagerBossRecycleViewAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final BossInfoBean data, int position) {
        TextView textViewBossId = holder.getView(R.id.manager_boss_item_boss_id);
        TextView textViewBossUserId = holder.getView(R.id.manager_boss_item_boss_user_id);
        TextView textViewBossName = holder.getView(R.id.manager_boss_item_boss_name);
        TextView textViewBossPosition = holder.getView(R.id.manager_boss_item_boss_position);
        TextView textViewHandleInfo = holder.getView(R.id.manager_boss_item_handle_info);
        textViewBossId.setText(data.getBossId() + "");
        textViewBossUserId.setText("老板ID："+data.getUserId() + "");
        textViewBossName.setText(data.getBossName());
        textViewBossPosition.setText(data.getBossPosition());
        textViewHandleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ManagerBossInfoActivity.class);
                intent.putExtra("bossId", data.getBossId());
                mContext.startActivity(intent);
            }
        });
    }
}
