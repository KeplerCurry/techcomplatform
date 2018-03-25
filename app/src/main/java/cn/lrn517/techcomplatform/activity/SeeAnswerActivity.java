package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.completeAnswerData;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAnswerActivity extends AppCompatActivity {

    private ImageView close;
    private TextView tdtitle,ualiase,content,ctime,chit,commentcount;
    private Button writeanswer;
    Call call;
    private DetailModel detailModel = new DetailModel();
    String tdtitle_s = "";
    String cid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_answer);
        initView();
        initEvent();
    }

    private void initView(){
        close = (ImageView) findViewById(R.id.see_answer_close);
        tdtitle = (TextView) findViewById(R.id.see_answer_tdtitle);
        ualiase = (TextView) findViewById(R.id.see_answer_ualiase);
        content = (TextView) findViewById(R.id.see_answer_content);
        ctime = (TextView) findViewById(R.id.see_answer_ctime);
        chit = (TextView) findViewById(R.id.see_answer_chit);
        writeanswer = (Button) findViewById(R.id.see_answer_writeanswer);
        commentcount = (TextView) findViewById(R.id.see_answer_commentcount);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        cid = bundle.getString("cid");
        tdtitle_s = bundle.getString("tdtitle");
        Log.i("tdtitle" , tdtitle_s+"");
        tdtitle.setText(tdtitle_s);

        call = detailModel.getCompleteAnswerData(cid);
        Callback<completeAnswerData> completeAnswerDataCallback = new Callback<completeAnswerData>() {
            @Override
            public void onResponse(Call<completeAnswerData> call, Response<completeAnswerData> response) {
                completeAnswerData data = response.body();
                if( 1 == data.getSuccess() ){
                    ualiase.setText(data.getUaliase().toString());
                    content.setText(data.getContent().toString());
                    ctime.setText(data.getCtime().toString());
                    chit.setText(data.getChit().toString());
                    commentcount.setText(data.getCommentcount().toString());
                }
            }

            @Override
            public void onFailure(Call<completeAnswerData> call, Throwable t) {

            }
        };
        call.enqueue(completeAnswerDataCallback);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        commentcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeAnswerActivity.this , CommentAnswerActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("cid" , cid);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
}
