package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAnswerActivity extends AppCompatActivity {

    private ImageView close,send;
    private TextView tdtitle;
    private EditText content_send;
    String tdid = "";
    String tdtitle_s ="";

    private DetailModel detailModel = new DetailModel();
    private Call call;

    //测试数据
    String uid = "20180319155823";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);
        initView();
        initEvent();
    }

    private void initView(){
        close = (ImageView) findViewById(R.id.add_answer_close);
        send = (ImageView) findViewById(R.id.add_answer_send);
        tdtitle = (TextView) findViewById(R.id.add_answer_tdtitle);
        content_send = (EditText) findViewById(R.id.add_answer_content_send);
    }

    private void initEvent(){

        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        tdtitle_s = bundle.getString("tdtitle");
        tdtitle.setText(tdtitle_s);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = content_send.getText().toString();
                call = detailModel.sendAnswer(tdid , uid , content);
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
}
