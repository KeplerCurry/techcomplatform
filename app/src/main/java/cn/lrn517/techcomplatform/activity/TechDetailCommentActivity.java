package cn.lrn517.techcomplatform.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ImageView close,send;
    private Call call;
    private TextView sendtext;
    private DetailModel detailModel = new DetailModel();
    List firstCommentData;
    List adddata;
    String tdid;
    String cid;
    String ctime;
    //测试数据
    String uid = "20180319155823";
    String aliase = "tchCST582你好好3";
    String content = "";

    TechFirstCommentViewAdapter techFirstCommentViewAdapter;
    private RecyclerView firstRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_detail_comment);
        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        initView();
        initEvent();
    }

    private void initView(){
        close = (ImageView) findViewById(R.id.tech_detail_comment_close);
        send = (ImageView) findViewById(R.id.tech_detail_comment_send);
        sendtext = (TextView) findViewById(R.id.tech_detail_comment_firsttext);
        firstRecyclerView = (RecyclerView) findViewById(R.id.tech_detail_comment_firstcommentView);
        linearLayoutManager = new LinearLayoutManager( TechDetailCommentActivity.this);
        firstRecyclerView.setLayoutManager(linearLayoutManager);
        //临时添加
        firstRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initEvent(){


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        call = detailModel.getTechFirstComment(tdid);
        Callback<List<techFirstComment>> listCallback = new Callback<List<techFirstComment>>() {
            @Override
            public void onResponse(Call<List<techFirstComment>> call, Response<List<techFirstComment>> response) {
                firstCommentData = response.body();
                if( 0 != firstCommentData.size()){
                    techFirstCommentViewAdapter = new TechFirstCommentViewAdapter( TechDetailCommentActivity.this , firstCommentData );
                    firstRecyclerView.setAdapter(techFirstCommentViewAdapter);
                }else{
                    techFirstCommentViewAdapter = new TechFirstCommentViewAdapter( TechDetailCommentActivity.this , firstCommentData );
                    firstRecyclerView.setAdapter(techFirstCommentViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<techFirstComment>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = sendtext.getText().toString();
                call = detailModel.sendFirstComment(tdid, uid , content);
                Callback<commonForTech> commonForTechCallback = new Callback<commonForTech>() {
                    @Override
                    public void onResponse(Call<commonForTech> call, Response<commonForTech> response) {
                        commonForTech data = response.body();
                        if( 1 == data.getSuccess() ){
                            ctime = data.getTime().toString();
                            cid = data.getCid().toString();
                            addFirstCommentData();
                        }
                    }

                    @Override
                    public void onFailure(Call<commonForTech> call, Throwable t) {

                    }
                };
                call.enqueue(commonForTechCallback);
            }
        });
    }

    private void addFirstCommentData(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                techFirstComment testdata = new techFirstComment();
                testdata.setChit("0");
                testdata.setUaliase( aliase);
                testdata.setCtime( ctime);
                testdata.setContent(content);
                testdata.setCid(cid);
                firstCommentData.add(testdata);
                techFirstCommentViewAdapter.notifyDataSetChanged();
            }
        });

    }
}
