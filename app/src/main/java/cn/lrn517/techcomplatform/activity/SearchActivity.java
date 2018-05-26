package cn.lrn517.techcomplatform.activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.SearchCommonViewAdapter;
import cn.lrn517.techcomplatform.adapter.SearchPagerAdapter;
import cn.lrn517.techcomplatform.bean.userSearchHistory;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText search;
    private ImageView back;
    private UserModel userModel = new UserModel();
    private SharedPreferences sharedPreferences;
    private String uid = null;
    private Call call;

    private LinearLayout click_before,click_after;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView not_search;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchCommonViewAdapter searchCommonViewAdapter;

    public String searchtext = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initEvent();
    }

    private void initView(){
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        search = findViewById(R.id.search_edittext);
        not_search = findViewById(R.id.search_not_history);
        back = findViewById(R.id.search_back);
        click_before = findViewById(R.id.search_not_click_layout);
        click_after = findViewById(R.id.search_click_layout);
        recyclerView = findViewById(R.id.search_history_recyclerview);
        linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        tabLayout = findViewById(R.id.search_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("用户"));
        tabLayout.addTab(tabLayout.newTab().setText("技术贴"));
        tabLayout.addTab(tabLayout.newTab().setText("问题"));
        tabLayout.addTab(tabLayout.newTab().setText("专栏贴"));
        viewPager = findViewById(R.id.search_viewpager);
    }

    private void initEvent(){

        click_after.setVisibility(View.GONE);
        not_search.setVisibility(View.GONE);

        call = userModel.getUserSearch(uid);
        Callback<List<userSearchHistory>> callback = new Callback<List<userSearchHistory>>() {
            @Override
            public void onResponse(Call<List<userSearchHistory>> call, Response<List<userSearchHistory>> response) {
                List data = response.body();
                if( 0 != data.size()){
                    searchCommonViewAdapter = new SearchCommonViewAdapter(SearchActivity.this, data,5);
                    recyclerView.setAdapter(searchCommonViewAdapter);
                }else{
                    recyclerView.setVisibility(View.GONE);
                    not_search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<userSearchHistory>> call, Throwable t) {

            }
        };
        call.enqueue(callback);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if( 0 == i || 3 == i){
                    searchtext = search.getText().toString();
                    viewPager.setAdapter(new SearchPagerAdapter(getSupportFragmentManager(),searchtext));
                    tabLayout.setupWithViewPager(viewPager);
                    click_before.setVisibility(View.GONE);
                    click_after.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setSearch(String text){
        search.setText(text);
    }
}
