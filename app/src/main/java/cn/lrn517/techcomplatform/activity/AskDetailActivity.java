package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.AskAnswerDataViewAdapter;
import cn.lrn517.techcomplatform.bean.askData;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.firstAnswerData;
import cn.lrn517.techcomplatform.listener.MoreRecyclerOnScrollListener;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskDetailActivity extends AppCompatActivity {

    private TextView tname,tdtitle,tdcontent,commentcount,attentionquestiontext;
    private Call call;
    private DetailModel detailModel = new DetailModel();
    private UserModel userModel = new UserModel();
    private LinearLayout addAnswer,attentionquestion;
    String tdid = "";
    String tdtitle_s = "";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    AskAnswerDataViewAdapter askAnswerDataViewAdapter;
    private Toolbar toolbar;
    List data;
    private SharedPreferences sharedPreferences;
    private String uid;
    private int attentionflag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_detail);
        initView();
        initEvent();
    }

    private void initView(){
        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        attentionquestion = findViewById(R.id.ask_detail_attention_layout);
        attentionquestiontext = findViewById(R.id.ask_detail_attention_text);
        tname = (TextView) findViewById(R.id.ask_detail_tname);
        tdtitle = (TextView) findViewById(R.id.ask_detail_tdtitle);
        tdcontent = (TextView) findViewById(R.id.ask_detail_tdcontent);
        commentcount = (TextView) findViewById(R.id.ask_detail_commentcount);
        addAnswer = (LinearLayout) findViewById(R.id.ask_detail_addAnswer);
        recyclerView = (RecyclerView) findViewById(R.id.ask_detail_commentRecyclerView);
        linearLayoutManager = new LinearLayoutManager( AskDetailActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //临时添加
        recyclerView.setNestedScrollingEnabled(false);
        toolbar = (Toolbar) findViewById(R.id.ask_detail_toolbar);
        toolbar.setTitle("查看问题");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent(){

        if( null == uid ){
            getAskByNull();
        }else{
            getAskByUid();
        }

        getFirstAnswerData();

        attentionquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == uid ){

                }
                else{
                    attention();
                }
            }
        });

        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskDetailActivity.this,AddAnswerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tdid" , tdid);
                bundle.putString("tdtitle" , tdtitle_s);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getFirstAnswerData(){
        call = detailModel.getFirstAnswerData(tdid);
        Callback<List<firstAnswerData>> listCallback = new Callback<List<firstAnswerData>>() {
            @Override
            public void onResponse(Call<List<firstAnswerData>> call, Response<List<firstAnswerData>> response) {
                data = response.body();
                askAnswerDataViewAdapter = new AskAnswerDataViewAdapter(AskDetailActivity.this ,data,tdtitle_s);
                recyclerView.setAdapter(askAnswerDataViewAdapter);

            }

            @Override
            public void onFailure(Call<List<firstAnswerData>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }

    private void getAskByNull(){
        Log.i("test" , "进入null");
        call = detailModel.getAskData(tdid);
        Callback<askData> askDataCallback = new Callback<askData>() {
            @Override
            public void onResponse(Call<askData> call, Response<askData> response) {
                askData data = response.body();
                tname.setText(data.getTname().toString());
                tdtitle.setText(data.getTdtitle().toString());
                tdcontent.setText(data.getTdcontent().toString());
                tdtitle_s = data.getTdtitle().toString();
                commentcount.setText(data.getCommentcount().toString());
            }

            @Override
            public void onFailure(Call<askData> call, Throwable t) {

            }
        };
        call.enqueue(askDataCallback);
    }

    private void getAskByUid(){
        Log.i("test" , "进入UID");
        call = detailModel.getAskData(tdid);
        Callback<askData> askDataCallback = new Callback<askData>() {
            @Override
            public void onResponse(Call<askData> call, Response<askData> response) {
                askData data = response.body();
                tname.setText(data.getTname().toString());
                tdtitle.setText(data.getTdtitle().toString());
                tdcontent.setText(data.getTdcontent().toString());
                tdtitle_s = data.getTdtitle().toString();
                commentcount.setText(data.getCommentcount().toString());
                getUserAttention();
            }

            @Override
            public void onFailure(Call<askData> call, Throwable t) {

            }
        };
        call.enqueue(askDataCallback);
    }

    private void getUserAttention(){
        call = detailModel.getUserAttentionQuestion(uid,tdid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess() ){
                    attentionflag = 1;
                    Log.i("attentionflag" ,": "+attentionflag);
                    attentionquestion.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                    attentionquestiontext.setText("已关注");
                    attentionquestiontext.setTextColor(getResources().getColor(R.color.unset));
                }else{
                    attentionflag = 0;
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {
                Toast.makeText(AskDetailActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        };
        call.enqueue(commonCallback);
    }

    private void attention(){
        call = userModel.common_like_collect_attention(attentionflag,12,tdid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == attentionflag ){
                        Toast.makeText(AskDetailActivity.this, "取消关注问题成功！", Toast.LENGTH_SHORT).show();
                        attentionflag = 0;
                        attentionquestion.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_0));
                        attentionquestiontext.setText("关注");
                        attentionquestiontext.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        Toast.makeText(AskDetailActivity.this, "关注问题成功！", Toast.LENGTH_SHORT).show();
                        attentionflag = 1;
                        attentionquestion.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                        attentionquestiontext.setText("已关注");
                        attentionquestiontext.setTextColor(getResources().getColor(R.color.unset));
                    }
                }
                else{
                    Toast.makeText(AskDetailActivity.this, "关注问题失败！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity" , "AskDetailActivity is onResume");
        getFirstAnswerData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Activity" , "AskDetailActivity is onStart");
    }
}
