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

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.SeeAnswerActivity;
import cn.lrn517.techcomplatform.activity.TechDetailActivity;
import cn.lrn517.techcomplatform.bean.firstAnswerData;
import cn.lrn517.techcomplatform.bean.homeData;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lirun on 2018/3/23.
 */

public class AskAnswerDataViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List mDataList;
    String tdtitle;


    public AskAnswerDataViewAdapter(Context context , List<Map<String,Object>> mDataList , String tdtitle){
        this.context = context;
        this.mDataList = mDataList;
        this.tdtitle = tdtitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ask_answer_data_view, parent, false);
        return new AskAnswerDataViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final firstAnswerData data = (firstAnswerData) mDataList.get(position);
        AskAnswerDataViewAdapter.RecyclerViewHolder recyclerViewHolder = (AskAnswerDataViewAdapter.RecyclerViewHolder) holder;
        Glide.with(context)
                .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                .dontAnimate()
                .crossFade()
                .into(recyclerViewHolder.uphoto);
        recyclerViewHolder.ualiase.setText(data.getUaliase().toString());
        recyclerViewHolder.ctime.setText(data.getCtime().toString());
        recyclerViewHolder.content.setText(data.getContent().toString());
        recyclerViewHolder.ulevel.setText("Lv."+data.getUlevel().toString());
        if( "1".equals(data.getUtype().toString())){
            recyclerViewHolder.utype.setText("普通用户");
        }else{
            recyclerViewHolder.utype.setText("高级用户");
        }

        Log.i("testxxxx","--------------"+position);

        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , SeeAnswerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cid" , data.getCid().toString());
                bundle.putString("tdtitle" , tdtitle);
                Log.i("testxxxx","--------------"+tdtitle);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView ualiase,ulevel,utype,content,ctime;
        CircleImageView uphoto;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ualiase =  itemView.findViewById(R.id.ask_answer_ualiase);
            ulevel =  itemView.findViewById(R.id.ask_answer_ulevel);
            utype = itemView.findViewById(R.id.ask_answer_utype);
            content = itemView.findViewById(R.id.ask_answer_content);
            ctime = itemView.findViewById(R.id.ask_answer_ctime);
            uphoto = itemView.findViewById(R.id.ask_answer_uphoto);
        }
    }
}
