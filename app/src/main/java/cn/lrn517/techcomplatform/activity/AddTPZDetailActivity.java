package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.commonForSendTPZ;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTPZDetailActivity extends AppCompatActivity {

    private EditText tpzdtitle,tpzdcontent,tpzdprice;
    private LinearLayout price_layout;
    private CheckBox isfree_select;
    private Button send;
    private Toolbar toolbar;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    Call call;
    int isfree_flag = 1;
    int price = 0;
    String title = "";
    String content = "";


    //测试数据
    String tpzid = "tpz-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tpzdetail);
        initView();
        initEvent();
    }

    private void initView(){
        price_layout = (LinearLayout) findViewById(R.id.add_tpzdetail_price_layout);
        isfree_select = (CheckBox) findViewById(R.id.add_tpzdetail_free_select);
        tpzdprice = (EditText) findViewById(R.id.add_tpzdetail_price);
        tpzdtitle = (EditText) findViewById(R.id.add_tpzdetail_tpzdtitle);
        tpzdcontent = (EditText) findViewById(R.id.add_tpzdetail_tpzdcontent);
        send = (Button) findViewById(R.id.add_tpzdetail_send);
        toolbar = (Toolbar) findViewById(R.id.add_tpzdetail_toolbar);
        toolbar.setTitle("发表专栏文章");
        setSupportActionBar(toolbar);
    }

    private void initEvent(){
        price_layout.setVisibility(View.GONE);

        isfree_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isfree_flag = 0;
                    Log.i("flag" , " ---"+isfree_flag);
                    price_layout.setVisibility(View.VISIBLE);
                }else{
                    isfree_flag = 1;
                    Log.i("flag" , " ---"+isfree_flag);
                    price_layout.setVisibility(View.GONE);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTechPersonZoneDetail();
            }
        });
    }

    private void sendTechPersonZoneDetail(){
        title = tpzdtitle.getText().toString();
        content = tpzdcontent.getText().toString();
        if( !"".equals(tpzdprice.getText().toString())){
            price = Integer.valueOf(tpzdprice.getText().toString());
        }
        call = techPersonZoneModel.sendTechPersonZoneDetail(tpzid , title , content , isfree_flag , price);
        Callback<commonForSendTPZ> commonForSendTPZCallback = new Callback<commonForSendTPZ>() {
            @Override
            public void onResponse(Call<commonForSendTPZ> call, Response<commonForSendTPZ> response) {
                commonForSendTPZ data = response.body();
                if( 1 == data.getSuccess()){
                    Toast.makeText(AddTPZDetailActivity.this, "发表成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTPZDetailActivity.this,BrowseTechPersonZoneDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tpzdid" , data.getTpzdid());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AddTPZDetailActivity.this, "失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<commonForSendTPZ> call, Throwable t) {

            }
        };
        call.enqueue(commonForSendTPZCallback);
    }


}
