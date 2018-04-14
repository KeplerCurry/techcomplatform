package cn.lrn517.techcomplatform.activity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

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
                if( null == mTabMine){
                    mTabMine = new MineFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabMine);
                }else{
                    fragmentTransaction.show(mTabMine);
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
//        switch( id ){
//            case :
//                return true;
//            case android.R.id.selectAll:
//                break;
//        }

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
        } else if (id == R.id.nav_community) {
            toolbar.setTitle("社区");
            setSelect(1);
        } else if (id == R.id.nav_special) {
            toolbar.setTitle("专栏");
            setSelect(2);
        } else if (id == R.id.nav_message) {
            toolbar.setTitle("私信");
            setSelect(3);
        } else if (id == R.id.nav_mine) {
            toolbar.setTitle("个人");
            setSelect(4);
        }else if (id == R.id.nav_share ){

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
