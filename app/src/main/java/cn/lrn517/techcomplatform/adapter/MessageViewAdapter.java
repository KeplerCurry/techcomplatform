package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.MessageActivity;
import cn.lrn517.techcomplatform.bean.loadMessageByUid;
import cn.lrn517.techcomplatform.bean.loadMessageData;

/**
 * Created by lirun on 2018/4/10.
 */

public class MessageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;
    String uid;

    public MessageViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i ,String uid){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
        this.uid = uid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 1 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.message_list_view,parent,false);
            return new MessageViewAdapter.ViewHolder_1(view);
        }else{
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.chat_view,parent,false);
            return new MessageViewAdapter.ViewHolder_2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof MessageViewAdapter.ViewHolder_1){
            final loadMessageData.ListBean data = (loadMessageData.ListBean) list.get(position);
            MessageViewAdapter.ViewHolder_1 viewHolder_1 = (MessageViewAdapter.ViewHolder_1) holder;
            viewHolder_1.ualiase.setText(data.getUaliase().toString());
            viewHolder_1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context , MessageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid" , data.getSendid().toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else{
            final loadMessageByUid data1 = (loadMessageByUid) list.get(position);
            MessageViewAdapter.ViewHolder_2 viewHolder_2 = (MessageViewAdapter.ViewHolder_2) holder;
            if( uid.equals(data1.getSendid())){
                viewHolder_2.left.setVisibility(View.GONE);
                viewHolder_2.createtime_r.setText(data1.getCreatetime().toString());
                if( 1 == data1.getIsread()){
                    viewHolder_2.isread_r.setText("已读");
                }else{
                    viewHolder_2.isread_r.setText("未读");
                }
                viewHolder_2.ualiase_r.setText(data1.getUaliase().toString());
                viewHolder_2.text_r.setText(data1.getText().toString());
            }else{
                viewHolder_2.right.setVisibility(View.GONE);
                viewHolder_2.createtime_l.setText(data1.getCreatetime().toString());
                if( 1 == data1.getIsread()){
                    viewHolder_2.isread_l.setText("已读");
                }else{
                    viewHolder_2.isread_l.setText("未读");
                }
                viewHolder_2.ualiase_l.setText(data1.getUaliase().toString());
                viewHolder_2.text_l.setText(data1.getText().toString());
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_1 extends RecyclerView.ViewHolder{

        TextView ualiase;
        ImageView image;

        ViewHolder_1(View itemView){
            super(itemView);
            ualiase = itemView.findViewById(R.id.message_list_view_ualiase);
        }
    }

    private class ViewHolder_2 extends RecyclerView.ViewHolder {

        LinearLayout left, right;
        TextView createtime_l, ualiase_l, text_l, isread_l;
        TextView createtime_r, ualiase_r, text_r, isread_r;

        ViewHolder_2(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.chat_left);
            right = itemView.findViewById(R.id.chat_right);
            createtime_l = itemView.findViewById(R.id.chat_createtime_left);
            ualiase_l = itemView.findViewById(R.id.chat_ualiase_left);
            text_l = itemView.findViewById(R.id.chat_text_left);
            isread_l = itemView.findViewById(R.id.chat_isread_left);
            createtime_r = itemView.findViewById(R.id.chat_createtime_right);
            ualiase_r = itemView.findViewById(R.id.chat_ualiase_right);
            text_r = itemView.findViewById(R.id.chat_text_right);
            isread_r = itemView.findViewById(R.id.chat_isread_right);
        }
    }

}
