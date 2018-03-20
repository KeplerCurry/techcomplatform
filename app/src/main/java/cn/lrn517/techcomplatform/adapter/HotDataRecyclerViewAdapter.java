package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.homeData;

/**
 * Created by lirun on 2018/3/20.
 */

public class HotDataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List mDataList;
    int headeraccount = 1;
    int footaccount = 1;
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BUTTON = 2;

    public HotDataRecyclerViewAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == ITEM_TYPE_HEADER){
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.header_add_data , parent , false));
        }
        if( viewType == ITEM_TYPE_BUTTON){
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.header_add_data , parent , false));
        }
        if( viewType == ITEM_TYPE_CONTENT){
            View view = LayoutInflater.from(context).inflate(R.layout.hot_data_view , parent , false);
            return new HotDataRecyclerViewAdapter.ViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof HeaderViewHolder){

        }else if( holder instanceof FootViewHolder){

        }else if( holder instanceof ViewHolder){
            final homeData data = (homeData) mDataList.get(position);
            HotDataRecyclerViewAdapter.ViewHolder viewHolder = (HotDataRecyclerViewAdapter.ViewHolder) holder;
            viewHolder.tdfirsttime.setText(data.getTdfirsttime().toString());
            viewHolder.tdtitle.setText(data.getTdtitle().toString());
            viewHolder.isfree.setText(data.getIsfree().toString());
            viewHolder.state.setText(data.getState().toString());
            viewHolder.tname.setText(data.getTname().toString());
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if( headeraccount != 0 && position < headeraccount ){
            return ITEM_TYPE_HEADER;
        }else if( footaccount != 0 && position >= (headeraccount + mDataList.size()) ){
            return ITEM_TYPE_BUTTON;
        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View viewItem;
        TextView tname,state,isfree,tdtitle,tdfirsttime;
        public ViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            tname = itemView.findViewById(R.id.hot_data_view_tname);
            state = itemView.findViewById(R.id.hot_data_view_state);
            isfree = itemView.findViewById(R.id.hot_data_view_isfree);
            tdtitle = itemView.findViewById(R.id.hot_data_view_tdtitle);
            tdfirsttime = itemView.findViewById(R.id.hot_data_view_tdfirsttime);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
