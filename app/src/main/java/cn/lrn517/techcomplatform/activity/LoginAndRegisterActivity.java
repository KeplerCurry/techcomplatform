package cn.lrn517.techcomplatform.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.fragment.LoginFragment;
import cn.lrn517.techcomplatform.fragment.RegisterFragment;
import cn.smssdk.SMSSDK;

public class LoginAndRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView l_a_r_text;
    private Fragment mTabLogin;
    private Fragment mTabRegister;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initView(){
        l_a_r_text = (TextView)findViewById(R.id.login_and_register_textview);
        toolbar = findViewById(R.id.login_and_register_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initEvent(){
        l_a_r_text.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        if( "注册".equals(l_a_r_text.getText().toString())){
            setSelect(1);
        }else{
            setSelect(0);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSelect( int i ){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment( fragmentTransaction );
        switch ( i ){
            case 0:
                if( null == mTabLogin ){
                    mTabLogin = new LoginFragment();
                    fragmentTransaction.add(R.id.login_and_register_fragment , mTabLogin);
                }else{
                    fragmentTransaction.show(mTabLogin);
                }
                l_a_r_text.setText("注册");
                toolbar.setTitle("登录");
                //l_a_r_text.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                if( null == mTabRegister ){
                    mTabRegister = new RegisterFragment();
                    fragmentTransaction.add(R.id.login_and_register_fragment , mTabRegister);
                }else{
                    fragmentTransaction.show(mTabRegister);
                }
                l_a_r_text.setText("登录");
                toolbar.setTitle("注册");
                //l_a_r_text.setTextColor(Color.parseColor("#000000"));
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if( null != mTabLogin ){
            fragmentTransaction.hide(mTabLogin);
        }
        if( null != mTabRegister ){
            fragmentTransaction.hide(mTabRegister);
        }
    }

    /*
     *销毁Activity后解除SMSSDK绑定，防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
