package cn.lrn517.techcomplatform.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.lrn517.techcomplatform.fragment.TechDetailOrQuestionViewFragment;

/**
 * Created by lirun on 2018/4/28.
 */

public class TechDetailOrQuestionFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment fragment;
    private String[] mTitle;
    private String[] mTitleId;
    private int state;
    private Bundle bundle;

    public TechDetailOrQuestionFragmentPagerAdapter(FragmentManager fm, String[] data , String[] data1 , int state){
        super(fm);
        this.mTitle = data;
        this.mTitleId = data1;
        this.state = state;
    }
    @Override
    public Fragment getItem(int position) {
        fragment = new TechDetailOrQuestionViewFragment();
        bundle = new Bundle();
        bundle.putString("title" , mTitle[position]);
        bundle.putString("tid" , mTitleId[position]);
        bundle.putInt("state" , state);
        fragment.setArguments(bundle);
        return fragment;
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
