package com.xuhao.myapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    /**
     * 视图容器。
     */
    private SparseArray<View> mViews;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 获取子视图
     *
     * @param id  条目内部的控件id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {
        View v = mViews.get(id, null);    // 当前缓存获取
        if (v == null) {    // 获取失败。
            // 通过id查找。
            v = itemView.findViewById(id);
            // 加入缓存
            mViews.put(id, v);
        }
        return (T) v;
    }

    /**
     * 获取当前条目视图。
     *
     * @return
     */
    public View getRootView() {
        return itemView;
    }

}
