package cn.lrn517.techcomplatform.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.model.DetailModel;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAnswerActivity extends AppCompatActivity {

    private ImageView send;
    private TextView tdtitle;
    private EditText content_send;
    String tdid = "";
    String tdtitle_s ="";
    Toolbar toolbar;

    private DetailModel detailModel = new DetailModel();
    private Call call;
    private SharedPreferences sharedPreferences;

    Map<String, RequestBody> params = new HashMap<>();

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);
        initView();
        initEvent();
    }

    private void initView(){
        send = (ImageView) findViewById(R.id.add_answer_send);
        tdtitle = (TextView) findViewById(R.id.add_answer_tdtitle);
        content_send = (EditText) findViewById(R.id.add_answer_content_send);
        toolbar = findViewById(R.id.add_answer_toolbar);
        toolbar.setTitle("添加回答");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        tdtitle_s = bundle.getString("tdtitle");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        tdtitle.setText(tdtitle_s);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = content_send.getText().toString();
                params.put("tdid" , toRequestBody(tdid));
                params.put("reviewer" , toRequestBody(uid));
                params.put("content" ,toRequestBody(content));
                call = detailModel.sendAnswer(params);
                Callback<common> commonCallback = new Callback<common>() {
                    @Override
                    public void onResponse(Call<common> call, Response<common> response) {
                        common data = response.body();
                        if( 1 == data.getSuccess()){
                            Toast.makeText(AddAnswerActivity.this , "回答成功" ,Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<common> call, Throwable t) {

                    }
                };
                call.enqueue(commonCallback);
            }
        });
    }

    private static RequestBody toRequestBody(String t){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),t);
        return requestBody;
    }
}
