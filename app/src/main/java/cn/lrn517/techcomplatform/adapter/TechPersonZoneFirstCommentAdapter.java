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

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.commonForTPZ;
import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techFirstComment;
import cn.lrn517.techcomplatform.bean.tpzCommentAgain;
import cn.lrn517.techcomplatform.bean.tpzFirstComment;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
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
    //测试数据
    String healer = "20180319155823";
    String aliase = "tchCST582你好好3";
    String tpzcatime = "";

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
        viewHolder.content.setText(data.getContent().toString());
        viewHolder.ctime.setText(data.getTpzctime().toString());
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
                String tpzcid = data.getTpzcid().toString();
                Log.i("Adapter" , "tpzcid:"+tpzcid + "content:"+content);
                reply(tpzcid, content);
            }
        });
        getCommentAgainData(data.getTpzcid().toString() , viewHolder);

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

    public void reply(final String tpzcid ,final String content){
        call = techPersonZoneModel.sendTechPersonZoneCommentAgain(tpzcid, healer , content);
        Callback<commonForTPZ> commonForTPZCallback = new Callback<commonForTPZ>() {
            @Override
            public void onResponse(Call<commonForTPZ> call, Response<commonForTPZ> response) {
                commonForTPZ data = response.body();
                if( 1 == data.getSuccess() ){
                    tpzcatime = data.getTime().toString();
                    addCommentAgainData(tpzcid , tpzcatime ,content);
                }
            }

            @Override
            public void onFailure(Call<commonForTPZ> call, Throwable t) {

            }
        };
        call.enqueue(commonForTPZCallback);
    }

    public void addCommentAgainData(String tpzcid , String tpzcatime ,String content){

        tpzCommentAgain testdata = new tpzCommentAgain();
        testdata.setTpzcatime(tpzcatime);
        testdata.setUaliase(aliase);
        testdata.setContent( content);
        testdata.setTpzcid(tpzcid);
        data.add(testdata);
        techPersonZoneCommentAgainAdapter.notifyDataSetChanged();
        Log.i("adddata" , "tpzcid = " + tpzcid +"的数据添加成功！");

    }
}
