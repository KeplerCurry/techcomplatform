package cn.lrn517.techcomplatform.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.TechPersonZoneFirstCommentAdapter;
import cn.lrn517.techcomplatform.bean.commonForTPZ;
import cn.lrn517.techcomplatform.bean.tpzFirstComment;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentTPZDetailActivity extends AppCompatActivity {

    private ImageView send;
    Toolbar toolbar;
    private EditText sendtext;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    Call call;
    List firstCommentData;
    String tpzdid;
    String tpzcid;
    String tpzctime;

    String uid = "";
    String content = "";
    String toualiase;
    int flag = 0;

    TechPersonZoneFirstCommentAdapter techPersonZoneFirstCommentAdapter;
    private RecyclerView firstRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    public SwipeRefreshLayout swipeRefreshLayout;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_tpzdetail);
        initView();
        initEvent();
    }

    private void initView(){
        Bundle bundle = getIntent().getExtras();
        tpzdid = bundle.getString("tpzdid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        toolbar = (Toolbar) findViewById(R.id.comment_tpzdetail_toolbar);
        toolbar.setTitle("评论列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send = (ImageView) findViewById(R.id.comment_tpzdetail_send);
        sendtext = (EditText) findViewById(R.id.comment_tpzdetail_content);
        firstRecyclerView = (RecyclerView) findViewById(R.id.comment_tpzdetail_recyclerview);
        linearLayoutManager = new LinearLayoutManager(CommentTPZDetailActivity.this);
        firstRecyclerView.setLayoutManager(linearLayoutManager);
        firstRecyclerView.setNestedScrollingEnabled(false);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        swipeRefreshLayout = findViewById(R.id.comment_tpzdetail_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorBasic));
    }

    private void initEvent(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getFirstComment();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            content = sendtext.getText().toString();
            if( null != uid ){
                if( 0 == flag ){
                    sendFirstComment();
                }else{
                    sendCommentAgain();
                }
            }else{

            }
            }
        });
    }

    private void getFirstComment(){
        swipeRefreshLayout.setRefreshing(true);
        call = techPersonZoneModel.getTechPersonZoneFirstCommentData(tpzdid);
        Callback<List<tpzFirstComment>> listCallback = new Callback<List<tpzFirstComment>>() {
            @Override
            public void onResponse(Call<List<tpzFirstComment>> call, Response<List<tpzFirstComment>> response) {
                firstCommentData = response.body();
                if( 0 != firstCommentData.size()){
                    techPersonZoneFirstCommentAdapter = new TechPersonZoneFirstCommentAdapter( CommentTPZDetailActivity.this , firstCommentData);
                    firstRecyclerView.setAdapter(techPersonZoneFirstCommentAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    techPersonZoneFirstCommentAdapter = new TechPersonZoneFirstCommentAdapter( CommentTPZDetailActivity.this , firstCommentData);
                    firstRecyclerView.setAdapter(techPersonZoneFirstCommentAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<tpzFirstComment>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }

    public void send(String toualiase,String cid,int flag){
        this.flag = flag;
        if( 0 == flag ){
            this.tpzcid = cid;
            this.toualiase = toualiase;
            //Toast.makeText(this, "回到一层评论", Toast.LENGTH_SHORT).show();
            sendtext.setHint("请输入要回复的内容");
            sendtext.setText("");
        }else{
            this.tpzcid = cid;
            this.toualiase = toualiase;
            //Toast.makeText(this, "来到二层评论", Toast.LENGTH_SHORT).show();
            sendtext.setHint("回复"+toualiase+":");
            sendtext.setText("");
            inputMethodManager.showSoftInput(sendtext , 0);
        }
    }


    private void sendFirstComment(){
        content = sendtext.getText().toString();
        call = techPersonZoneModel.sendTechPersonZoneFirstComment(tpzdid,uid,content);
        Callback<commonForTPZ> commonForTPZCallback = new Callback<commonForTPZ>() {
            @Override
            public void onResponse(Call<commonForTPZ> call, Response<commonForTPZ> response) {
                commonForTPZ data = response.body();
                if( 1 == data.getSuccess()){
                    getFirstComment();
                    inputMethodManager.hideSoftInputFromWindow(sendtext.getWindowToken(),0);
                    sendtext.setHint("请输入要回复的内容");
                    sendtext.setText("");
                    Toast.makeText(CommentTPZDetailActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<commonForTPZ> call, Throwable t) {

            }
        };
        call.enqueue(commonForTPZCallback);
    }

    private void sendCommentAgain(){
        call = techPersonZoneModel.sendTechPersonZoneCommentAgain(tpzcid, uid , content);
        Callback<commonForTPZ> commonForTPZCallback = new Callback<commonForTPZ>() {
            @Override
            public void onResponse(Call<commonForTPZ> call, Response<commonForTPZ> response) {
                commonForTPZ data = response.body();
                if( 1 == data.getSuccess() ){
                    getFirstComment();
                    inputMethodManager.hideSoftInputFromWindow(sendtext.getWindowToken(),0);
                    sendtext.setHint("请输入要回复的内容");
                    sendtext.setText("");
                    Toast.makeText(CommentTPZDetailActivity.this, "回复"+toualiase+"成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<commonForTPZ> call, Throwable t) {

            }
        };
        call.enqueue(commonForTPZCallback);
    }



}
