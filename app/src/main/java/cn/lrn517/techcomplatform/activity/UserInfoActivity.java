package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MineInfoPagerAdapter;
import cn.lrn517.techcomplatform.adapter.UserInfoPagerAdapter;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.loadUserInfo;
import cn.lrn517.techcomplatform.listener.AppBarStateChangeListener;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Call call;
    private UserModel userModel = new UserModel();
    private TextView ualiase;
    private TextView ulevel;
    private TextView utype;
    private TextView uspecialline;
    private TextView attention_ta;
    private TextView ta_attention;
    private CircleImageView uphoto;
    private Button to_attention_ta,to_send_ta;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;

    String uid = "";
    String userid = "";
    private int attentionUserFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initEvent();
    }

    private void initView(){
        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("uid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", null);
        toolbar = findViewById(R.id.user_info_toolbar);
        collapsingToolbarLayout = findViewById(R.id.user_info_collapsing_toolbar);
        appBarLayout = findViewById(R.id.user_info_app_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if( null != actionBar ){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(" ");
        ualiase = findViewById(R.id.user_info_ualiase);
        ulevel = findViewById(R.id.user_info_ulevel);
        utype = findViewById(R.id.user_info_utype);
        uspecialline = findViewById(R.id.user_info_uspecialline);
        attention_ta = findViewById(R.id.user_info_attention_ta);
        ta_attention = findViewById(R.id.user_info_ta_attention);
        to_attention_ta = findViewById(R.id.user_info_to_attention_ta);
        to_send_ta = findViewById(R.id.user_info_to_send_mail);
        uphoto = findViewById(R.id.user_info_uphoto);
        tabLayout = findViewById(R.id.user_info_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("帖子"));
        tabLayout.addTab(tabLayout.newTab().setText("回答"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏"));
        viewPager = findViewById(R.id.user_info_viewpager);
        viewPager.setAdapter(new UserInfoPagerAdapter(getSupportFragmentManager(),userid));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initEvent(){
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ){
                    collapsingToolbarLayout.setTitle(" ");
                    collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                }else if( state == State.COLLAPSED ){
                    collapsingToolbarLayout.setTitle(ualiase.getText().toString());
                    collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    collapsingToolbarLayout.setTitle(" ");
                    collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
            }
        });

        to_send_ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this,MessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid" , userid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        to_attention_ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( null != uid){
                    attention();
                }else{

                }
            }
        });

        if( null == uid){
            setUserInfoByNoUid();
        }else{
            setUserInfoByUid();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUserInfoByNoUid(){
        call = userModel.getUserInfoData(userid);
        Callback<loadUserInfo> callback = new Callback<loadUserInfo>() {
            @Override
            public void onResponse(Call<loadUserInfo> call, Response<loadUserInfo> response) {
                loadUserInfo data = response.body();
                Glide.with(UserInfoActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                ualiase.setText(data.getUaliase().toString());
                ulevel.setText("Lv."+data.getUlevel().toString());
                uspecialline.setText(data.getUspecialline());
                if( "0".equals(data.getUtype().toString())){
                    utype.setText("普通用户");
                }else{
                    utype.setText("高级用户");
                }
                ta_attention.setText(data.getUser_attention()+"Ta关注的人");
                attention_ta.setText(data.getAttention_user()+"关注Ta的人");
            }

            @Override
            public void onFailure(Call<loadUserInfo> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void setUserInfoByUid(){
        call = userModel.getUserInfoData(userid);
        Callback<loadUserInfo> callback = new Callback<loadUserInfo>() {
            @Override
            public void onResponse(Call<loadUserInfo> call, Response<loadUserInfo> response) {
                loadUserInfo data = response.body();
                Glide.with(UserInfoActivity.this)
                        .load(serviceAddress.SERVICE_ADDRESS+"/Public/userphoto/"+data.getUphoto())
                        .dontAnimate()
                        .crossFade()
                        .into(uphoto);
                ualiase.setText(data.getUaliase().toString());
                ulevel.setText("Lv."+data.getUlevel().toString());
                uspecialline.setText(data.getUspecialline());
                if( "0".equals(data.getUtype().toString())){
                    utype.setText("普通用户");
                }else{
                    utype.setText("高级用户");
                }
                ta_attention.setText(data.getUser_attention()+"Ta关注的人");
                attention_ta.setText(data.getAttention_user()+"关注Ta的人");
                getUserAttention();
            }

            @Override
            public void onFailure(Call<loadUserInfo> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void getUserAttention(){
        call = userModel.getUserAttentionUser(uid,userid);
        final Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    attentionUserFlag = 1;
                    to_attention_ta.setText("已关注");
                }else{
                    attentionUserFlag = 0;
                    to_attention_ta.setText("关注Ta");
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }

    private void attention(){
        call = userModel.common_like_collect_attention(attentionUserFlag,11,userid,uid);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    if( 1 == attentionUserFlag){
                        Toast.makeText(UserInfoActivity.this, "取消关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 0;
                        to_attention_ta.setText("关注Ta");
                    }else{
                        Toast.makeText(UserInfoActivity.this, "关注用户成功！", Toast.LENGTH_SHORT).show();
                        attentionUserFlag = 1;
                        to_attention_ta.setText("已关注");
                    }
                }
                else{
                    Toast.makeText(UserInfoActivity.this, "关注用户失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }
}
