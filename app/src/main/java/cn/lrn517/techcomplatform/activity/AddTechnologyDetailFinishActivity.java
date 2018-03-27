package cn.lrn517.techcomplatform.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.addTechDetailResult;
import cn.lrn517.techcomplatform.model.AddModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTechnologyDetailFinishActivity extends AppCompatActivity {

    private ImageView back, send;
    private EditText tdtitle,tdcontent;
    private AddModel addModel = new AddModel();
    Call call;

    int isfree;
    double price;
    String tid;
    String tdtitle_s;
    String tdcontent_s;

    //测试数据
    String tuid = "20180319155823";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_technology_detail_finish);
        initView();
        initEvent();
    }

    private void initView(){
        back = (ImageView) findViewById(R.id.add_technology_detail_finish_close);
        send = (ImageView) findViewById(R.id.add_technology_detail_finish_send);
        tdtitle = (EditText) findViewById(R.id.add_technology_detail_finish_tdtitle);
        tdcontent = (EditText) findViewById(R.id.add_technology_detail_finish_tdcontent);
    }

    private void initEvent(){
        getDataForFirst();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddTechnologyDetailFinishActivity.this);
                dialog.setTitle("确认发布？");
                dialog.setPositiveButton("确认发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendTechnologyDetail();
                    }
                });
                dialog.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    private void getDataForFirst(){
        Bundle bundle = getIntent().getExtras();
        isfree = bundle.getInt("isfree");
        price = bundle.getDouble("price");
        tid = bundle.getString("tid");
    }

    private void sendTechnologyDetail(){
        tdcontent_s = tdcontent.getText().toString();
        tdtitle_s = tdtitle.getText().toString();
        call =  addModel.sendTechnologyDetail(tuid , tdtitle_s , tdcontent_s , tid , isfree , price);
        Callback<addTechDetailResult> addTechDetailResultCallback = new Callback<addTechDetailResult>() {
            @Override
            public void onResponse(Call<addTechDetailResult> call, Response<addTechDetailResult> response) {
                addTechDetailResult data = response.body();
                if( 1 == data.getSuccess() ){
                    Intent intent = new Intent(AddTechnologyDetailFinishActivity.this , TechDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tdid" , data.getTdid().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(AddTechnologyDetailFinishActivity.this, "发表成功！", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<addTechDetailResult> call, Throwable t) {

            }
        };
        call.enqueue(addTechDetailResultCallback);
    }
}
