package cn.lrn517.techcomplatform.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyLikePagerAdapter;

public class MyLikeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.my_like_toolbar);
        toolbar.setTitle("点赞");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.my_like_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("普通贴"));
        tabLayout.addTab(tabLayout.newTab().setText("回答"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏贴"));
        viewPager = (ViewPager) findViewById(R.id.my_like_viewpager);
        viewPager.setAdapter(new MyLikePagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initEvent(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
