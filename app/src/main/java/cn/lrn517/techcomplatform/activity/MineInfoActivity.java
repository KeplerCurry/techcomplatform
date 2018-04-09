package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MineInfoPagerAdapter;
import cn.lrn517.techcomplatform.bean.loadUserInfo;
import cn.lrn517.techcomplatform.listener.AppBarStateChangeListener;
import cn.lrn517.techcomplatform.model.UserModel;
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
    private TextView attention_me;
    private TextView my_attention;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public String uid = "20180319124601";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info);
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
        collapsingToolbarLayout.setTitle(" ");
        ualiase = findViewById(R.id.mine_info_ualiase);
        ulevel = findViewById(R.id.mine_info_ulevel);
        utype = findViewById(R.id.mine_info_utype);
        attention_me = findViewById(R.id.mine_info_attention_me);
        my_attention = findViewById(R.id.mine_info_my_attention);
        tabLayout = findViewById(R.id.mine_info_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("帖子"));
        tabLayout.addTab(tabLayout.newTab().setText("回答"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏"));
        viewPager = findViewById(R.id.mine_info_viewpager);
        viewPager.setAdapter(new MineInfoPagerAdapter(getSupportFragmentManager()));
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

        setUserInfo();
    }

    private void setUserInfo(){
        call = userModel.getUserInfoData(uid);
        Callback<loadUserInfo> callback = new Callback<loadUserInfo>() {
            @Override
            public void onResponse(Call<loadUserInfo> call, Response<loadUserInfo> response) {
                loadUserInfo data = response.body();
                ualiase.setText(data.getUaliase().toString());
                ulevel.setText(data.getUlevel().toString());
                utype.setText(data.getUtype().toString());
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
