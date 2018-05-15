package cn.lrn517.techcomplatform.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.lrn517.techcomplatform.bean.searchlist;
import cn.lrn517.techcomplatform.fragment.MySendViewFragment;
import cn.lrn517.techcomplatform.fragment.SearchFinalFragment;

/**
 * Created by lirun on 2018/5/9.
 */

public class SearchPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"用户","技术贴","问题","专栏贴"};
    private Fragment fragment;
    private String searchdata;

    public SearchPagerAdapter(FragmentManager fm , String data){
        super(fm);
        this.searchdata = data;
    }

    @Override
    public Fragment getItem(int position) {
        if( 0 == position ){
            fragment = new SearchFinalFragment();
            Bundle bundle = new Bundle();
            bundle.putString("searchtext" , searchdata);
            bundle.putInt("state" , 0);
            fragment.setArguments(bundle);
        }else if( 1 == position ){
            fragment = new SearchFinalFragment();
            Bundle bundle = new Bundle();
            bundle.putString("searchtext" , searchdata);
            bundle.putInt("state" , 1);
            fragment.setArguments(bundle);
        }else if( 2 == position){
            fragment = new SearchFinalFragment();
            Bundle bundle = new Bundle();
            bundle.putString("searchtext" , searchdata);
            bundle.putInt("state" , 2);
            fragment.setArguments(bundle);
        }else{
            fragment = new SearchFinalFragment();
            Bundle bundle = new Bundle();
            bundle.putString("searchtext" , searchdata);
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
