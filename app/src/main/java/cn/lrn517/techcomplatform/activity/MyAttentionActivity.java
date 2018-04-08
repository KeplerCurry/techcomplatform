package cn.lrn517.techcomplatform.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyAttentionPagerAdapter;

public class MyAttentionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_attention_toolbar);
        toolbar.setTitle("我的关注");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black);
        tabLayout = (TabLayout) findViewById(R.id.my_attention_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("用户"));
        tabLayout.addTab(tabLayout.newTab().setText("问题"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏"));
        viewPager = (ViewPager) findViewById(R.id.my_attention_viewpager);
        viewPager.setAdapter(new MyAttentionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initEvent(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
