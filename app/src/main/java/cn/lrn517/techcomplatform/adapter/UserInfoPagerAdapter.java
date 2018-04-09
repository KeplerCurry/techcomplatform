package cn.lrn517.techcomplatform.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.lrn517.techcomplatform.fragment.UserInfoCommonFragment;

/**
 * Created by lirun on 2018/4/9.
 */

public class UserInfoPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"帖子","回答","专栏","更多"};
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Bundle bundle;
    String uid;

    public UserInfoPagerAdapter(FragmentManager fm ,String uid) {
        super(fm);
        this.uid = uid;
    }

    @Override
    public Fragment getItem(int position) {
        if( 0 == position ){
            fragment1 = new UserInfoCommonFragment();
            bundle = new Bundle();
            bundle.putInt("id", 11);
            bundle.putString("uid",uid);
            fragment1.setArguments(bundle);
            return fragment1;
        }else if( 1 == position ){
            fragment2 = new UserInfoCommonFragment();
            bundle = new Bundle();
            bundle.putInt("id" , 12);
            bundle.putString("uid",uid);
            fragment2.setArguments(bundle);
            return fragment2;
        }else if( 2 == position){
            fragment3 = new UserInfoCommonFragment();
            bundle = new Bundle();
            bundle.putInt("id" , 13);
            bundle.putString("uid",uid);
            fragment3.setArguments(bundle);
            return fragment3;
        }else{
            fragment4 = new UserInfoCommonFragment();
            bundle = new Bundle();
            bundle.putInt("id" , 14);
            bundle.putString("uid",uid);
            fragment4.setArguments(bundle);
            return fragment4;
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
