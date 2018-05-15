package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import cn.lrn517.techcomplatform.bean.techDetailData;
import cn.lrn517.techcomplatform.bean.userForTechDetailState;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechDetailActivity extends AppCompatActivity {

    private TextView ualiase,tdtitle,tname,tdcontent,tdfirsttime,uspecialline;
    private Toolbar toolbar;
    private CircleImageView uphoto;
    private LinearLayout like,attention;
    private LinearLayout collect,comment;
    private ImageView likepic,collectpic;
    private TextView liketext,collecttext,attentiontext;
    private String tdid;
    private Call call;
    private DetailModel detailModel = new DetailModel();
    private UserModel userModel = new UserModel();

    private String uid;
    private String authoruid;
    private SharedPreferences sharedPreferences;

    private int attentionUserFlag = 0;
    private int likeFlag = 0;
    private int collectionFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_detail);
        initView();
        initEvent();
    }

    private void initView(){
        ualiase = (TextView) findViewById(R.id.tech_detail_ualiase);
        attention = findViewById(R.id.tech_detail_attention_layout);
        attentiontext = findViewById(R.id.tech_detail_attention_text);
        uphoto = findViewById(R.id.tech_detail_uphoto);
        tdcontent = (TextView) findViewById(R.id.tech_detail_tdcontent);
        tdfirsttime = (TextView) findViewById(R.id.tech_detail_tdfirsttime);
        tname = (TextView) findViewById(R.id.tech_detail_tname);
        tdtitle = (TextView) findViewById(R.id.tech_detail_tdtitle);
        like = (LinearLayout) findViewById(R.id.tech_detail_like);
        collect = (LinearLayout) findViewById(R.id.tech_detail_collect);
        comment = (LinearLayout) findViewById(R.id.tech_detail_comment);
        likepic = findViewById(R.id.tech_detail_like_pic);
        liketext = findViewById(R.id.tech_detail_like_count);
        collectpic = findViewById(R.id.tech_detail_collect_pic);
        collecttext = findViewById(R.id.tech_detail_collect_text);
        uspecialline = findViewById(R.id.tech_detail_uspecialline);
        toolbar = (Toolbar) findViewById(R.id.tech_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        if( null == uid ){
            getDetailByNull();
        }else{
            getDetailByUid();
        }
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TechDetailActivity.this, TechDetailCommentActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("tdid" , tdid);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == uid ){
                    Toast.makeText(TechDetailActivity.this, "请登录后操作！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TechDetailActivity.this,LoginAndRegisterActivity.class);
                    startActivity(intent);
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
                    Toast.makeText(TechDetailActivity.this, "请登录后操作！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TechDetailActivity.this,LoginAndRegisterActivity.class);
                    startActivity(intent);
                }
                else{
                    like();
                }
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == uid ){
                    Toast.makeText(TechDetailActivity.this, "请登录后操作！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TechDetailActivity.this,LoginAndRegisterActivity.class);
                    startActivity(intent);
                }
                else{
                    collection();
                }
            }
        });

        uphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( authoruid.equals(uid) ){
                    Intent intent = new Intent(TechDetailActivity.this, MineInfoActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TechDetailActivity.this, UserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uid" , authoruid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void getDetailByNull(){
        call = detailModel.getTechDetailData(tdid);
        Callback<techDetailData> techDetailDataCallback = new Callback<techDetailData>() {
            @Override
            public void onResponse(Call<techDetailData> call, Response<techDetailData> response) {
                techDetailData data = response.body();
                Glide.with(TechDetailActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                toolbar.setTitle(data.getTdtitle());
                ualiase.setText(data.getUaliase());
                tdcontent.setText(data.getTdcontent());
                tdfirsttime.setText(data.getTdfirsttime());
                tname.setText(data.getTname());
                tdtitle.setText(data.getTdtitle());
                liketext.setText(data.getLike());
                authoruid = data.getUid();
                uspecialline.setText(data.getUspecialline());
            }

            @Override
            public void onFailure(Call<techDetailData> call, Throwable t) {

            }
        };
        call.enqueue(techDetailDataCallback);
    }

    private void getDetailByUid(){
        call = detailModel.getTechDetailData(tdid);
        Callback<techDetailData> techDetailDataCallback = new Callback<techDetailData>() {
            @Override
            public void onResponse(Call<techDetailData> call, Response<techDetailData> response) {
                techDetailData data = response.body();
                Glide.with(TechDetailActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                toolbar.setTitle(data.getTdtitle());
                ualiase.setText(data.getUaliase());
                tdcontent.setText(data.getTdcontent());
                tdfirsttime.setText(data.getTdfirsttime());
                uspecialline.setText(data.getUspecialline());
                tname.setText(data.getTname());
                tdtitle.setText(data.getTdtitle());
                liketext.setText(data.getLike());
                authoruid = data.getUid();
                getUserL_A_C();
            }

            @Override
            public void onFailure(Call<techDetailData> call, Throwable t) {

            }
        };
        call.enqueue(techDetailDataCallback);
    }

    private void getUserL_A_C(){
        call = detailModel.getUsera_l_c(uid,authoruid,tdid);
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
                    liketext.setTextColor(getResources().getColor(R.color.white));
                    likeFlag = 1;
                }else{
                    likeFlag = 0;
                }
                if( 1 == data.getCollectflag() ){
                    collectpic.setImageResource(R.drawable.ic_collect_fill);
                    collecttext.setText("已收藏");
                    collecttext.setTextColor(getResources().getColor(R.color.colorBasic));
                    collectionFlag = 1;
                }else{
                    collectionFlag = 0;
                }
            }

            @Override
            public void onFailure(Call<userForTechDetailState> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void attention(){
        call = userModel.common_like_collect_attention(attentionUserFlag,11,authoruid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == attentionUserFlag ){
                        Toast.makeText(TechDetailActivity.this, "取消关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 0;
                        attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_0));
                        attentiontext.setText("关注");
                        attentiontext.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        Toast.makeText(TechDetailActivity.this, "关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 1;
                        attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                        attentiontext.setText("已关注");
                        attentiontext.setTextColor(getResources().getColor(R.color.unset));
                    }
                }
                else{
                    Toast.makeText(TechDetailActivity.this, "关注用户失败！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void like(){
        call = userModel.common_like_collect_attention(likeFlag,21,tdid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == likeFlag ){
                        Toast.makeText(TechDetailActivity.this, "取消点赞成功！", Toast.LENGTH_SHORT).show();
                        likeFlag = 0;
                        int count = Integer.valueOf(liketext.getText().toString())-1;
                        like.setBackground(getResources().getDrawable(R.drawable.tech_detail_unlike));
                        likepic.setImageResource(R.drawable.ic_like_unfill);
                        liketext.setText(String.valueOf(count));
                        liketext.setTextColor(getResources().getColor(R.color.unset));
                    }else{
                        Toast.makeText(TechDetailActivity.this, "点赞成功！", Toast.LENGTH_SHORT).show();
                        likeFlag = 1;
                        int count = Integer.valueOf(liketext.getText().toString())+1;
                        like.setBackground(getResources().getDrawable(R.drawable.tech_detail_like));
                        likepic.setImageResource(R.drawable.ic_like_fill);
                        liketext.setText(String.valueOf(count));
                        liketext.setTextColor(getResources().getColor(R.color.white));
                    }
                }
                else{
                    Toast.makeText(TechDetailActivity.this, "点赞失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void collection(){
        call = userModel.common_like_collect_attention(collectionFlag,31,tdid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == collectionFlag ){
                        Toast.makeText(TechDetailActivity.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                        collectionFlag = 0;
                        collectpic.setImageResource(R.drawable.ic_collect_unfill);
                        collecttext.setText("收藏");
                        collecttext.setTextColor(getResources().getColor(R.color.unset));
                    }else{
                        Toast.makeText(TechDetailActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                        collectionFlag = 1;
                        collectpic.setImageResource(R.drawable.ic_collect_fill);
                        collecttext.setText("已收藏");
                        collecttext.setTextColor(getResources().getColor(R.color.colorBasic));
                    }
                }
                else{
                    Toast.makeText(TechDetailActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
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
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
    }
}
