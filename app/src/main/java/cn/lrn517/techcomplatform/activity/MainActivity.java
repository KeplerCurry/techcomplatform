package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.fragment.CommunityFragment;
import cn.lrn517.techcomplatform.fragment.HomeFragment;
import cn.lrn517.techcomplatform.fragment.MessageFragment;
import cn.lrn517.techcomplatform.fragment.MineFragment;
import cn.lrn517.techcomplatform.fragment.SpecialClassifyFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Fragment mTabHome;
    private Fragment mTabCommunity;
    private Fragment mTabSpecialClassify;
    private Fragment mTabMeasage;
    private Fragment mTabMine;

    private LinearLayout login;
    private LinearLayout unlogin;

    NavigationView navigationView;

    private SharedPreferences sharedPreferences;
    //登录后返回MainActivity的控制碎片变量
    private int i;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSelect(0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void initView(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navview = navigationView.getHeaderView(0);
        login = navview.findViewById(R.id.login_layout);
        unlogin = navview.findViewById(R.id.unlogin_layout);
    }

    private void setSelect( int i ){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment( fragmentTransaction );
        switch( i ){
            case 0:
                if( null == mTabHome ){
                    mTabHome = new HomeFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabHome);
                }else{
                    fragmentTransaction.show(mTabHome);
                }
                break;
            case 1:
                if( null == mTabCommunity ){
                    mTabCommunity = new CommunityFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabCommunity);
                }else{
                    fragmentTransaction.show(mTabCommunity);
                }
                break;
            case 2:
                if( null == mTabSpecialClassify){
                    mTabSpecialClassify = new SpecialClassifyFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabSpecialClassify);
                }else{
                    fragmentTransaction.show(mTabSpecialClassify);
                }
                break;
            case 3:
                if( null == mTabMeasage){
                    mTabMeasage = new MessageFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabMeasage);
                }else{
                    fragmentTransaction.show(mTabMeasage);
                }
                break;
            case 4:
                sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                uid = sharedPreferences.getString("uid" , null);
                if( null == uid ){
                    Intent intent = new Intent(MainActivity.this,LoginAndRegisterActivity.class);
                    startActivity(intent);
                }else{
                    if( null == mTabMine){
                        mTabMine = new MineFragment();
                        fragmentTransaction.add(R.id.main_fragment , mTabMine);
                    }else{
                        fragmentTransaction.show(mTabMine);
                    }
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if( null != mTabHome ){
            fragmentTransaction.hide(mTabHome);
        }
        if( null != mTabCommunity ){
            fragmentTransaction.hide(mTabCommunity);
        }
        if( null != mTabSpecialClassify ){
            fragmentTransaction.hide(mTabSpecialClassify);
        }
        if( null != mTabMeasage ){
            fragmentTransaction.hide(mTabMeasage);
        }
        if( null != mTabMine ){
            fragmentTransaction.hide(mTabMine);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setTitle("首页");
            setSelect(0);
            i = 0;
        } else if (id == R.id.nav_community) {
            toolbar.setTitle("社区");
            setSelect(1);
            i = 1;
        } else if (id == R.id.nav_special) {
            toolbar.setTitle("专栏");
            setSelect(2);
            i = 2;
        } else if (id == R.id.nav_message) {
            toolbar.setTitle("私信");
            setSelect(3);
            i = 3;
        } else if (id == R.id.nav_mine) {
            toolbar.setTitle("个人");
            setSelect(4);
            i = 4;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        if( null != uid ){
            login.setVisibility(View.VISIBLE);
            unlogin.setVisibility(View.GONE);
        }else{
            login.setVisibility(View.GONE);
            unlogin.setVisibility(View.VISIBLE);
        }
    }
}
