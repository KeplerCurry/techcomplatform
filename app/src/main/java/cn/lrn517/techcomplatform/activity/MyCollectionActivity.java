package cn.lrn517.techcomplatform.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyCollectionPagerAdapter;

public class MyCollectionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.mycollection_toolbar);
        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black);
        tabLayout = (TabLayout) findViewById(R.id.mycollection_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("普通贴收藏"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏贴收藏"));
        viewPager = (ViewPager) findViewById(R.id.mycollection_viewpager);
        viewPager.setAdapter(new MyCollectionPagerAdapter(getSupportFragmentManager()));
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
