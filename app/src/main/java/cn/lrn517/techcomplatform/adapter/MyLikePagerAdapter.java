package cn.lrn517.techcomplatform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import cn.lrn517.techcomplatform.fragment.MyLikeAnswerFragment;
import cn.lrn517.techcomplatform.fragment.MyLikeDetailFragment;
import cn.lrn517.techcomplatform.fragment.MyLikeTPZDetailFragment;

/**
 * Created by lirun on 2018/4/8.
 */

public class MyLikePagerAdapter extends FragmentPagerAdapter{

    private String[] mTitle = new String[]{"普通帖","回答","专栏贴"};

    public MyLikePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if( 1 == position ){
            return new MyLikeAnswerFragment();
        }else if( 2 == position ){
            return new MyLikeTPZDetailFragment();
        }
        return new MyLikeDetailFragment();
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
