package cn.lrn517.techcomplatform.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.TechFirstCommentViewAdapter;
import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.techFirstComment;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechDetailCommentActivity extends AppCompatActivity {

    private ImageView send;
    private Call call;
    private EditText sendtext;
    private DetailModel detailModel = new DetailModel();
    private Toolbar toolbar;
    List firstCommentData;
    List adddata;
    String tdid;
    String cid;
    String ctime;
    //测试数据
    String uid = null;
    String ualiase = null;
    String content = "";
    String toualiase;
    int flag = 0;

    TechFirstCommentViewAdapter techFirstCommentViewAdapter;
    private RecyclerView firstRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    public SwipeRefreshLayout swipeRefreshLayout;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_detail_comment);
        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null );
        ualiase = sharedPreferences.getString("ualiase" , null);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tech_detail_comment_toolbar);
        toolbar.setTitle("评论");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send = (ImageView) findViewById(R.id.tech_detail_comment_send);
        sendtext = (EditText) findViewById(R.id.tech_detail_comment_firsttext);
        firstRecyclerView = (RecyclerView) findViewById(R.id.tech_detail_comment_firstcommentView);
        linearLayoutManager = new LinearLayoutManager( TechDetailCommentActivity.this);
        firstRecyclerView.setLayoutManager(linearLayoutManager);
        //临时添加
        //firstRecyclerView.setNestedScrollingEnabled(false);
        swipeRefreshLayout = findViewById(R.id.tech_detail_comment_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#1296db"));
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void initEvent(){

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = 0;
                sendtext.setHint("请输入要回复的内容");
                sendtext.setText("");
                getFirstComment();
                Toast.makeText(TechDetailCommentActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void send(String toualiase,String cid){
        flag = 1==flag ? 0:1;
        if( 0 == flag ){
            this.cid = cid;
            this.toualiase = toualiase;
            //Toast.makeText(this, "回到一层评论", Toast.LENGTH_SHORT).show();
            sendtext.setHint("请输入要回复的内容");
            sendtext.setText("");
        }else{
            this.cid = cid;
            this.toualiase = toualiase;
            //Toast.makeText(this, "来到二层评论", Toast.LENGTH_SHORT).show();
            sendtext.setHint("回复"+toualiase+":");
            sendtext.setText("");
            inputMethodManager.showSoftInput(sendtext , 0);
        }
    }

    private void sendFirstComment(){
        content = sendtext.getText().toString();
        call = detailModel.sendFirstComment(tdid, uid , content);
        Callback<commonForTech> commonForTechCallback = new Callback<commonForTech>() {
            @Override
            public void onResponse(Call<commonForTech> call, Response<commonForTech> response) {
                commonForTech data = response.body();
                if( 1 == data.getSuccess() ){
                    getFirstComment();
                    inputMethodManager.hideSoftInputFromWindow(sendtext.getWindowToken(),0);
                    sendtext.setHint("请输入要回复的内容");
                    sendtext.setText("");
                    Toast.makeText(TechDetailCommentActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<commonForTech> call, Throwable t) {

            }
        };
        call.enqueue(commonForTechCallback);
    }

    private void getFirstComment(){
        swipeRefreshLayout.setRefreshing(true);
        call = detailModel.getTechFirstComment(tdid);
        Callback<List<techFirstComment>> listCallback = new Callback<List<techFirstComment>>() {
            @Override
            public void onResponse(Call<List<techFirstComment>> call, Response<List<techFirstComment>> response) {
                firstCommentData = response.body();
                if( 0 != firstCommentData.size()){
                    techFirstCommentViewAdapter = new TechFirstCommentViewAdapter( TechDetailCommentActivity.this , firstCommentData );
                    firstRecyclerView.setAdapter(techFirstCommentViewAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    techFirstCommentViewAdapter = new TechFirstCommentViewAdapter( TechDetailCommentActivity.this , firstCommentData );
                    firstRecyclerView.setAdapter(techFirstCommentViewAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<techFirstComment>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }

    private void sendCommentAgain(){
        call = detailModel.sendCommentAgain(cid, uid , content);
        Callback<commonForTech> commonForTechCallback = new Callback<commonForTech>() {
            @Override
            public void onResponse(Call<commonForTech> call, Response<commonForTech> response) {
                commonForTech data = response.body();
                if( 1 == data.getSuccess() ){
                    getFirstComment();
                    inputMethodManager.hideSoftInputFromWindow(sendtext.getWindowToken(),0);
                    sendtext.setHint("请输入要回复的内容");
                    sendtext.setText("");
                    Toast.makeText(TechDetailCommentActivity.this, "回复"+toualiase+"成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<commonForTech> call, Throwable t) {

            }
        };
        call.enqueue(commonForTechCallback);
    }
}
