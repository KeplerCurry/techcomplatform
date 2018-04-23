package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.techCommentAgain;

/**
 * Created by lirun on 2018/3/22.
 */

public class TechCommentAgainViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List list;
    private LayoutInflater layoutInflater;

    public TechCommentAgainViewAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.list = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.tech_comment_again_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final techCommentAgain data = (techCommentAgain) list.get(position);
        if( null == data )
            return;
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.content.setText(data.getContent().toString());
        //viewHolder.catime.setText(data.getCatime().toString());
        viewHolder.ualiase.setText(data.getUaliase().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ualiase,content,catime;


        public ViewHolder(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.tech_comment_again_ualiase);
            content = itemView.findViewById(R.id.tech_comment_again_content);
        }
    }
}
