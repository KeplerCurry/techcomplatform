package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.EditCommonActivity;
import cn.lrn517.techcomplatform.bean.commonForUserSend;

/**
 * Created by lirun on 2018/5/1.
 */

public class MySendCommonViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;

    public MySendCommonViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 0 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view, parent, false);
            return new MySendCommonViewAdapter.ViewHolder_0(view);
        }else if( 1 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_question_view,parent,false);
            return new MySendCommonViewAdapter.ViewHolder_1(view);
        }else if( 2 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_like_answer_view,parent,false);
            return new MySendCommonViewAdapter.ViewHolder_2(view);
        }else{
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view, parent, false);
            return new MySendCommonViewAdapter.ViewHolder_3(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final commonForUserSend data = (commonForUserSend) list.get(position);
        if( holder instanceof MySendCommonViewAdapter.ViewHolder_0){
            MySendCommonViewAdapter.ViewHolder_0 viewHolder_0 = (MySendCommonViewAdapter.ViewHolder_0) holder;
            viewHolder_0.name.setText(data.getTdtitle());
            viewHolder_0.tname.setText(data.getTname());
            viewHolder_0.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "长按修改", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context , EditCommonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("state" , 0);
                    bundle.putString("id",data.getTdid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    return true;
                }
            });
        }else if( holder instanceof MySendCommonViewAdapter.ViewHolder_1){
            MySendCommonViewAdapter.ViewHolder_1 viewHolder_1 = (MySendCommonViewAdapter.ViewHolder_1) holder;
            viewHolder_1.tdtitle.setText(data.getTdtitle());
            viewHolder_1.tname.setText(data.getTname());
            viewHolder_1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "长按修改", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context , EditCommonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("state" , 1);
                    bundle.putString("id",data.getTdid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    return true;
                }
            });
        }else if (holder instanceof MySendCommonViewAdapter.ViewHolder_2){
            MySendCommonViewAdapter.ViewHolder_2 viewHolder_2 = (MySendCommonViewAdapter.ViewHolder_2) holder;
            viewHolder_2.content.setText(data.getContent());
            viewHolder_2.tdtitle.setText(data.getTdtitle());
            viewHolder_2.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "长按修改", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context , EditCommonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("state" , 2);
                    bundle.putString("id",data.getCid());
                    bundle.putString("tdtitle" , data.getTdtitle());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    return true;
                }
            });
        }else{
            MySendCommonViewAdapter.ViewHolder_3 viewHolder_3 = (MySendCommonViewAdapter.ViewHolder_3) holder;
            viewHolder_3.name.setText(data.getTpzdtitle());
            viewHolder_3.tname.setText(data.getTpzname());
            viewHolder_3.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "长按修改", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context , EditCommonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("state" , 3);
                    bundle.putString("id",data.getTpzdid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_2 extends RecyclerView.ViewHolder{

        TextView content,tdtitle;

        ViewHolder_2(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_like_answer_tdtitle);
            content = itemView.findViewById(R.id.my_like_answer_content);
        }
    }

    private class ViewHolder_1 extends RecyclerView.ViewHolder{

        TextView tdtitle;
        TextView tname;
        ViewHolder_1(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_attention_question_tdtitle);
            tname = itemView.findViewById(R.id.my_attention_question_tname);
        }
    }

    public class ViewHolder_0 extends RecyclerView.ViewHolder{

        TextView name;
        TextView tname;

        public ViewHolder_0(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
            tname = itemView.findViewById(R.id.my_collection_common_detail_tname);
        }
    }

    public class ViewHolder_3 extends RecyclerView.ViewHolder{

        TextView name;
        TextView tname;

        public ViewHolder_3(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
            tname = itemView.findViewById(R.id.my_collection_common_detail_tname);
        }
    }
}
