package cn.lrn517.techcomplatform.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    private ImageView send;
    private EditText tdtitle,tdcontent;
    private AddModel addModel = new AddModel();
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    Call call;

    int isfree;
    double price;
    String tid;
    String tdtitle_s;
    String tdcontent_s;

    //测试数据
    String tuid= null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_technology_detail_finish);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = findViewById(R.id.add_technology_detail_finish_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send = (ImageView) findViewById(R.id.add_technology_detail_finish_send);
        tdtitle = (EditText) findViewById(R.id.add_technology_detail_finish_tdtitle);
        tdcontent = (EditText) findViewById(R.id.add_technology_detail_finish_tdcontent);
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        tuid = sharedPreferences.getString("uid" , null );
    }

    private void initEvent(){
        getDataForFirst();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tdcontent_s = tdcontent.getText().toString();
                tdtitle_s = tdtitle.getText().toString();
                if( "".equals(tdcontent_s) || "".equals(tdtitle_s)){
                    Toast.makeText(AddTechnologyDetailFinishActivity.this, "请将标题或内容填写完整", Toast.LENGTH_SHORT).show();
                }else{
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

            }
        });
    }

    private void getDataForFirst(){
        Bundle bundle = getIntent().getExtras();
        isfree = bundle.getInt("isfree");
        price = bundle.getDouble("price",0.0);
        tid = bundle.getString("tid");
    }

    private void sendTechnologyDetail(){
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
                    AddTechnologyDetailFirstActivity.activity.finish();
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
