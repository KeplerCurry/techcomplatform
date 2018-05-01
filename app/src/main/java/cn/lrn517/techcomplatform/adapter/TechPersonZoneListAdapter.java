package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.BrowseTechPersonZoneActivity;
import cn.lrn517.techcomplatform.activity.MainActivity;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techpersonzonelist;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lirun on 2018/4/2.
 */

public class TechPersonZoneListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List list;
    private LayoutInflater layoutInflater;

    public TechPersonZoneListAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.list = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.special_classify_list_cardview, parent, false);
        return new TechPersonZoneListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final techpersonzonelist data = (techpersonzonelist) list.get(position);
        if( null == data )
            return;
        final TechPersonZoneListAdapter.ViewHolder viewHolder = (TechPersonZoneListAdapter.ViewHolder) holder;
        viewHolder.tname.setText(data.getTname());
        viewHolder.tpzname.setText(data.getTpzname());
        viewHolder.ualiase.setText(data.getUaliase());
        Glide.with(context)
                .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                .dontAnimate()
                .crossFade()
                .into(viewHolder.uphoto);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , BrowseTechPersonZoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tpzid" , data.getTpzid().toString());
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

        TextView tpzname,tname,ualiase;
        CircleImageView uphoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.special_classify_list_ualiase);
            tname = itemView.findViewById(R.id.special_classify_list_tname);
            tpzname = itemView.findViewById(R.id.special_classify_list_tpzname);
            uphoto = itemView.findViewById(R.id.special_classify_list_uphoto);
        }
    }
}
