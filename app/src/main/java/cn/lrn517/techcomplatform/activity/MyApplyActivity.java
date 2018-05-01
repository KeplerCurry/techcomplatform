package cn.lrn517.techcomplatform.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyApplyViewAdapter;
import cn.lrn517.techcomplatform.bean.userapplyfordata;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Call call;
    private UserModel userModel = new UserModel();
    SharedPreferences sharedPreferences;

    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_apply_toolbar);
        toolbar.setTitle("查看申请列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.my_apply_recyclerview);
        linearLayoutManager = new LinearLayoutManager( MyApplyActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initEvent(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        call = userModel.getUserApplyFor(uid);
        Callback<List<userapplyfordata>> listCallback = new Callback<List<userapplyfordata>>() {
            @Override
            public void onResponse(Call<List<userapplyfordata>> call, Response<List<userapplyfordata>> response) {
                List data = response.body();
                if( 0 != data.size() ){
                    MyApplyViewAdapter myApplyViewAdapter = new MyApplyViewAdapter(MyApplyActivity.this , data);
                    recyclerView.setAdapter(myApplyViewAdapter);
                }else{

                }

            }

            @Override
            public void onFailure(Call<List<userapplyfordata>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);


    }
}
