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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
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
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.content.setText(data.getContent().toString());
        viewHolder.chit.setText(data.getChit().toString());
        viewHolder.ctime.setText(data.getCtime().toString());
        viewHolder.ualiase.setText(data.getUaliase().toString());
        getCommentAgainData(data.getCid().toString() , viewHolder);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ualiase,content,ctime,chit;
        RecyclerView commentAgainrecyclerView;
        LinearLayoutManager linearLayoutManager;


        public ViewHolder(View itemView) {
            super(itemView);
            ualiase = itemView.findViewById(R.id.tech_first_comment_ualiase);
            content = itemView.findViewById(R.id.tech_first_comment_content);
            ctime = itemView.findViewById(R.id.tech_first_comment_ctime);
            chit = itemView.findViewById(R.id.tech_first_comment_chit);
            commentAgainrecyclerView = itemView.findViewById(R.id.tech_first_comment_commentagainView);
            linearLayoutManager = new LinearLayoutManager( context );
            commentAgainrecyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public void getCommentAgainData(String cid , final ViewHolder holder){
        call = detailModel.getTechCommentAgain(cid);
        Callback<List<techCommentAgain>> listCallback = new Callback<List<techCommentAgain>>() {
            @Override
            public void onResponse(Call<List<techCommentAgain>> call, Response<List<techCommentAgain>> response) {
                List data = response.body();
                Log.i("testxxxx" , "=============="+data.size());
                if( 0 != data.size()){
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
