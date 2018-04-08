package cn.lrn517.techcomplatform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.lrn517.techcomplatform.fragment.MyAttentionQuestionFragment;
import cn.lrn517.techcomplatform.fragment.MyAttentionTPZFragment;
import cn.lrn517.techcomplatform.fragment.MyAttentionUserFragment;

/**
 * Created by lirun on 2018/4/8.
 */

public class MyAttentionPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"用户","问题","专栏"};

    public MyAttentionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if( 1 == position ){
            return new MyAttentionQuestionFragment();
        }else if( 2 == position ){
            return new MyAttentionTPZFragment();
        }
        return new MyAttentionUserFragment();
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
