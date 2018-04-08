package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.userBuyedData;
import cn.lrn517.techcomplatform.bean.userapplyfordata;

/**
 * Created by lirun on 2018/4/8.
 */

public class MyBuyedViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List list;
    Context context;
    View view;
    private LayoutInflater layoutInflater;

    public MyBuyedViewAdapter(Context context, List<Map<String,Object>> list ){
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_buyed_view,parent,false);
        return new MyBuyedViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final userBuyedData data = (userBuyedData) list.get(position);
        if( null == data )
            return;
        MyBuyedViewAdapter.ViewHolder viewHolder = (MyBuyedViewAdapter.ViewHolder) holder;
        if( null != data.getTdtitle()){
           viewHolder.tpzd.setVisibility(View.GONE);
           viewHolder.rtime_td.setText(data.getRtime());
           viewHolder.price_td.setText(data.getPrice());
           viewHolder.tdtitle.setText(data.getTdtitle());
        }else{
            viewHolder.td.setVisibility(View.GONE);
            viewHolder.rtime_tpzd.setText(data.getRtime());
            viewHolder.tpzdtitle.setText(data.getTpzdtitle());
            viewHolder.price_tpzd.setText(data.getPrice());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout td,tpzd;
        TextView price_td,price_tpzd,rtime_td,rtime_tpzd,tpzdtitle,tdtitle;

        ViewHolder(View itemView){
            super(itemView);
            td = itemView.findViewById(R.id.my_buyed_td);
            tpzd = itemView.findViewById(R.id.my_buyed_tpzd);
            price_td = itemView.findViewById(R.id.my_buyed_td_price);
            price_tpzd = itemView.findViewById(R.id.my_buyed_tpzd_price);
            rtime_td = itemView.findViewById(R.id.my_buyed_td_rtime);
            rtime_tpzd = itemView.findViewById(R.id.my_buyed_tpzd_rtime);
            tpzdtitle = itemView.findViewById(R.id.my_buyed_tpzd_tpzdtitle);
            tdtitle = itemView.findViewById(R.id.my_buyed_td_tdtitle);
        }
    }
}
