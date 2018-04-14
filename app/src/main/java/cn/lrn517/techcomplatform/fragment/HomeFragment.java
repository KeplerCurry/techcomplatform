package cn.lrn517.techcomplatform.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
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
import cn.lrn517.techcomplatform.activity.AddAskForFirstActivity;
import cn.lrn517.techcomplatform.activity.AddTechnologyDetailFirstActivity;
import cn.lrn517.techcomplatform.adapter.HomeFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton add;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        add = view.findViewById(R.id.home_add);
        tabLayout = view.findViewById(R.id.home_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("热门"));
        tabLayout.addTab(tabLayout.newTab().setText("推荐"));
        tabLayout.addTab(tabLayout.newTab().setText("关注"));
        viewPager = view.findViewById(R.id.home_viewpager);
        viewPager.setAdapter(new HomeFragmentPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);//设置页面缓存数，防止切到第三页面的时候第一页面的视图被销毁
        tabLayout.setupWithViewPager(viewPager);
    }



    private void initEvent(){

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("请选择发布类型");
                alert.setPositiveButton("提问", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent( getActivity() , AddAskForFirstActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("发帖", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent( getActivity() , AddTechnologyDetailFirstActivity.class);
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        });
    }
}
