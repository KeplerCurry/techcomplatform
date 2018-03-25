package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.AskAnswerDataViewAdapter;
import cn.lrn517.techcomplatform.bean.askData;
import cn.lrn517.techcomplatform.bean.firstAnswerData;
import cn.lrn517.techcomplatform.listener.MoreRecyclerOnScrollListener;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskDetailActivity extends AppCompatActivity {

    private TextView tname,tdtitle,tdcontent,commentcount;
    private Call call;
    private DetailModel detailModel = new DetailModel();
    private LinearLayout addAnswer;
    String tdid = "";
    String tdtitle_s = "";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    AskAnswerDataViewAdapter askAnswerDataViewAdapter;
    List data;

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
    }

    private void initEvent(){

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

        getFirstAnswerData();

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
