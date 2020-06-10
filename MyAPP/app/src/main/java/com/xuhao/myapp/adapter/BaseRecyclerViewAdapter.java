package com.xuhao.myapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private LayoutInflater inflater;
    private List<T> datas;
    private int layoutId;

    public BaseRecyclerViewAdapter(Context context, List<T> datas, int layoutId) {
        this.datas = datas;
        this.layoutId = layoutId;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(inflater.inflate(layoutId, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        bindData(baseViewHolder, datas.get(i), i);
    }

    protected abstract void bindData(BaseViewHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

}
