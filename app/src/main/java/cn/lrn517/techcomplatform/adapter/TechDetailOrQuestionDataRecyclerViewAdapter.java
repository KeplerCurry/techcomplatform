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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.AskDetailActivity;
import cn.lrn517.techcomplatform.activity.TechDetailActivity;
import cn.lrn517.techcomplatform.bean.homeData;

/**
 * Created by lirun on 2018/3/20.
 */

public class TechDetailOrQuestionDataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List mDataList;


    public TechDetailOrQuestionDataRecyclerViewAdapter(Context context , List<Map<String,Object>> mDataList){
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
                    .inflate(R.layout.hot_data_view, parent, false);
            return new RecyclerViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            final homeData data = (homeData) mDataList.get(position);
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            if( "0".equals(data.getState().toString()) ){
                recyclerViewHolder.state.setText("技术贴");
                recyclerViewHolder.attention.setVisibility(View.GONE);
                recyclerViewHolder.answer.setVisibility(View.GONE);
                if( "0".equals(data.getIsfree().toString()) ){
                    recyclerViewHolder.isfree.setText("需付费");
                }else{
                    recyclerViewHolder.isfree.setText("免费");
                }
                recyclerViewHolder.collect.setVisibility(View.VISIBLE);
                recyclerViewHolder.like.setVisibility(View.VISIBLE);
                recyclerViewHolder.collect.setText(data.getCollect().toString()+" 收藏");
                recyclerViewHolder.like.setText(data.getLike().toString()+" 点赞 · ");
            }else{
                recyclerViewHolder.state.setText("问题");
                recyclerViewHolder.collect.setVisibility(View.GONE);
                recyclerViewHolder.like.setVisibility(View.GONE);
                if( "0".equals(data.getIsfree().toString()) ){
                    recyclerViewHolder.isfree.setText("有赏金");
                }else{
                    recyclerViewHolder.isfree.setText("无赏金");
                }
                recyclerViewHolder.answer.setVisibility(View.VISIBLE);
                recyclerViewHolder.attention.setVisibility(View.VISIBLE);
                recyclerViewHolder.answer.setText(data.getAnswer().toString()+" 回答");
                recyclerViewHolder.attention.setText(data.getAttention().toString()+" 关注 · ");
            }
            recyclerViewHolder.tdtitle.setText(data.getTdtitle().toString());
            recyclerViewHolder.tdcontent.setText(data.getTdcontent().toString());
            Log.i("testxxxx","--------------"+position);

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String state = data.getState().toString();
                    if( "1".equals(state) ){
                        Intent intent = new Intent(context , AskDetailActivity.class);
                        String tdid_s = data.getTdid().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("tdid" , tdid_s);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context , TechDetailActivity.class);
                        String tdid_s = data.getTdid().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("tdid" , tdid_s);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                }
            });

        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
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

        TextView state,isfree,tdtitle;
        TextView tdcontent,attention,answer,like,collect;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            state =  itemView.findViewById(R.id.hot_data_view_state);
            isfree = itemView.findViewById(R.id.hot_data_view_isfree);
            tdtitle = itemView.findViewById(R.id.hot_data_view_tdtitle);
            tdcontent = itemView.findViewById(R.id.hot_data_view_tdcontent);
            attention = itemView.findViewById(R.id.hot_data_view_attention);
            answer = itemView.findViewById(R.id.hot_data_view_answer);
            like = itemView.findViewById(R.id.hot_data_view_like);
            collect = itemView.findViewById(R.id.hot_data_view_collect);
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
