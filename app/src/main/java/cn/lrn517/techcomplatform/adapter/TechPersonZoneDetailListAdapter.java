package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.BrowseTechPersonZoneActivity;
import cn.lrn517.techcomplatform.activity.BrowseTechPersonZoneDetailActivity;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaillist;
import cn.lrn517.techcomplatform.bean.techpersonzonelist;

/**
 * Created by lirun on 2018/4/2.
 */

public class TechPersonZoneDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List list;
    private LayoutInflater layoutInflater;

    public TechPersonZoneDetailListAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.list = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.tech_person_zone_detail_listview, parent, false);
        return new TechPersonZoneDetailListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final techpersonzonedetaillist data = (techpersonzonedetaillist) list.get(position);
        if( null == data )
            return;
        final TechPersonZoneDetailListAdapter.ViewHolder viewHolder = (TechPersonZoneDetailListAdapter.ViewHolder) holder;
        viewHolder.tpzdtitle.setText(data.getTpzdtitle().toString());
        viewHolder.tpzdfirsttime.setText(data.getTpzdfirsttime().toString());
        if( "1".equals(data.getIsfree())){
            viewHolder.price.setVisibility(View.GONE);
            viewHolder.isfree.setText("免费");
        }else{
            viewHolder.isfree.setText("收费");
            viewHolder.price.setText("$"+data.getPrice().toString());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , BrowseTechPersonZoneDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tpzdid" , data.getTpzdid().toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tpzdtitle,isfree,price,tpzdfirsttime;


        public ViewHolder(View itemView) {
            super(itemView);
            tpzdtitle = itemView.findViewById(R.id.browse_tech_person_zone_detail_list_tpzdtitle);
            isfree = itemView.findViewById(R.id.browse_tech_person_zone_detail_list_isfree);
            price = itemView.findViewById(R.id.browse_tech_person_zone_detail_list_price);
            tpzdfirsttime = itemView.findViewById(R.id.browse_tech_person_zone_detail_list_tpzdfirsttime);
        }
    }
}
