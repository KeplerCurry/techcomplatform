package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

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

    //测试数据

    String uid = "20180319155823";
    String aliase = "tchCST582你好好3";
    String content = "";

    TechPersonZoneFirstCommentAdapter techPersonZoneFirstCommentAdapter;
    private RecyclerView firstRecyclerView;
    private LinearLayoutManager linearLayoutManager;

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
        toolbar = (Toolbar) findViewById(R.id.comment_tpzdetail_toolbar);
        toolbar.setTitle("评论列表");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black);
        send = (ImageView) findViewById(R.id.comment_tpzdetail_send);
        sendtext = (EditText) findViewById(R.id.comment_tpzdetail_content);
        firstRecyclerView = (RecyclerView) findViewById(R.id.comment_tpzdetail_recyclerview);
        linearLayoutManager = new LinearLayoutManager(CommentTPZDetailActivity.this);
        firstRecyclerView.setLayoutManager(linearLayoutManager);
        firstRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initEvent(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        call = techPersonZoneModel.getTechPersonZoneFirstCommentData(tpzdid);
        Callback<List<tpzFirstComment>> listCallback = new Callback<List<tpzFirstComment>>() {
            @Override
            public void onResponse(Call<List<tpzFirstComment>> call, Response<List<tpzFirstComment>> response) {
                firstCommentData = response.body();
                if( 0 != firstCommentData.size()){
                    techPersonZoneFirstCommentAdapter = new TechPersonZoneFirstCommentAdapter( CommentTPZDetailActivity.this , firstCommentData);
                    firstRecyclerView.setAdapter(techPersonZoneFirstCommentAdapter);
                }else{
                    techPersonZoneFirstCommentAdapter = new TechPersonZoneFirstCommentAdapter( CommentTPZDetailActivity.this , firstCommentData);
                    firstRecyclerView.setAdapter(techPersonZoneFirstCommentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<tpzFirstComment>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = sendtext.getText().toString();
                call = techPersonZoneModel.sendTechPersonZoneFirstComment(tpzdid,uid,content);
                Callback<commonForTPZ> commonForTPZCallback = new Callback<commonForTPZ>() {
                    @Override
                    public void onResponse(Call<commonForTPZ> call, Response<commonForTPZ> response) {
                        commonForTPZ data = response.body();
                        if( 1 == data.getSuccess()){
                            tpzctime = data.getTime().toString();
                            tpzcid = data.getTpzcid().toString();
                            addFirstCommentData();
                        }
                    }

                    @Override
                    public void onFailure(Call<commonForTPZ> call, Throwable t) {

                    }
                };
                call.enqueue(commonForTPZCallback);
            }
        });
    }

    private void addFirstCommentData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tpzFirstComment testdata = new tpzFirstComment();
                testdata.setUaliase(aliase);
                testdata.setContent(content);
                testdata.setTpzcid(tpzcid);
                testdata.setTpzctime(tpzctime);
                firstCommentData.add(testdata);
                techPersonZoneFirstCommentAdapter.notifyDataSetChanged();
            }
        });
    }
}
