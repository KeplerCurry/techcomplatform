package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.commonAttentionData;

/**
 * Created by lirun on 2018/4/8.
 */

public class MyLikeCommonViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;

    public MyLikeCommonViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 21 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_like_detail_view,parent,false);
            return new MyLikeCommonViewAdapter.ViewHolder_21(view);
        }else if( 22 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_like_answer_view,parent,false);
            return new MyLikeCommonViewAdapter.ViewHolder_22(view);
        }else{
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_like_tpzdetail_view,parent,false);
            return new MyLikeCommonViewAdapter.ViewHolder_23(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final commonAttentionData data = (commonAttentionData) list.get(position);
        if( holder instanceof MyLikeCommonViewAdapter.ViewHolder_21){
            MyLikeCommonViewAdapter.ViewHolder_21 viewHolder_21 = (MyLikeCommonViewAdapter.ViewHolder_21) holder;
            viewHolder_21.tdtitle.setText(data.getTdtitle().toString());
            viewHolder_21.tname.setText(data.getTname().toString());
        }else if( holder instanceof MyLikeCommonViewAdapter.ViewHolder_22){
            MyLikeCommonViewAdapter.ViewHolder_22 viewHolder_22 = (MyLikeCommonViewAdapter.ViewHolder_22) holder;
            viewHolder_22.content.setText(data.getContent().toString());
            viewHolder_22.tdtitle.setText(data.getTdtitle().toString());
        }else{
            MyLikeCommonViewAdapter.ViewHolder_23 viewHolder_23 = (MyLikeCommonViewAdapter.ViewHolder_23) holder;
            viewHolder_23.tpzname.setText(data.getTpzname().toString());
            viewHolder_23.tpzdtitle.setText(data.getTpzdtitle().toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_21 extends RecyclerView.ViewHolder{

        TextView tdtitle,tname;

        ViewHolder_21(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_like_detail_tdtitle);
            tname = itemView.findViewById(R.id.my_like_detail_tname);

        }
    }

    private class ViewHolder_22 extends RecyclerView.ViewHolder{

        TextView content,tdtitle;

        ViewHolder_22(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_like_answer_tdtitle);
            content = itemView.findViewById(R.id.my_like_answer_content);
        }
    }

    private class ViewHolder_23 extends RecyclerView.ViewHolder{

        TextView tpzname,tpzdtitle;

        ViewHolder_23(View itemView){
            super(itemView);
            tpzdtitle = itemView.findViewById(R.id.my_like_tpzdetail_tpzdtitle);
            tpzname = itemView.findViewById(R.id.my_like_tpzdetail_tpzname);
        }
    }
}
