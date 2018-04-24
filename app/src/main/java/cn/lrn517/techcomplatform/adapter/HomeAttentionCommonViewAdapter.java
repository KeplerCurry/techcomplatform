package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.MainActivity;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.bean.homeattentiondata;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lirun on 2018/4/24.
 */

public class HomeAttentionCommonViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;

    public HomeAttentionCommonViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 11 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.home_attention_user_cardview,parent,false);
            return new HomeAttentionCommonViewAdapter.ViewHolder_11(view);
        }else if( 12 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.home_attention_question_cardview,parent,false);
            return new HomeAttentionCommonViewAdapter.ViewHolder_12(view);
        }else{
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.home_attention_tpz_cardview,parent,false);
            return new HomeAttentionCommonViewAdapter.ViewHolder_13(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof HomeAttentionCommonViewAdapter.ViewHolder_11){
            final homeattentiondata.UserBean data = (homeattentiondata.UserBean) list.get(position);
            HomeAttentionCommonViewAdapter.ViewHolder_11 viewHolder_11 = (HomeAttentionCommonViewAdapter.ViewHolder_11) holder;
            viewHolder_11.ualiase.setText(data.getUaliase().toString());
            viewHolder_11.ulevel.setText("Lv."+data.getUlevel().toString());
            if( "0".equals(data.getUtype().toString())){
                viewHolder_11.utype.setText("普通用户");
            }else{
                viewHolder_11.utype.setText("高级用户");
            }
            Glide.with(context)
                    .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                    .dontAnimate()
                    .crossFade()
                    .into(viewHolder_11.uphoto);
        }else if( holder instanceof HomeAttentionCommonViewAdapter.ViewHolder_12){
            final homeattentiondata.DetailBean data = (homeattentiondata.DetailBean) list.get(position);
            HomeAttentionCommonViewAdapter.ViewHolder_12 viewHolder_12 = (HomeAttentionCommonViewAdapter.ViewHolder_12) holder;
            viewHolder_12.tdtitle.setText(data.getTdtitle().toString());
            viewHolder_12.answer.setText(data.getAnswer().toString()+" 回答");
            viewHolder_12.attention.setText(data.getAttention().toString()+" 关注 · ");
        }else{
            final homeattentiondata.TechpersonzoneBean data = (homeattentiondata.TechpersonzoneBean) list.get(position);
            HomeAttentionCommonViewAdapter.ViewHolder_13 viewHolder_13 = (HomeAttentionCommonViewAdapter.ViewHolder_13) holder;
            viewHolder_13.tpzname.setText(data.getTpzname().toString());
            viewHolder_13.ualiase.setText(data.getUaliase().toString());
            Glide.with(context)
                    .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                    .dontAnimate()
                    .crossFade()
                    .into(viewHolder_13.uphoto);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_11 extends RecyclerView.ViewHolder{

        TextView ualiase,ulevel,utype;
        CircleImageView uphoto;

        ViewHolder_11(View itemView){
            super(itemView);
            ualiase = itemView.findViewById(R.id.home_attention_user_ualiase);
            ulevel = itemView.findViewById(R.id.home_attention_user_ulevel);
            utype = itemView.findViewById(R.id.home_attention_user_utype);
            uphoto = itemView.findViewById(R.id.home_attention_user_uphoto);
        }
    }

    private class ViewHolder_12 extends RecyclerView.ViewHolder{

        TextView tdtitle,attention,answer;

        ViewHolder_12(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.home_attention_question_tdtitle);
            attention = itemView.findViewById(R.id.home_attention_question_attention);
            answer = itemView.findViewById(R.id.home_attention_question_answer);
        }
    }

    private class ViewHolder_13 extends RecyclerView.ViewHolder{

        TextView tpzname,ualiase;
        CircleImageView uphoto;

        ViewHolder_13(View itemView){
            super(itemView);
            tpzname = itemView.findViewById(R.id.home_attention_tpz_tpzname);
            uphoto = itemView.findViewById(R.id.home_attention_tpz_uphoto);
            ualiase = itemView.findViewById(R.id.home_attention_tpz_ualiase);
        }
    }
}
