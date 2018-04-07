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
import cn.lrn517.techcomplatform.bean.commonAttentionData;

/**
 * Created by lirun on 2018/4/7.
 */

public class MyCollectionViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List list;
    private LayoutInflater layoutInflater;

    public MyCollectionViewAdapter(Context context,List<Map<String,Object>> mDataList){
        this.context = context;
        this.list = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view, parent, false);
        return new MyCollectionViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final commonAttentionData data = (commonAttentionData) list.get(position);
        if( null == data ){
            return;
        }
        MyCollectionViewAdapter.ViewHolder viewHolder = (MyCollectionViewAdapter.ViewHolder) holder;
        if( null != data.getTdtitle()){
            viewHolder.name.setText(data.getTdtitle().toString());
        }else{
            viewHolder.name.setText(data.getTpzdtitle().toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
        }
    }
}
