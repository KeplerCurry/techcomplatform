package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.TechDetailActivity;
import cn.lrn517.techcomplatform.bean.firstAnswerData;
import cn.lrn517.techcomplatform.bean.homeData;

/**
 * Created by lirun on 2018/3/23.
 */

public class AskAnswerDataViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List mDataList;


    public AskAnswerDataViewAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.mDataList = mDataList;
    }

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 通过判断显示类型，来创建不同的View
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.ask_answer_data_view, parent, false);
            return new AskAnswerDataViewAdapter.RecyclerViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new AskAnswerDataViewAdapter.FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AskAnswerDataViewAdapter.RecyclerViewHolder) {
            final firstAnswerData data = (firstAnswerData) mDataList.get(position);
            AskAnswerDataViewAdapter.RecyclerViewHolder recyclerViewHolder = (AskAnswerDataViewAdapter.RecyclerViewHolder) holder;
            recyclerViewHolder.ualiase.setText(data.getUaliase().toString());
            recyclerViewHolder.ctime.setText(data.getCtime().toString());
            recyclerViewHolder.content.setText(data.getContent().toString());
            recyclerViewHolder.ulevel.setText(data.getUlevel().toString());
            recyclerViewHolder.utype.setText(data.getUtype().toString());

            Log.i("testxxxx","--------------"+position);


        } else if (holder instanceof AskAnswerDataViewAdapter.FootViewHolder) {
            AskAnswerDataViewAdapter.FootViewHolder footViewHolder = (AskAnswerDataViewAdapter.FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView ualiase,ulevel,utype,content,ctime;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ualiase =  itemView.findViewById(R.id.ask_answer_ualiase);
            ulevel =  itemView.findViewById(R.id.ask_answer_ulevel);
            utype = itemView.findViewById(R.id.ask_answer_utype);
            content = itemView.findViewById(R.id.ask_answer_content);
            ctime = itemView.findViewById(R.id.ask_answer_ctime);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
