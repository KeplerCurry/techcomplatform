package cn.lrn517.techcomplatform.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.lrn517.techcomplatform.fragment.MySendViewFragment;

/**
 * Created by lirun on 2018/5/1.
 */

public class MySendPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"普通帖","问题","回答","专栏贴"};
    private Fragment fragment;

    public MySendPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if( 0 == position ){
            fragment = new MySendViewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state" , 0);
            fragment.setArguments(bundle);
        }else if( 1 == position ){
            fragment = new MySendViewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state" , 1);
            fragment.setArguments(bundle);
        }else if( 2 == position){
            fragment = new MySendViewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state" , 2);
            fragment.setArguments(bundle);
        }else{
            fragment = new MySendViewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("state" , 3);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return  mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
