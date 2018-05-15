package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaildata;
import cn.lrn517.techcomplatform.bean.userForTechDetailState;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseTechPersonZoneDetailActivity extends AppCompatActivity {

    private TextView tpzdtitle,ualiase,tpzname,tpzdcontent,tpzdfirsttime,uspecialline;
    private Toolbar toolbar;
    private LinearLayout tocomment,like,collect,attention;
    private CircleImageView uphoto;
    private ImageView likepic,collectpic;
    private TextView liketext,collecttext,attentiontext;
    Call call;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    private UserModel userModel = new UserModel();
    String tpzdid = "";
    String uid = "";
    String authoruid = "";

    private int attentionUserFlag = 0;
    private int likeFlag = 0;
    private int collectionFlag = 0;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_tech_person_zone_detail);
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        initView();
        initEvent();
    }

    private void initView(){
        tocomment = (LinearLayout) findViewById(R.id.browse_tech_person_zone_detail_tocomment);
        uphoto = findViewById(R.id.browse_tech_person_zone_detail_uphoto);
        toolbar = (Toolbar) findViewById(R.id.browse_tech_person_zone_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tpzdtitle = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzdtitle);
        ualiase = (TextView) findViewById(R.id.browse_tech_person_zone_detail_ualiase);
        tpzname = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzname);
        tpzdcontent = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzdcontent);
        tpzdfirsttime = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzdfirsttime);
        like = findViewById(R.id.browse_tech_person_zone_detail_like);
        attention = findViewById(R.id.browse_tech_person_zone_detail_attention_layout);
        collect = findViewById(R.id.browse_tech_person_zone_detail_collect);
        likepic = findViewById(R.id.browse_tech_person_zone_detail_like_pic);
        collectpic = findViewById(R.id.browse_tech_person_zone_detail_collect_pic);
        liketext = findViewById(R.id.browse_tech_person_zone_detail_like_count);
        collecttext = findViewById(R.id.browse_tech_person_zone_detail_collect_text);
        uspecialline = findViewById(R.id.browse_tech_person_zone_detail_uspecialline);
        attentiontext = findViewById(R.id.browse_tech_person_zone_detail_attention_text);
        Bundle bundle = getIntent().getExtras();
        tpzdid = bundle.getString("tpzdid");
    }

    private void initEvent(){

        if( null == uid ){
            getDetailByNull();
        }else{
            getDetailByUid();
        }

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

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null == uid ){

                }
                else{
                    collection();
                }
            }
        });

        uphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( authoruid == uid ){
                    Intent intent = new Intent(BrowseTechPersonZoneDetailActivity.this, MineInfoActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(BrowseTechPersonZoneDetailActivity.this, UserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uid" , authoruid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        tocomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseTechPersonZoneDetailActivity.this  , CommentTPZDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tpzdid" , tpzdid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getDetailByNull(){
        call = techPersonZoneModel.getTechPersonZoneDetailData(tpzdid);
        Callback<techpersonzonedetaildata> techpersonzonedetaildataCallback = new Callback<techpersonzonedetaildata>() {
            @Override
            public void onResponse(Call<techpersonzonedetaildata> call, Response<techpersonzonedetaildata> response) {
                techpersonzonedetaildata data = response.body();
                toolbar.setTitle(data.getTpzdtitle().toString());
                Glide.with(BrowseTechPersonZoneDetailActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                tpzdtitle.setText(data.getTpzdtitle().toString());
                ualiase.setText(data.getUaliase().toString());
                tpzdfirsttime.setText(data.getTpzdfirsttime().toString());
                tpzdcontent.setText(data.getTpzdcontent().toString());
                tpzname.setText(data.getTpzname());
                uspecialline.setText(data.getUspecialline());
                liketext.setText(data.getLike());
                authoruid = data.getUid();
            }

            @Override
            public void onFailure(Call<techpersonzonedetaildata> call, Throwable t) {

            }
        };
        call.enqueue(techpersonzonedetaildataCallback);
    }

    private void getDetailByUid(){
        call = techPersonZoneModel.getTechPersonZoneDetailData(tpzdid);
        Callback<techpersonzonedetaildata> techpersonzonedetaildataCallback = new Callback<techpersonzonedetaildata>() {
            @Override
            public void onResponse(Call<techpersonzonedetaildata> call, Response<techpersonzonedetaildata> response) {
                techpersonzonedetaildata data = response.body();
                toolbar.setTitle(data.getTpzdtitle().toString());
                Glide.with(BrowseTechPersonZoneDetailActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                tpzdtitle.setText(data.getTpzdtitle().toString());
                ualiase.setText(data.getUaliase().toString());
                tpzdfirsttime.setText(data.getTpzdfirsttime().toString());
                tpzdcontent.setText(data.getTpzdcontent().toString());
                tpzname.setText(data.getTpzname());
                uspecialline.setText(data.getUspecialline());
                authoruid = data.getUid();
                liketext.setText(data.getLike());
                getUserL_A_C_TPZ();
            }

            @Override
            public void onFailure(Call<techpersonzonedetaildata> call, Throwable t) {

            }
        };
        call.enqueue(techpersonzonedetaildataCallback);
    }

    private void getUserL_A_C_TPZ(){
        call = techPersonZoneModel.getUsera_l_c_TPZ(uid,authoruid,tpzdid);
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
                        Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "取消关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 0;
                        attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_0));
                        attentiontext.setText("关注");
                        attentiontext.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 1;
                        attention.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                        attentiontext.setText("已关注");
                        attentiontext.setTextColor(getResources().getColor(R.color.unset));
                    }
                }
                else{
                    Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "关注用户失败！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void like(){
        call = userModel.common_like_collect_attention(likeFlag,23,tpzdid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == likeFlag ){
                        Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "取消点赞成功！", Toast.LENGTH_SHORT).show();
                        likeFlag = 0;
                        int count = Integer.valueOf(liketext.getText().toString())-1;
                        like.setBackground(getResources().getDrawable(R.drawable.tech_detail_unlike));
                        likepic.setImageResource(R.drawable.ic_like_unfill);
                        liketext.setText(String.valueOf(count));
                        liketext.setTextColor(getResources().getColor(R.color.unset));
                    }else{
                        Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "点赞成功！", Toast.LENGTH_SHORT).show();
                        likeFlag = 1;
                        int count = Integer.valueOf(liketext.getText().toString())+1;
                        like.setBackground(getResources().getDrawable(R.drawable.tech_detail_like));
                        likepic.setImageResource(R.drawable.ic_like_fill);
                        liketext.setText(String.valueOf(count));
                        liketext.setTextColor(getResources().getColor(R.color.white));
                    }
                }
                else{
                    Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "点赞失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void collection(){
        call = userModel.common_like_collect_attention(collectionFlag,32,tpzdid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == collectionFlag ){
                        Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                        collectionFlag = 0;
                        collectpic.setImageResource(R.drawable.ic_collect_unfill);
                        collecttext.setText("收藏");
                        collecttext.setTextColor(getResources().getColor(R.color.unset));
                    }else{
                        Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                        collectionFlag = 1;
                        collectpic.setImageResource(R.drawable.ic_collect_fill);
                        collecttext.setText("已收藏");
                        collecttext.setTextColor(getResources().getColor(R.color.colorBasic));
                    }
                }
                else{
                    Toast.makeText(BrowseTechPersonZoneDetailActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }



}
