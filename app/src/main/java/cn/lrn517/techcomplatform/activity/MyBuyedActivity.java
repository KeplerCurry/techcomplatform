package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyBuyedViewAdapter;
import cn.lrn517.techcomplatform.bean.userBuyedData;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBuyedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Call call;
    private UserModel userModel = new UserModel();

    String uid = "20180319124601";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buyed);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_buyed_toolbar);
        toolbar.setTitle("购买记录");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black);
        recyclerView = (RecyclerView) findViewById(R.id.my_buyed_recyclerview);
        linearLayoutManager = new LinearLayoutManager(MyBuyedActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = userModel.getUserBuyedData(uid);
        Callback<List<userBuyedData>> listCallback = new Callback<List<userBuyedData>>() {
            @Override
            public void onResponse(Call<List<userBuyedData>> call, Response<List<userBuyedData>> response) {
                List data = response.body();
                MyBuyedViewAdapter buyedViewAdapter = new MyBuyedViewAdapter( MyBuyedActivity.this , data);
                recyclerView.setAdapter(buyedViewAdapter);
            }

            @Override
            public void onFailure(Call<List<userBuyedData>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }
}
