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

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.searchlist;
import cn.lrn517.techcomplatform.bean.userSearchHistory;

/**
 * Created by lirun on 2018/5/9.
 */

public class SearchCommonViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //控制变量
    int i;
    List list;
    private LayoutInflater layoutInflater;
    Context context;
    View view;

    public SearchCommonViewAdapter(Context context , List<Map<String,Object>> mDatalist , int i){
        this.context = context;
        this.list = mDatalist;
        layoutInflater = LayoutInflater.from(context);
        this.i = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( 0 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_user_view, parent, false);
            return new SearchCommonViewAdapter.ViewHolder_0(view);
        }else if( 1 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view,parent,false);
            return new SearchCommonViewAdapter.ViewHolder_1(view);
        }else if( 2 == i ){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_attention_question_view,parent,false);
            return new SearchCommonViewAdapter.ViewHolder_2(view);
        }else if( 3 == i){
            view = layoutInflater.from(parent.getContext()).inflate(R.layout.my_collection_common_detail_view, parent, false);
            return new SearchCommonViewAdapter.ViewHolder_3(view);
        }else{
            view= layoutInflater.from(parent.getContext()).inflate(R.layout.search_history_list_view, parent, false);
            return new SearchCommonViewAdapter.ViewHolder_4(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof SearchCommonViewAdapter.ViewHolder_0){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_0 viewHolder_0 = (SearchCommonViewAdapter.ViewHolder_0) holder;
            viewHolder_0.ualiase.setText(data.getUaliase());
            viewHolder_0.ulevel.setText(data.getUlevel());
            viewHolder_0.utype.setText(data.getUtype());
        }else if( holder instanceof SearchCommonViewAdapter.ViewHolder_1){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_1 viewHolder_1 = (SearchCommonViewAdapter.ViewHolder_1) holder;
            viewHolder_1.name.setText(data.getTdtitle());
        }else if (holder instanceof SearchCommonViewAdapter.ViewHolder_2){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_2 viewHolder_2 = (SearchCommonViewAdapter.ViewHolder_2) holder;
            viewHolder_2.tdtitle.setText(data.getTdtitle());
        }else if(holder instanceof SearchCommonViewAdapter.ViewHolder_3){
            final searchlist data = (searchlist) list.get(position);
            SearchCommonViewAdapter.ViewHolder_3 viewHolder_3 = (SearchCommonViewAdapter.ViewHolder_3) holder;
            viewHolder_3.name.setText(data.getTpzdtitle());
        }else{
            final userSearchHistory data = (userSearchHistory) list.get(position);
            SearchCommonViewAdapter.ViewHolder_4 viewHolder_4 = (SearchCommonViewAdapter.ViewHolder_4) holder;
            viewHolder_4.text.setText(data.getSearchcontent());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder_2 extends RecyclerView.ViewHolder{

        TextView tdtitle;

        ViewHolder_2(View itemView){
            super(itemView);
            tdtitle = itemView.findViewById(R.id.my_attention_question_tdtitle);
        }
    }

    private class ViewHolder_1 extends RecyclerView.ViewHolder{

        TextView name;

        ViewHolder_1(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
        }
    }

    public class ViewHolder_0 extends RecyclerView.ViewHolder{

        TextView ualiase,ulevel,utype;
        ImageView image;

        public ViewHolder_0(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.my_attention_user_ualiase);
            ulevel = itemView.findViewById(R.id.my_attention_user_ulevel);
            utype = itemView.findViewById(R.id.my_attention_user_utype);
            image = itemView.findViewById(R.id.my_attention_user_image);
        }
    }

    public class ViewHolder_3 extends RecyclerView.ViewHolder{

        TextView name;


        public ViewHolder_3(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_collection_common_detail_name);
        }
    }

    public class ViewHolder_4 extends RecyclerView.ViewHolder{

        TextView text;
        ImageView del;


        public ViewHolder_4(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.search_history_list_text);
            del = itemView.findViewById(R.id.search_history_list_del);
        }
    }
}
