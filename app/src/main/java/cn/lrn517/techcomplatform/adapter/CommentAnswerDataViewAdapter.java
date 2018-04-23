package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.commentAnswerData;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lirun on 2018/3/25.
 */

public class CommentAnswerDataViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List list;
    private LayoutInflater layoutInflater;

    public CommentAnswerDataViewAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.list = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.comment_answer_list_data, parent, false);
        return new CommentAnswerDataViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final commentAnswerData data = (commentAnswerData) list.get(position);
        if( null == data )
            return;
        CommentAnswerDataViewAdapter.ViewHolder viewHolder = (CommentAnswerDataViewAdapter.ViewHolder) holder;
        Glide.with(context)
                .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                .dontAnimate()
                .crossFade()
                .into(viewHolder.uphoto);
        viewHolder.content.setText(data.getContent().toString());
        viewHolder.catime.setText(data.getCatime().toString());
        viewHolder.ualiase.setText(data.getUaliase().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ualiase,content,catime;
        CircleImageView uphoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.comment_answer_list_ualiase);
            content = itemView.findViewById(R.id.comment_answer_list_content);
            catime = itemView.findViewById(R.id.comment_answer_list_catime);
            uphoto = itemView.findViewById(R.id.comment_answer_list_uphoto);
        }
    }
}
