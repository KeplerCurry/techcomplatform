package cn.lrn517.techcomplatform.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

    private ImageView send;
    private EditText send_content;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private CommentAnswerDataViewAdapter commentAnswerDataViewAdapter;
    private Toolbar toolbar;
    List data;
    String cid = "";
    String content_s = "";
    Call call;
    DetailModel detailModel = new DetailModel();
    private SharedPreferences sharedPreferences;
    public SwipeRefreshLayout swipeRefreshLayout;

    //测试数据
    String uid = null;
    String ualiase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_answer);
        initView();
        initEvent();
    }

    private void initView(){
        send = (ImageView) findViewById(R.id.comment_answer_send);
        send_content = (EditText) findViewById(R.id.comment_answer_content);
        recyclerView = (RecyclerView) findViewById(R.id.comment_answer_commentRecyclerView);
        linearLayoutManager = new LinearLayoutManager(CommentAnswerActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = findViewById(R.id.comment_answer_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#1296db"));
        toolbar = (Toolbar) findViewById(R.id.comment_answer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        cid = bundle.getString("cid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null );
        getCommentList();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content_s = send_content.getText().toString();
                if( null != uid ){
                    sendComment();
                }else{
                    Toast.makeText(CommentAnswerActivity.this, "请登陆！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                send_content.setHint("请输入要回复的内容");
                send_content.setText("");
                getCommentList();
                Toast.makeText(CommentAnswerActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendComment(){
        swipeRefreshLayout.setRefreshing(true);
        call = detailModel.sendCommentAnswer(cid ,uid , content_s);
        Callback<commonForCommentAnswer> commonForCommentAnswerCallback = new Callback<commonForCommentAnswer>() {
            @Override
            public void onResponse(Call<commonForCommentAnswer> call, Response<commonForCommentAnswer> response) {
                commonForCommentAnswer newdata = response.body();
                if( 1 == newdata.getSuccess()){
                    Toast.makeText(CommentAnswerActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
                    getCommentList();
                }
            }

            @Override
            public void onFailure(Call<commonForCommentAnswer> call, Throwable t) {

            }
        };
        call.enqueue(commonForCommentAnswerCallback);
    }

    private void getCommentList(){
        swipeRefreshLayout.setRefreshing(true);
        call = detailModel.getCommentAnswerData(cid);
        Callback<List<commentAnswerData>> listCallback = new Callback<List<commentAnswerData>>() {
            @Override
            public void onResponse(Call<List<commentAnswerData>> call, Response<List<commentAnswerData>> response) {
                data = response.body();
                commentAnswerDataViewAdapter = new CommentAnswerDataViewAdapter( CommentAnswerActivity.this , data);
                recyclerView.setAdapter(commentAnswerDataViewAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<commentAnswerData>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }


}
