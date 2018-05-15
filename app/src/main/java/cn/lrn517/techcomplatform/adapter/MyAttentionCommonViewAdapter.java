package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.MainActivity;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lirun on 2018/4/8.
 */

public class MyAttentionCommonViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;

    public MyAttentionCommonViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 11 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_user_view,parent,false);
            return new ViewHolder_11(view);
        }else if( 12 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_question_view,parent,false);
            return new ViewHolder_12(view);
        }else{
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_tpz_view,parent,false);
            return new ViewHolder_13(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final commonAttentionData data = (commonAttentionData) list.get(position);
        if( holder instanceof ViewHolder_11){
            ViewHolder_11 viewHolder_11 = (ViewHolder_11) holder;
            viewHolder_11.ualiase.setText(data.getUaliase().toString());
            Glide.with(context)
                    .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                    .dontAnimate()
                    .crossFade()
                    .into(viewHolder_11.image);
            viewHolder_11.ulevel.setText(data.getUlevel());
            if( "0".equals(data.getUtype())){
                viewHolder_11.utype.setText("普通用户");
            }else{
                viewHolder_11.utype.setText("高级用户");
            }
        }else if( holder instanceof ViewHolder_12){
            ViewHolder_12 viewHolder_12 = (ViewHolder_12) holder;
            viewHolder_12.tdtitle.setText(data.getTdtitle());
            viewHolder_12.tname.setText(data.getTname());
        }else{
            ViewHolder_13 viewHolder_13 = (ViewHolder_13) holder;
            viewHolder_13.tpzname.setText(data.getTpzname().toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_11 extends RecyclerView.ViewHolder{

        TextView ualiase,ulevel,utype;
        CircleImageView image;

        ViewHolder_11(View itemView){
            super(itemView);
            ualiase = itemView.findViewById(R.id.my_attention_user_ualiase);
            ulevel = itemView.findViewById(R.id.my_attention_user_ulevel);
            utype = itemView.findViewById(R.id.my_attention_user_utype);
            image = itemView.findViewById(R.id.my_attention_user_image);
        }
    }

    private class ViewHolder_12 extends RecyclerView.ViewHolder{

        TextView tdtitle;
        TextView tname;

        ViewHolder_12(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_attention_question_tdtitle);
            tname = itemView.findViewById(R.id.my_attention_question_tname);
        }
    }

    private class ViewHolder_13 extends RecyclerView.ViewHolder{

        TextView tpzname;

        ViewHolder_13(View itemView){
            super(itemView);
            tpzname = itemView.findViewById(R.id.my_attention_tpz_tpzname);
        }
    }
}
