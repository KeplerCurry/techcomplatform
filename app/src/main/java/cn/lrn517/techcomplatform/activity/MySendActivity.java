package cn.lrn517.techcomplatform.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyAttentionPagerAdapter;
import cn.lrn517.techcomplatform.adapter.MySendPagerAdapter;

public class MySendActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_send);
        initView();
    }

    private void initView(){
        toolbar = findViewById(R.id.my_send_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = findViewById(R.id.my_send_tablayout);
        tabLayout = (TabLayout) findViewById(R.id.my_send_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("普通贴"));
        tabLayout.addTab(tabLayout.newTab().setText("问题"));
        tabLayout.addTab(tabLayout.newTab().setText("回答"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏贴"));
        viewPager = (ViewPager) findViewById(R.id.my_send_viewpager);
        viewPager.setAdapter(new MySendPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
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
