package cn.lrn517.techcomplatform.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MineInfoPagerAdapter;
import cn.lrn517.techcomplatform.adapter.UserInfoPagerAdapter;
import cn.lrn517.techcomplatform.bean.loadUserInfo;
import cn.lrn517.techcomplatform.listener.AppBarStateChangeListener;
import cn.lrn517.techcomplatform.model.UserModel;
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
    private TextView attention_ta;
    private TextView ta_attention;
    private Button to_attention_ta,to_send_ta;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initEvent();
    }

    private void initView(){
        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("uid").toString();

        toolbar = (Toolbar) findViewById(R.id.user_info_toolbar);
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
        attention_ta = findViewById(R.id.user_info_attention_ta);
        ta_attention = findViewById(R.id.user_info_ta_attention);
        to_attention_ta = findViewById(R.id.user_info_to_attention_ta);
        to_send_ta = findViewById(R.id.user_info_to_send_mail);
        tabLayout = findViewById(R.id.user_info_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("帖子"));
        tabLayout.addTab(tabLayout.newTab().setText("回答"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏"));
        viewPager = findViewById(R.id.user_info_viewpager);
        viewPager.setAdapter(new UserInfoPagerAdapter(getSupportFragmentManager(),uid));
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

        setUserInfo();
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

    private void setUserInfo(){
        call = userModel.getUserInfoData(uid);
        Callback<loadUserInfo> callback = new Callback<loadUserInfo>() {
            @Override
            public void onResponse(Call<loadUserInfo> call, Response<loadUserInfo> response) {
                loadUserInfo data = response.body();
                ualiase.setText(data.getUaliase().toString());
                ulevel.setText(data.getUlevel().toString());
                utype.setText(data.getUtype().toString());
                ta_attention.setText(data.getUser_attention()+"Ta关注的人");
                attention_ta.setText(data.getAttention_user()+"关注Ta的人");
            }

            @Override
            public void onFailure(Call<loadUserInfo> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }
}
