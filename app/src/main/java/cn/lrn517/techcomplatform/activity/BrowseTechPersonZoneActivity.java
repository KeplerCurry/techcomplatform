package cn.lrn517.techcomplatform.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mob.tools.gui.MobViewPager;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.TechPersonZoneDetailListAdapter;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaillist;
import cn.lrn517.techcomplatform.bean.techpersonzoneuserinfo;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseTechPersonZoneActivity extends AppCompatActivity {

    private TextView tpzname,tname,ualiase,detailcount,attentionTPZText;
    private LinearLayout attentionTPZLayout;
    private LinearLayoutManager linearLayoutManager;
    private CircleImageView uphoto;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    private SharedPreferences sharedPreferences;
    String tpzid = "";
    String uid = null;
    String minetpzid = null;

    private int attentionTPZFlag = 0;
    private Call call;
    private DetailModel detailModel = new DetailModel();
    private UserModel userModel = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_tech_person_zone);
        initView();
        initEvent();
    }

    private void initView(){
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        minetpzid = sharedPreferences.getString("tpzid" , null);
        Log.i("minetpzid" , ""+minetpzid);
        Bundle bundle = getIntent().getExtras();
        tpzid = bundle.getString("tpzid");
        Log.i("tpzid" , ""+tpzid);
        toolbar = (Toolbar) findViewById(R.id.browse_tech_person_zone_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tpzname = (TextView) findViewById(R.id.browse_tech_person_zone_tpzname);
        attentionTPZLayout = findViewById(R.id.browse_tech_person_zone_attention_layout);
        attentionTPZText = findViewById(R.id.browse_tech_person_zone_attention_text);
        tname = (TextView) findViewById(R.id.browse_tech_person_zone_tname);
        ualiase = (TextView) findViewById(R.id.browse_tech_person_zone_ualiase);
        detailcount = (TextView) findViewById(R.id.browse_tech_person_zone_detailcount);
        uphoto = findViewById(R.id.browse_tech_person_zone_uphoto);
        linearLayoutManager = new LinearLayoutManager(BrowseTechPersonZoneActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.browse_tech_person_zone_listview);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        if( null != uid ){
            if( null != minetpzid){
                if( minetpzid.equals(tpzid) ){
                    attentionTPZLayout.setVisibility(View.GONE);
                    getUserInfo();
                    getDetailList();
                }else{
                    getUserInfo();
                    getAttentionState();
                    getDetailList();
                }
            }else{
                getUserInfo();
                getAttentionState();
                getDetailList();
            }
        }else{
            getUserInfo();
            getDetailList();
        }

        attentionTPZLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attention();
            }
        });

    }

    private void getAttentionState(){
        call = detailModel.getUserAttentionTPZ(uid,tpzid);
        Callback<common> callback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    attentionTPZLayout.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                    attentionTPZText.setText("已关注");
                    attentionTPZText.setTextColor(getResources().getColor(R.color.unset));
                    attentionTPZFlag = 1;
                }else{
                    attentionTPZFlag = 0;
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void attention(){
        call = userModel.common_like_collect_attention(attentionTPZFlag,13,tpzid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == attentionTPZFlag ){
                        Toast.makeText(BrowseTechPersonZoneActivity.this, "”取消关注专栏成功！", Toast.LENGTH_SHORT).show();
                        attentionTPZFlag = 0;
                        attentionTPZLayout.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_0));
                        attentionTPZText.setText("关注");
                        attentionTPZText.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        Toast.makeText(BrowseTechPersonZoneActivity.this, "关注专栏成功！", Toast.LENGTH_SHORT).show();
                        attentionTPZFlag = 1;
                        attentionTPZLayout.setBackground(getResources().getDrawable(R.drawable.tech_detail_attention_1));
                        attentionTPZText.setText("已关注");
                        attentionTPZText.setTextColor(getResources().getColor(R.color.unset));
                    }
                }
                else{
                    Toast.makeText(BrowseTechPersonZoneActivity.this, "关注专栏失败！", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void getUserInfo(){
        call = techPersonZoneModel.getTechPersonZoneUserinfo(tpzid);
        Callback<techpersonzoneuserinfo> techpersonzoneuserinfoCallback = new Callback<techpersonzoneuserinfo>() {
            @Override
            public void onResponse(Call<techpersonzoneuserinfo> call, Response<techpersonzoneuserinfo> response) {
                techpersonzoneuserinfo data = response.body();
                Glide.with(BrowseTechPersonZoneActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto().toString())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                tpzname.setText(data.getTpzname().toString());
                toolbar.setTitle(data.getTpzname().toString());
                ualiase.setText(data.getUaliase().toString());
                tname.setText(data.getTname().toString());
                detailcount.setText(data.getListcount().toString()+"篇文章");
            }

            @Override
            public void onFailure(Call<techpersonzoneuserinfo> call, Throwable t) {

            }
        };
        call.enqueue(techpersonzoneuserinfoCallback);
    }

    private void getDetailList(){
        call = techPersonZoneModel.getTechPersonZoneDetailList(tpzid);
        Callback<List<techpersonzonedetaillist>> listCallback = new Callback<List<techpersonzonedetaillist>>() {
            @Override
            public void onResponse(Call<List<techpersonzonedetaillist>> call, Response<List<techpersonzonedetaillist>> response) {
                List data = response.body();
                TechPersonZoneDetailListAdapter techPersonZoneDetailListAdapter = new TechPersonZoneDetailListAdapter(BrowseTechPersonZoneActivity.this , data);
                recyclerView.setAdapter(techPersonZoneDetailListAdapter);
            }

            @Override
            public void onFailure(Call<List<techpersonzonedetaillist>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }


}
