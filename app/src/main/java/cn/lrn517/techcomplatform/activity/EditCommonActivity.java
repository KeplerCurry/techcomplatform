package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonGetDataFromEdit;
import cn.lrn517.techcomplatform.model.UserModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCommonActivity extends AppCompatActivity {

    private int state;
    private String id;

    private Toolbar toolbar;
    private ImageView send;

    private LinearLayout detail_layout;
    private CheckBox detail_yes,detail_no;
    private LinearLayout detail_price_layout;
    private EditText detail_price;
    private EditText detail_tdtitle,detail_tdcontent;

    private UserModel userModel = new UserModel();
    private Call call;

    private String tdtitle;
    private String tdcontent;
    private int isfree;
    private int price = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_common);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = findViewById(R.id.edit_common_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send = findViewById(R.id.edit_common_send);
        //state = 0
        detail_no = findViewById(R.id.edit_common_detail_check_no);
        detail_yes = findViewById(R.id.edit_common_detail_check_yes);
        detail_price_layout = findViewById(R.id.edit_common_detail_price_layout);
        detail_layout = findViewById(R.id.edit_common_detail_layout);
        detail_price = findViewById(R.id.edit_common_detail_price);
        detail_tdtitle = findViewById(R.id.edit_common_detail_tdtitle);
        detail_tdcontent = findViewById(R.id.edit_common_detail_tdcontent);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        state = bundle.getInt("state");
        id = bundle.getString("id");
        switch (state){
            case 0:
                toolbar.setTitle("技术贴内容修改");
                displayDetail();
                break;
            case 1:
                displayQuestion();
                break;
            case 2:
                displayAnswer();
                break;
            case 3:
                displayTPZDetail();
                break;
        }

        detail_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b ){
                    isfree = 1;
                    detail_no.setChecked(false);
                }else{
                    isfree = 0;
                    detail_no.setChecked(true);
                }
            }
        });

        detail_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b ){
                    isfree = 0;
                    detail_yes.setChecked(false);
                }else{
                    isfree = 1;
                    detail_yes.setChecked(true);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> params = new HashMap<>();
                switch (state){
                    case 0:
                        tdtitle = detail_tdtitle.getText().toString();
                        tdcontent = detail_tdcontent.getText().toString();
                        if( 0 == isfree ){
                            price = Integer.valueOf(detail_price.getText().toString());
                        }
                        RequestBody requestBody = RequestBody.create(MediaType.parse("text") , tdcontent);
                        call = userModel.editSendByState(state,id,tdtitle,requestBody,isfree,price,null,null,null);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
                Callback<common> callback = new Callback<common>() {
                    @Override
                    public void onResponse(Call<common> call, Response<common> response) {
                        common data = response.body();
                        if( 1 == data.getSuccess()){
                            Toast.makeText(EditCommonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditCommonActivity.this,TechDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tdid" , id);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(EditCommonActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<common> call, Throwable t) {

                    }
                };
                call.enqueue(callback);
            }
        });
    }

    private void displayDetail(){
        call = userModel.getDataByIdFromEdit(state,id);
        Callback<commonGetDataFromEdit> callback = new Callback<commonGetDataFromEdit>() {
            @Override
            public void onResponse(Call<commonGetDataFromEdit> call, Response<commonGetDataFromEdit> response) {
                commonGetDataFromEdit data = response.body();
                if( 1 == data.getIsfree()){
                    detail_yes.setChecked(true);
                    isfree = 1;
                }else{
                    detail_no.setChecked(true);
                    isfree = 0;
                    detail_price.setText(String.valueOf(data.getPrice()));
                }
                detail_tdtitle.setText(data.getTdtitle());
                detail_tdcontent.setText(data.getTdcontent());
            }
            @Override
            public void onFailure(Call<commonGetDataFromEdit> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void displayQuestion(){

    }

    private void displayAnswer(){

    }

    private void displayTPZDetail(){

    }
}
