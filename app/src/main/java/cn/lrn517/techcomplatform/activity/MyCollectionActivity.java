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
    private String[] tablayoutText = {"我的赞" ," 我的收藏"};
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
    }

    private void initEvent(){
        viewPager.setAdapter(new MyCollectionPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("tab" ,tab.getPosition()+"");
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
