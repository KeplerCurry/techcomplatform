package cn.lrn517.techcomplatform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.lrn517.techcomplatform.fragment.HomeAttentionFragment;
import cn.lrn517.techcomplatform.fragment.HomeCommendFragment;
import cn.lrn517.techcomplatform.fragment.HomeHotFragment;

/**
 * Created by lirun on 2018/3/12.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"热门","推荐","关注"};
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;

    public HomeFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if( 0 == position ){
            if( null == fragment1)
            {
                fragment1 = new HomeHotFragment();
            }
            return fragment1;
        }else if( 1 == position ){
            if( null == fragment2){
                fragment2 = new HomeCommendFragment();
            }
            return fragment2;
        }else{
            if( null == fragment3 ){
                fragment3 = new HomeAttentionFragment();
            }
            return fragment3;
        }

    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }


}
