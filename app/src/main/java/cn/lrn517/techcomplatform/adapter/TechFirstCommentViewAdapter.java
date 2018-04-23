package cn.lrn517.techcomplatform.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.TechDetailCommentActivity;
import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techFirstComment;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lirun on 2018/3/22.
 */

public class TechFirstCommentViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List mDataList;
    private LayoutInflater layoutInflater;
    private DetailModel detailModel = new DetailModel();
    private Call call;
    TechCommentAgainViewAdapter techCommentAgainViewAdapter;
    List data;
    //测试数据
    String healer = "20180319155823";
    String aliase = "tchCST582你好好3";
    String catime = "";

    public TechFirstCommentViewAdapter(Context context , List<Map<String,Object>> mDataList){
        this.context = context;
        this.mDataList = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.from(parent.getContext()).inflate(R.layout.tech_first_comment_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final techFirstComment data = (techFirstComment) mDataList.get(position);
        if( null == data )
            return;
        final ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(context)
                .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                .dontAnimate()
                .crossFade()
                .into(viewHolder.uphoto);
        viewHolder.content.setText(data.getContent().toString());
        viewHolder.ctime.setText(data.getCtime().toString());
        viewHolder.ualiase.setText(data.getUaliase().toString());
        Log.i("uphoto" , data.getUphoto().toString());
        //viewHolder.layout.setVisibility(View.GONE);
        viewHolder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( "回复".equals(viewHolder.reply.getText().toString()) ){
                    //viewHolder.layout.setVisibility(View.VISIBLE);
                    viewHolder.reply.setText("取消回复");
                    ((TechDetailCommentActivity) context).send(data.getUaliase().toString(),data.getCid().toString());
                }else{
                    //viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.reply.setText("回复");
                    ((TechDetailCommentActivity) context).send(data.getUaliase().toString(),data.getCid().toString());
                }

            }
        });
        getCommentAgainData(data.getCid().toString() , viewHolder);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ualiase,content,ctime,reply;
        RecyclerView commentAgainrecyclerView;
        LinearLayoutManager linearLayoutManager;
        LinearLayout layout;
        CircleImageView uphoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.tech_first_comment_ualiase);
            content = itemView.findViewById(R.id.tech_first_comment_content);
            ctime = itemView.findViewById(R.id.tech_first_comment_ctime);
            reply = itemView.findViewById(R.id.tech_first_comment_reply);
            uphoto = itemView.findViewById(R.id.tech_first_comment_uphoto);
            commentAgainrecyclerView = itemView.findViewById(R.id.tech_first_comment_commentagainView);
            linearLayoutManager = new LinearLayoutManager( context );
            commentAgainrecyclerView.setLayoutManager(linearLayoutManager);
            //临时添加
            commentAgainrecyclerView.setNestedScrollingEnabled(false);
        }
    }

    public void getCommentAgainData(String cid , final ViewHolder holder){
        call = detailModel.getTechCommentAgain(cid);
        Callback<List<techCommentAgain>> listCallback = new Callback<List<techCommentAgain>>() {
            @Override
            public void onResponse(Call<List<techCommentAgain>> call, Response<List<techCommentAgain>> response) {
                data = response.body();
                Log.i("testxxxx" , "=============="+data.size());
                if( 0 != data.size()){
                    techCommentAgainViewAdapter = new TechCommentAgainViewAdapter(context , data);
                    holder.commentAgainrecyclerView.setAdapter(techCommentAgainViewAdapter);
                    Log.i("testxxxx" , "==============又开始适配了");
                }
                else{
                    techCommentAgainViewAdapter = new TechCommentAgainViewAdapter(context , data);
                    holder.commentAgainrecyclerView.setAdapter(techCommentAgainViewAdapter);
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
