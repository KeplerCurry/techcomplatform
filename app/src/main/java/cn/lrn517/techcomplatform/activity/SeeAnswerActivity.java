package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.completeAnswerData;
import cn.lrn517.techcomplatform.bean.userForTechDetailState;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAnswerActivity extends AppCompatActivity {

    private TextView tdtitle,ualiase,content,ctime,commentcount,likecount,attentiontext;
    private Toolbar toolbar;
    private LinearLayout like,comment,attention;
    private ImageView likepic;
    private CircleImageView uphoto;
    Call call;
    private DetailModel detailModel = new DetailModel();
    private UserModel userModel = new UserModel();
    String tdtitle_s = "";
    String cid = "";
    private SharedPreferences sharedPreferences;
    private String uid;
    private String authorid;
    private int attentionUserFlag = 0;
    private int likeFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_answer);
        initView();
        initEvent();
    }

    private void initView(){
        //close = (ImageView) findViewById(R.id.see_answer_close);
        tdtitle = (TextView) findViewById(R.id.see_answer_tdtitle);
        ualiase = (TextView) findViewById(R.id.see_answer_ualiase);
        content = (TextView) findViewById(R.id.see_answer_content);
        ctime = (TextView) findViewById(R.id.see_answer_ctime);
        commentcount = (TextView) findViewById(R.id.see_answer_commentcount);
        toolbar = (Toolbar) findViewById(R.id.see_answer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        like = findViewById(R.id.see_answer_like);
        comment = findViewById(R.id.see_answer_comment);
        likepic = findViewById(R.id.see_answer_like_pic);
        likecount = findViewById(R.id.see_answer_like_count);
        uphoto = findViewById(R.id.see_answer_uphoto);
        attention = findViewById(R.id.see_answer_attention_layout);
        attentiontext = findViewById(R.id.see_answer_attention_text);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        cid = bundle.getString("cid");
        tdtitle_s = bundle.getString("tdtitle");
        Log.i("tdtitle" , tdtitle_s+"");
        tdtitle.setText(tdtitle_s);

        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        if( null == uid ){
            getAnswerByNull();
        }else{
            getAnswerByUid();
        }
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeAnswerActivity.this , CommentAnswerActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("cid" , cid);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == uid ){

                }
                else{
                    attention();
                }
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == uid ){

                }
                else{
                    like();
                }
            }
        });
    }

    private void getAnswerByNull(){
        call = detailModel.getCompleteAnswerData(cid);
        Callback<completeAnswerData> completeAnswerDataCallback = new Callback<completeAnswerData>() {
            @Override
            public void onResponse(Call<completeAnswerData> call, Response<completeAnswerData> response) {
                completeAnswerData data = response.body();
                if( 1 == data.getSuccess() ){
                    Glide.with(SeeAnswerActivity.this)
                            .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                            .dontAnimate()
                            .crossFade()
                            .into(uphoto);
                    ualiase.setText(data.getUaliase().toString());
                    content.setText(data.getContent().toString());
                    ctime.setText("发表于  "+data.getCtime().toString());
                    likecount.setText(data.getChit().toString());
                    commentcount.setText(data.getCommentcount().toString());
                }
            }

            @Override
            public void onFailure(Call<completeAnswerData> call, Throwable t) {

            }
        };
        call.enqueue(completeAnswerDataCallback);
    }

    private void getAnswerByUid(){
        call = detailModel.getCompleteAnswerData(cid);
        Callback<completeAnswerData> completeAnswerDataCallback = new Callback<completeAnswerData>() {
            @Override
            public void onResponse(Call<completeAnswerData> call, Response<completeAnswerData> response) {
                completeAnswerData data = response.body();
                if( 1 == data.getSuccess() ){
                    Glide.with(SeeAnswerActivity.this)
                            .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                            .dontAnimate()
                            .crossFade()
                            .into(uphoto);
                    ualiase.setText(data.getUaliase().toString());
                    content.setText(data.getContent().toString());
                    ctime.setText("发表于  "+data.getCtime().toString());
                    likecount.setText(data.getChit().toString());
                    commentcount.setText(data.getCommentcount().toString());
                    authorid = data.getUid().toString();
                    getUserForTheAnswer();
                }
            }

            @Override
            public void onFailure(Call<completeAnswerData> call, Throwable t) {

            }
        };
        call.enqueue(completeAnswerDataCallback);
    }

    private void getUserForTheAnswer(){
        call = detailModel.getUserForTheAnswer(uid,authorid,cid);
        Callback<userForTechDetailState> callback = new Callback<userForTechDetailState>() {
            @Override
            public void onResponse(Call<userForTechDetailState> call, Response<userForTechDetailState> response) {
                userForTechDetailState data = response.body();
                if( 1 == data.getUserflag()){
                    attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                    attentiontext.setText("已关注");
                    attentiontext.setTextColor(getResources().getColor(R.color.unset));
                    attentionUserFlag = 1;
                }else{
                    attentionUserFlag = 0;
                }
                if( 1 == data.getLikeflag()){
                    like.setBackground(getResources().getDrawable(R.drawable.tech_detail_like));
                    likepic.setImageResource(R.drawable.ic_like_fill);
                    likecount.setTextColor(getResources().getColor(R.color.white));
                    likeFlag = 1;
                }else{
                    likeFlag = 0;
                }
            }

            @Override
            public void onFailure(Call<userForTechDetailState> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void getAnswerData(){
        call = detailModel.getCompleteAnswerData(cid);
        Callback<completeAnswerData> completeAnswerDataCallback = new Callback<completeAnswerData>() {
            @Override
            public void onResponse(Call<completeAnswerData> call, Response<completeAnswerData> response) {
                completeAnswerData data = response.body();
                if( 1 == data.getSuccess() ){
                    ualiase.setText(data.getUaliase().toString());
                    content.setText(data.getContent().toString());
                    ctime.setText("发表于  "+data.getCtime().toString());
                    likecount.setText(data.getChit().toString());
                    commentcount.setText(data.getCommentcount().toString());
                }
            }

            @Override
            public void onFailure(Call<completeAnswerData> call, Throwable t) {

            }
        };
        call.enqueue(completeAnswerDataCallback);
    }

    private void attention(){
        call = userModel.common_like_collect_attention(attentionUserFlag,11,authorid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == attentionUserFlag ){
                        Toast.makeText(SeeAnswerActivity.this, "取消关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 0;
                        attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_0));
                        attentiontext.setText("关注");
                        attentiontext.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        Toast.makeText(SeeAnswerActivity.this, "关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 1;
                        attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                        attentiontext.setText("已关注");
                        attentiontext.setTextColor(getResources().getColor(R.color.unset));
                    }
                }
                else{
                    Toast.makeText(SeeAnswerActivity.this, "关注用户失败！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void like(){
        call = userModel.common_like_collect_attention(likeFlag,22,cid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == likeFlag ){
                        Toast.makeText(SeeAnswerActivity.this, "取消点赞成功！", Toast.LENGTH_SHORT).show();
                        likeFlag = 0;
                        int count = Integer.valueOf(likecount.getText().toString())-1;
                        like.setBackground(getResources().getDrawable(R.drawable.tech_detail_unlike));
                        likepic.setImageResource(R.drawable.ic_like_unfill);
                        likecount.setText(String.valueOf(count));
                        likecount.setTextColor(getResources().getColor(R.color.unset));
                    }else{
                        Toast.makeText(SeeAnswerActivity.this, "点赞成功！", Toast.LENGTH_SHORT).show();
                        likeFlag = 1;
                        int count = Integer.valueOf(likecount.getText().toString())+1;
                        like.setBackground(getResources().getDrawable(R.drawable.tech_detail_like));
                        likepic.setImageResource(R.drawable.ic_like_fill);
                        likecount.setText(String.valueOf(count));
                        likecount.setTextColor(getResources().getColor(R.color.white));
                    }
                }
                else{
                    Toast.makeText(SeeAnswerActivity.this, "点赞失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAnswerData();
    }
}
