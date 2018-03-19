package cn.lrn517.techcomplatform.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.HomeFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view,viewbar;
    private ArrayList<Fragment> fragmentArrayList;
    private ViewPager viewPager;
    private int currIndex; //当前页卡编号
    private TextView pager1,pager2,pager3;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initBar(view);
        InitViewPager(view);
        return view;
    }

    private void initView(View view){
        pager1 = view.findViewById(R.id.home_line1);
        pager2 = view.findViewById(R.id.home_line2);
        pager3 = view.findViewById(R.id.home_line3);

        pager1.setOnClickListener(new cvListener(0));
        pager2.setOnClickListener(new cvListener(1));
        pager3.setOnClickListener(new cvListener(2));
    }

    //监听页面切换
    private class cvListener implements View.OnClickListener{

        private int index = 0;

        public cvListener(int i){
            index = i;
        }
        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(index);
        }
    }

    //页面切换时滚动条移动
    public void initBar(View view){
        viewbar = view.findViewById(R.id.home_bar);
        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        //得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //设置滚动条宽度为屏幕宽度得1/3
        int tabLineLength = metrics.widthPixels / 3 ;
        ViewGroup.LayoutParams layoutParams = viewbar.getLayoutParams();
        layoutParams.width = tabLineLength;
        viewbar.setLayoutParams(layoutParams);
    }

    //初始化ViewPager
    public void InitViewPager(View view){
        viewPager = view.findViewById(R.id.home_viewpager);
        fragmentArrayList = new ArrayList<>();
        Fragment HomeAttentionFragment = new HomeAttentionFragment();
        Fragment HomeCommendFragment = new HomeCommendFragment();
        Fragment HomeHotFragment = new HomeHotFragment();
        fragmentArrayList.add(HomeHotFragment);
        fragmentArrayList.add(HomeCommendFragment);
        fragmentArrayList.add(HomeAttentionFragment);
        //设置适配器
        viewPager.setAdapter(new HomeFragmentPagerAdapter(getActivity().getSupportFragmentManager() , fragmentArrayList));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new HomeOnPageChangeListener());
    }

    //页面变化监听器
    public class HomeOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //获取控件实例
            LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) viewbar.getLayoutParams();
            selectTextColor(currIndex);
            if ( currIndex == position ){
                layoutParams.leftMargin=  (int) (currIndex * viewbar.getWidth() + positionOffset * viewbar.getWidth());
            }else if( currIndex > position ){
                layoutParams.leftMargin = (int) (currIndex * viewbar.getWidth() - (1 - positionOffset) * viewbar.getWidth());
            }
            viewbar.setLayoutParams(layoutParams);
        }

        @Override
        public void onPageSelected(int position) {
            currIndex = position ;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void selectTextColor(int i){
        pager1.setTextColor(Color.parseColor("#a1abb5"));
        pager2.setTextColor(Color.parseColor("#a1abb5"));
        pager3.setTextColor(Color.parseColor("#a1abb5"));
        switch ( i ){
            case 0:
                pager1.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                pager2.setTextColor(Color.parseColor("#000000"));
                break;
            case 2:
                pager3.setTextColor(Color.parseColor("#000000"));
                break;
        }
    }
}
