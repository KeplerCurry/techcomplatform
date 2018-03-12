package cn.lrn517.techcomplatform.activity;

import android.graphics.Color;
import android.media.Image;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.fragment.CommunityFragment;
import cn.lrn517.techcomplatform.fragment.HomeFragment;
import cn.lrn517.techcomplatform.fragment.MessageFragment;
import cn.lrn517.techcomplatform.fragment.MineFragment;
import cn.lrn517.techcomplatform.fragment.SpecialClassifyFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mLinearHome;
    private LinearLayout mLinearCommunity;
    private LinearLayout mLinearSpecialClassify;
    private LinearLayout mLinearMeasage;
    private LinearLayout mLinearMine;

    private ImageView mImgHome;
    private ImageView mImgCommunity;
    private ImageView mImgSpecialClassify;
    private ImageView mImgMeasage;
    private ImageView mImgMine;

    private TextView mTvHome;
    private TextView mTvCommunity;
    private TextView mTvSpecialClassify;
    private TextView mTvMeasage;
    private TextView mTvMine;

    private Fragment mTabHome;
    private Fragment mTabCommunity;
    private Fragment mTabSpecialClassify;
    private Fragment mTabMeasage;
    private Fragment mTabMine;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initView(){
        //LinearLayout
        mLinearCommunity = (LinearLayout)findViewById(R.id.community_layout);
        mLinearHome = (LinearLayout)findViewById(R.id.home_layout);
        mLinearMeasage = (LinearLayout)findViewById(R.id.message_layout);
        mLinearMine = (LinearLayout)findViewById(R.id.mine_layout);
        mLinearSpecialClassify = (LinearLayout)findViewById(R.id.special_layout);
        //Image
        mImgCommunity = (ImageView)findViewById(R.id.community_image);
        mImgHome = (ImageView)findViewById(R.id.home_image);
        mImgMeasage = (ImageView)findViewById(R.id.message_image);
        mImgMine = (ImageView)findViewById(R.id.mine_image);
        mImgSpecialClassify = (ImageView)findViewById(R.id.special_iamge);
        //TextView
        mTvCommunity = (TextView)findViewById(R.id.community_text);
        mTvHome = (TextView)findViewById(R.id.home_text);
        mTvMeasage = (TextView)findViewById(R.id.message_text);
        mTvMine = (TextView)findViewById(R.id.mine_text);
        mTvSpecialClassify = (TextView)findViewById(R.id.special_text);
    }

    private void initEvent(){
        mLinearSpecialClassify.setOnClickListener(this);
        mLinearMine.setOnClickListener(this);
        mLinearMeasage.setOnClickListener(this);
        mLinearHome.setOnClickListener(this);
        mLinearCommunity.setOnClickListener(this);
    }

    private void setSelect( int i ){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment( fragmentTransaction );
        switch( i ){
            case 0:
                if( null == mTabHome ){
                    mTabHome = new HomeFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabHome);
                }else{
                    fragmentTransaction.show(mTabHome);
                }
                mImgHome.setImageResource(R.drawable.ic_home_set);
                mTvHome.setTextColor(Color.parseColor("#1296db"));
                break;
            case 1:
                if( null == mTabCommunity ){
                    mTabCommunity = new CommunityFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabCommunity);
                }else{
                    fragmentTransaction.show(mTabCommunity);
                }
                mImgCommunity.setImageResource(R.drawable.ic_community_set);
                mTvCommunity.setTextColor(Color.parseColor("#1296db"));
                break;
            case 2:
                if( null == mTabSpecialClassify){
                    mTabSpecialClassify = new SpecialClassifyFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabSpecialClassify);
                }else{
                    fragmentTransaction.show(mTabSpecialClassify);
                }
                mImgSpecialClassify.setImageResource(R.drawable.ic_special_set);
                mTvSpecialClassify.setTextColor(Color.parseColor("#1296db"));
                break;
            case 3:
                if( null == mTabMeasage){
                    mTabMeasage = new MessageFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabMeasage);
                }else{
                    fragmentTransaction.show(mTabMeasage);
                }

                mImgMeasage.setImageResource(R.drawable.ic_message_set);
                mTvMeasage.setTextColor(Color.parseColor("#1296db"));
                break;
            case 4:
                if( null == mTabMine){
                    mTabMine = new MineFragment();
                    fragmentTransaction.add(R.id.main_fragment , mTabMine);
                }else{
                    fragmentTransaction.show(mTabMine);
                }
                mImgMine.setImageResource(R.drawable.ic_mine_set);
                mTvMine.setTextColor(Color.parseColor("#1296db"));
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if( null != mTabHome ){
            fragmentTransaction.hide(mTabHome);
        }
        if( null != mTabCommunity ){
            fragmentTransaction.hide(mTabCommunity);
        }
        if( null != mTabSpecialClassify ){
            fragmentTransaction.hide(mTabSpecialClassify);
        }
        if( null != mTabMeasage ){
            fragmentTransaction.hide(mTabMeasage);
        }
        if( null != mTabMine ){
            fragmentTransaction.hide(mTabMine);
        }
    }

    @Override
    public void onClick(View view) {
        resetView();
        switch ( view.getId() ){
            case R.id.home_layout:
                setSelect(0);
                break;
            case R.id.community_layout:
                setSelect(1);
                break;
            case R.id.special_layout:
                setSelect(2);
                break;
            case R.id.message_layout:
                setSelect(3);
                break;
            case R.id.mine_layout:
                setSelect(4);
                break;
            default:
                break;
        }
    }

    private void resetView(){
        mImgSpecialClassify.setImageResource(R.drawable.ic_special_unset);
        mImgMeasage.setImageResource(R.drawable.ic_message_unset);
        mImgHome.setImageResource(R.drawable.ic_home_unset);
        mImgCommunity.setImageResource(R.drawable.ic_community_unset);
        mImgMine.setImageResource(R.drawable.ic_mine_unset);
        mTvCommunity.setTextColor(Color.parseColor("#000000"));
        mTvMine.setTextColor(Color.parseColor("#000000"));
        mTvMeasage.setTextColor(Color.parseColor("#000000"));
        mTvHome.setTextColor(Color.parseColor("#000000"));
        mTvSpecialClassify.setTextColor(Color.parseColor("#000000"));
    }
}
