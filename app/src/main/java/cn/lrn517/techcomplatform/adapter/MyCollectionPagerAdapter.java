package cn.lrn517.techcomplatform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.lrn517.techcomplatform.fragment.MyCollectionTechDetailFragment;
import cn.lrn517.techcomplatform.fragment.MyCollectionTPZDetailFragment;

/**
 * Created by lirun on 2018/4/7.
 */

public class MyCollectionPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"普通贴收藏","专栏贴收藏"};

    public MyCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if( 0 == position ){
            return new MyCollectionTechDetailFragment();
        }else{
            return new MyCollectionTPZDetailFragment();
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
