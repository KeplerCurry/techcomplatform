package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.CommentTPZDetailActivity;
import cn.lrn517.techcomplatform.bean.commonForTPZ;
import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techFirstComment;
import cn.lrn517.techcomplatform.bean.tpzCommentAgain;
import cn.lrn517.techcomplatform.bean.tpzFirstComment;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lirun on 2018/4/2.
 */

public class TechPersonZoneFirstCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List mDataList;
    private LayoutInflater layoutInflater;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    private Call call;
    TechPersonZoneCommentAgainAdapter techPersonZoneCommentAgainAdapter;
    List data;

    public TechPersonZoneFirstCommentAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.mDataList = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.tech_first_comment_view, parent, false);
        return new TechPersonZoneFirstCommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final tpzFirstComment data = (tpzFirstComment) mDataList.get(position);
        if( null == data )
            return;
        final TechPersonZoneFirstCommentAdapter.ViewHolder viewHolder = (TechPersonZoneFirstCommentAdapter.ViewHolder) holder;
        Glide.with(context)
                .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                .dontAnimate()
                .crossFade()
                .into(viewHolder.uphoto);
        viewHolder.content.setText(data.getContent().toString());
        viewHolder.ctime.setText(data.getTpzctime().toString());
        viewHolder.ualiase.setText(data.getUaliase().toString());

        viewHolder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( "回复".equals(viewHolder.reply.getText().toString()) ){
                    //viewHolder.layout.setVisibility(View.VISIBLE);
                    viewHolder.reply.setText("取消回复");
                    ((CommentTPZDetailActivity) context).send(data.getUaliase().toString(),data.getTpzcid().toString(),1);
                }else{
                    //viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.reply.setText("回复");
                    ((CommentTPZDetailActivity) context).send(data.getUaliase().toString(),data.getTpzcid().toString(),0);
                }

            }
        });
        getCommentAgainData(data.getTpzcid().toString() , viewHolder);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ualiase,content,ctime,reply;
        RecyclerView commentAgainrecyclerView;
        CircleImageView uphoto;
        LinearLayoutManager linearLayoutManager;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            uphoto = itemView.findViewById(R.id.tech_first_comment_uphoto);
            ualiase = itemView.findViewById(R.id.tech_first_comment_ualiase);
            content = itemView.findViewById(R.id.tech_first_comment_content);
            ctime = itemView.findViewById(R.id.tech_first_comment_ctime);
            reply = itemView.findViewById(R.id.tech_first_comment_reply);
            commentAgainrecyclerView = itemView.findViewById(R.id.tech_first_comment_commentagainView);
            linearLayoutManager = new LinearLayoutManager( context );
            commentAgainrecyclerView.setLayoutManager(linearLayoutManager);
            //临时添加
            commentAgainrecyclerView.setNestedScrollingEnabled(false);
        }
    }

    public void getCommentAgainData(String tpzcid , final TechPersonZoneFirstCommentAdapter.ViewHolder holder){
        call = techPersonZoneModel.getTechPersonZoneCommentAgainData(tpzcid);
        Callback<List<techCommentAgain>> listCallback = new Callback<List<techCommentAgain>>() {
            @Override
            public void onResponse(Call<List<techCommentAgain>> call, Response<List<techCommentAgain>> response) {
                data = response.body();
                Log.i("testxxxx" , "=============="+data.size());
                if( 0 != data.size()){
                    techPersonZoneCommentAgainAdapter = new TechPersonZoneCommentAgainAdapter(context , data);
                    holder.commentAgainrecyclerView.setAdapter(techPersonZoneCommentAgainAdapter);
                    Log.i("testxxxx" , "==============又开始适配了");
                }
                else{
                    techPersonZoneCommentAgainAdapter = new TechPersonZoneCommentAgainAdapter(context , data);
                    holder.commentAgainrecyclerView.setAdapter(techPersonZoneCommentAgainAdapter);
                    Log.i("testxxxx" , "==============又开始适配了");
                }
            }

            @Override
            public void onFailure(Call<List<techCommentAgain>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }

}
