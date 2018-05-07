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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonGetDataFromEdit;
import cn.lrn517.techcomplatform.model.UserModel;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCommonActivity extends AppCompatActivity {

    private int state;
    private String id;

    private Toolbar toolbar;
    private ImageView send;
    //state = 0
    private ScrollView detail_layout;
    private CheckBox detail_yes,detail_no;
    private LinearLayout detail_price_layout;
    private EditText detail_price;
    private EditText detail_tdtitle,detail_tdcontent;
    //state = 1
    private ScrollView question_layout;
    private EditText question_tdtitle,question_tdcontent;
    //state = 2
    private ScrollView answer_layout;
    private TextView answer_tdtitle;
    private EditText answer_content;
    //state = 3
    private ScrollView tpzdetail_layout;
    private CheckBox tpzdetail_yes,tpzdetail_no;
    private LinearLayout tpzdetail_price_layout;
    private EditText tpzdetail_price;
    private EditText tpzdetail_tpzdtitle,tpzdetail_tpzdcontent;

    private UserModel userModel = new UserModel();
    private Call call;

    private String title;
    private String content;
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
        //state = 1
        question_layout = findViewById(R.id.edit_common_question_layout);
        question_tdtitle = findViewById(R.id.edit_common_question_tdtitle);
        question_tdcontent = findViewById(R.id.edit_common_question_tdcontent);
        //state = 2
        answer_layout = findViewById(R.id.edit_common_answer_layout);
        answer_tdtitle = findViewById(R.id.edit_common_answer_tdtitle);
        answer_content = findViewById(R.id.edit_common_answer_content);
        //state = 3
        tpzdetail_no = findViewById(R.id.edit_common_tpzdetail_check_no);
        tpzdetail_yes = findViewById(R.id.edit_common_tpzdetail_check_yes);
        tpzdetail_price_layout = findViewById(R.id.edit_common_tpzdetail_price_layout);
        tpzdetail_layout = findViewById(R.id.edit_common_tpzdetail_layout);
        tpzdetail_price = findViewById(R.id.edit_common_tpzdetail_price);
        tpzdetail_tpzdtitle = findViewById(R.id.edit_common_tpzdetail_tpzdtitle);
        tpzdetail_tpzdcontent = findViewById(R.id.edit_common_tpzdetail_tpzdcontent);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        state = bundle.getInt("state");
        id = bundle.getString("id");
        switch (state){
            case 0:
                toolbar.setTitle("技术贴内容修改");
                question_layout.setVisibility(View.GONE);
                answer_layout.setVisibility(View.GONE);
                tpzdetail_layout.setVisibility(View.GONE);
                displayDetail();
                break;
            case 1:
                detail_layout.setVisibility(View.GONE);
                answer_layout.setVisibility(View.GONE);
                tpzdetail_layout.setVisibility(View.GONE);
                displayQuestion();
                break;
            case 2:
                detail_layout.setVisibility(View.GONE);
                question_layout.setVisibility(View.GONE);
                tpzdetail_layout.setVisibility(View.GONE);
                displayAnswer();
                break;
            case 3:
                detail_layout.setVisibility(View.GONE);
                question_layout.setVisibility(View.GONE);
                answer_layout.setVisibility(View.GONE);
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
                Map<String,RequestBody> params = new HashMap<>();
                switch (state){
                    case 0:
                        title = detail_tdtitle.getText().toString();
                        content = detail_tdcontent.getText().toString();
                        if( 0 == isfree ){
                            price = Integer.valueOf(detail_price.getText().toString());
                        }
                        params.put("id" , toRequestBody(id));
                        params.put("state" , toRequestBody(String.valueOf(state)));
                        params.put("isfree" , toRequestBody(String.valueOf(isfree)));
                        params.put("price" , toRequestBody(String.valueOf(price)));
                        params.put("tdtitle" , toRequestBody(title));
                        params.put("tdcontent" , toRequestBody(content));
                        call = userModel.editSendByState(params);
                        break;
                    case 1:
                        title = question_tdtitle.getText().toString();
                        content = question_tdcontent.getText().toString();
                        params.put("id" , toRequestBody(id));
                        params.put("state" , toRequestBody(String.valueOf(state)));
                        params.put("tdtitle" , toRequestBody(title));
                        params.put("tdcontent", toRequestBody(content));
                        call = userModel.editSendByState(params);
                        break;
                    case 2:
                        content = answer_content.getText().toString();
                        params.put("id" , toRequestBody(id));
                        params.put("state" , toRequestBody(String.valueOf(state)));
                        params.put("content", toRequestBody(content));
                        call = userModel.editSendByState(params);
                        break;
                    case 3:
                        title = tpzdetail_tpzdtitle.getText().toString();
                        content = tpzdetail_tpzdcontent.getText().toString();
                        if( 0 == isfree ){
                            price = Integer.valueOf(tpzdetail_price.getText().toString());
                        }
                        params.put("id" , toRequestBody(id));
                        params.put("state" , toRequestBody(String.valueOf(state)));
                        params.put("isfree" , toRequestBody(String.valueOf(isfree)));
                        params.put("price" , toRequestBody(String.valueOf(price)));
                        params.put("tpzdtitle" , toRequestBody(title));
                        params.put("tpzdcontent" , toRequestBody(content));
                        call = userModel.editSendByState(params);
                        break;
                }
                Callback<common> callback = new Callback<common>() {
                    @Override
                    public void onResponse(Call<common> call, Response<common> response) {
                        common data = response.body();
                        if( 1 == data.getSuccess()){
                            if( 0 == state ){
                                Toast.makeText(EditCommonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditCommonActivity.this,TechDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tdid" , id);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }else if( 1 == state ){
                                Toast.makeText(EditCommonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditCommonActivity.this,AskDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tdid" , id);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }else if( 2 == state ){
                                Toast.makeText(EditCommonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditCommonActivity.this,SeeAnswerActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cid" , id);
                                bundle.putString("tdtitle" , answer_tdtitle.getText().toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(EditCommonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditCommonActivity.this,BrowseTechPersonZoneDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tpzdid" , id);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }

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
        call = userModel.getDataByIdFromEdit(state,id);
        Callback<commonGetDataFromEdit> callback = new Callback<commonGetDataFromEdit>() {
            @Override
            public void onResponse(Call<commonGetDataFromEdit> call, Response<commonGetDataFromEdit> response) {
                commonGetDataFromEdit data = response.body();
                question_tdtitle.setText(data.getTdtitle());
                question_tdcontent.setText(data.getTdcontent());
            }
            @Override
            public void onFailure(Call<commonGetDataFromEdit> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void displayAnswer(){
        call = userModel.getDataByIdFromEdit(state,id);
        Callback<commonGetDataFromEdit> callback = new Callback<commonGetDataFromEdit>() {
            @Override
            public void onResponse(Call<commonGetDataFromEdit> call, Response<commonGetDataFromEdit> response) {
                commonGetDataFromEdit data = response.body();
                answer_tdtitle.setText(data.getTdtitle());
                answer_content.setText(data.getContent());
            }
            @Override
            public void onFailure(Call<commonGetDataFromEdit> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void displayTPZDetail(){
        call = userModel.getDataByIdFromEdit(state,id);
        Callback<commonGetDataFromEdit> callback = new Callback<commonGetDataFromEdit>() {
            @Override
            public void onResponse(Call<commonGetDataFromEdit> call, Response<commonGetDataFromEdit> response) {
                commonGetDataFromEdit data = response.body();
                if( 1 == data.getIsfree()){
                    tpzdetail_yes.setChecked(true);
                    isfree = 1;
                }else{
                    tpzdetail_no.setChecked(true);
                    isfree = 0;
                    tpzdetail_price.setText(String.valueOf(data.getPrice()));
                }
                tpzdetail_tpzdtitle.setText(data.getTpzdtitle());
                tpzdetail_tpzdcontent.setText(data.getTpzdcontent());
            }
            @Override
            public void onFailure(Call<commonGetDataFromEdit> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    //将长文本数据转换成body类型传到map中进行上传
    private static RequestBody toRequestBody(String value){
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
}
