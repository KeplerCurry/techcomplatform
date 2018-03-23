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

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techFirstComment;
import cn.lrn517.techcomplatform.model.DetailModel;
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
        viewHolder.content.setText(data.getContent().toString());
        viewHolder.chit.setText(data.getChit().toString());
        viewHolder.ctime.setText(data.getCtime().toString());
        viewHolder.ualiase.setText(data.getUaliase().toString());
        viewHolder.layout.setVisibility(View.GONE);

        viewHolder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( "回复".equals(viewHolder.reply.getText().toString()) ){
                    viewHolder.layout.setVisibility(View.VISIBLE);
                    viewHolder.reply.setText("取消回复");
                }else{
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.reply.setText("回复");
                }

            }
        });
        viewHolder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = viewHolder.sendtext.getText().toString();
                String cid = data.getCid().toString();
                Log.i("Adapter" , "cid:"+cid + "content:"+content);
                reply(cid, content);
            }
        });
        getCommentAgainData(data.getCid().toString() , viewHolder);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ualiase,content,ctime,chit,reply;
        RecyclerView commentAgainrecyclerView;
        LinearLayoutManager linearLayoutManager;
        LinearLayout layout;
        EditText sendtext;
        ImageView send;


        public ViewHolder(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.tech_first_comment_ualiase);
            content = itemView.findViewById(R.id.tech_first_comment_content);
            ctime = itemView.findViewById(R.id.tech_first_comment_ctime);
            chit = itemView.findViewById(R.id.tech_first_comment_chit);
            reply = itemView.findViewById(R.id.tech_first_comment_reply);
            layout = itemView.findViewById(R.id.tech_first_comment_layout);
            sendtext = itemView.findViewById(R.id.tech_first_comment_sendtext);
            send = itemView.findViewById( R.id.tech_first_comment_send);
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

    public void reply(final String cid ,final String content){
        call = detailModel.sendCommentAgain(cid, healer , content);
        Callback<commonForTech> commonForTechCallback = new Callback<commonForTech>() {
            @Override
            public void onResponse(Call<commonForTech> call, Response<commonForTech> response) {
                commonForTech data = response.body();
                if( 1 == data.getSuccess() ){
                    catime = data.getTime().toString();
                    addCommentAgainData(cid , catime ,content);
                }
            }

            @Override
            public void onFailure(Call<commonForTech> call, Throwable t) {

            }
        };
        call.enqueue(commonForTechCallback);
    }

    public void addCommentAgainData(String cid , String catime ,String content){

        techCommentAgain testdata = new techCommentAgain();
        testdata.setCatime(catime);
        testdata.setUaliase(aliase);
        testdata.setContent( content);
        testdata.setCid(cid);
        data.add(testdata);
        techCommentAgainViewAdapter.notifyDataSetChanged();
        Log.i("adddata" , "cid = " + cid +"的数据添加成功！");

    }

}
