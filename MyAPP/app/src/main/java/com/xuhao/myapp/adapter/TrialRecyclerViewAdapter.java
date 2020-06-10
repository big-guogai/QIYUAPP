package com.xuhao.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xuhao.myapp.Bean.CheckBossInfoBean;
import com.xuhao.myapp.R;
import com.xuhao.myapp.TrialBossInfoActivity;

import java.util.List;

public class TrialRecyclerViewAdapter extends BaseRecyclerViewAdapter<CheckBossInfoBean> {

    private Context mcontext;

    public TrialRecyclerViewAdapter(Context context, List<CheckBossInfoBean> datas, int layoutId) {
        super(context, datas, layoutId);
        mcontext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(BaseViewHolder holder, final CheckBossInfoBean data, int position) {
        TextView textViewUserId = holder.getView(R.id.trial_list_user_id);
        TextView textViewBossName = holder.getView(R.id.trial_list_boss_name);
        TextView textViewBossIntroduction = holder.getView(R.id.trial_list_boss_introduction);

        //查看审核点击事件
        TextView textViewTrial = holder.getView(R.id.trial_list_trial);
        textViewTrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, TrialBossInfoActivity.class);
                intent.putExtra("userId", data.getUserId());
                intent.putExtra("bossName", data.getBossName());
                intent.putExtra("bossDocument", data.getBossDocument());
                intent.putExtra("bossInformation", data.getBossInformation());
                intent.putExtra("bossTelephone", data.getBossTelePhone());
                intent.putExtra("bossPosition", data.getBossPosition());
                intent.putExtra("bossPhotoOne", data.getBossPhotoOne());
                intent.putExtra("bossPhotoTwo", data.getBossPhotoTwo());
                intent.putExtra("bossPhotoThree", data.getBossPhotoThree());
                intent.putExtra("bossPhotoFour", data.getBossPhotoFour());
                intent.putExtra("bossPhotoFive", data.getBossPhotoFive());
                intent.putExtra("bossReceiptType", data.getBossReceiptType());
                intent.putExtra("bossReceiptId", data.getBossReceiptId());
                mcontext.startActivity(intent);
            }
        });
        textViewUserId.setText(data.getUserId() + "");
        textViewBossName.setText(data.getBossName());
        textViewBossIntroduction.setText("\u3000\u3000" + data.getBossInformation());
    }
}
