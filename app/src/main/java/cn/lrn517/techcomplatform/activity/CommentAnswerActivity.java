package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.CommentAnswerDataViewAdapter;
import cn.lrn517.techcomplatform.bean.commentAnswerData;
import cn.lrn517.techcomplatform.bean.commonForCommentAnswer;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAnswerActivity extends AppCompatActivity {

    private ImageView close,send;
    private EditText send_content;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private CommentAnswerDataViewAdapter commentAnswerDataViewAdapter;
    List data;
    String cid = "";
    String content_s = "";
    Call call;
    DetailModel detailModel = new DetailModel();


    //测试数据
    String uid = "20180319155823";
    String ualiase = "tchCST582你好好3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_answer);
        initView();
        initEvent();
    }

    private void initView(){
        close = (ImageView) findViewById(R.id.comment_answer_close);
        send = (ImageView) findViewById(R.id.comment_answer_send);
        send_content = (EditText) findViewById(R.id.comment_answer_content);
        recyclerView = (RecyclerView) findViewById(R.id.comment_answer_commentRecyclerView);
        linearLayoutManager = new LinearLayoutManager(CommentAnswerActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        cid = bundle.getString("cid");
        call = detailModel.getCommentAnswerData(cid);
        Callback<List<commentAnswerData>> listCallback = new Callback<List<commentAnswerData>>() {
            @Override
            public void onResponse(Call<List<commentAnswerData>> call, Response<List<commentAnswerData>> response) {
                data = response.body();
                commentAnswerDataViewAdapter = new CommentAnswerDataViewAdapter( CommentAnswerActivity.this , data);
                recyclerView.setAdapter(commentAnswerDataViewAdapter);
            }

            @Override
            public void onFailure(Call<List<commentAnswerData>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content_s = send_content.getText().toString();
                call = detailModel.sendCommentAnswer(cid ,uid , content_s);
                Callback<commonForCommentAnswer> commonForCommentAnswerCallback = new Callback<commonForCommentAnswer>() {
                    @Override
                    public void onResponse(Call<commonForCommentAnswer> call, Response<commonForCommentAnswer> response) {
                        commonForCommentAnswer newdata = response.body();
                        if( 1 == newdata.getSuccess()){
                            addData(newdata);
                        }
                    }

                    @Override
                    public void onFailure(Call<commonForCommentAnswer> call, Throwable t) {

                    }
                };
                call.enqueue(commonForCommentAnswerCallback);
            }
        });
    }

    private void addData(commonForCommentAnswer idata){
        commentAnswerData i = new commentAnswerData();
        i.setCatime(idata.getCatime().toString());
        i.setCid(cid);
        i.setContent(content_s);
        i.setUaliase(ualiase);
        data.add(i);
        commentAnswerDataViewAdapter.notifyDataSetChanged();
    }

}
