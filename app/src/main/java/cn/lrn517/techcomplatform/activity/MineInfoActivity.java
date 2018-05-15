package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MineInfoPagerAdapter;
import cn.lrn517.techcomplatform.bean.loadUserInfo;
import cn.lrn517.techcomplatform.listener.AppBarStateChangeListener;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.lrn517.techcomplatform.service.serviceAddress;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MineInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Call call;
    private UserModel userModel = new UserModel();
    private TextView ualiase;
    private TextView ulevel;
    private TextView utype;
    private TextView uspecialline;
    private TextView attention_me;
    private TextView my_attention;
    private CircleImageView uphoto;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton edit_info;

    public String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info);
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.mine_info_toolbar);
        collapsingToolbarLayout = findViewById(R.id.mine_info_collapsing_toolbar);
        appBarLayout = findViewById(R.id.mine_info_app_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if( null != actionBar ){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        edit_info = findViewById(R.id.mine_info_floatingactionbutton);
        collapsingToolbarLayout.setTitle(" ");
        uphoto = findViewById(R.id.mine_info_uphoto);
        ualiase = findViewById(R.id.mine_info_ualiase);
        uspecialline = findViewById(R.id.mine_info_uspecialline);
        ulevel = findViewById(R.id.mine_info_ulevel);
        utype = findViewById(R.id.mine_info_utype);
        attention_me = findViewById(R.id.mine_info_attention_me);
        my_attention = findViewById(R.id.mine_info_my_attention);
        tabLayout = findViewById(R.id.mine_info_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("帖子"));
        tabLayout.addTab(tabLayout.newTab().setText("回答"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏"));
        viewPager = findViewById(R.id.mine_info_viewpager);
        viewPager.setAdapter(new MineInfoPagerAdapter(getSupportFragmentManager() , uid));
        tabLayout.setupWithViewPager(viewPager);
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

        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineInfoActivity.this,EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        setUserInfo();
    }

    private void setUserInfo(){
        call = userModel.getUserInfoData(uid);
        Callback<loadUserInfo> callback = new Callback<loadUserInfo>() {
            @Override
            public void onResponse(Call<loadUserInfo> call, Response<loadUserInfo> response) {
                loadUserInfo data = response.body();
                Glide.with(MineInfoActivity.this)
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

                my_attention.setText(data.getUser_attention()+"我关注的人");
                attention_me.setText(data.getAttention_user()+"关注我的人");
            }

            @Override
            public void onFailure(Call<loadUserInfo> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }
}
