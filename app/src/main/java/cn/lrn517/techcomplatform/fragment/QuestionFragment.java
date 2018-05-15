package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.AddAskForFirstActivity;
import cn.lrn517.techcomplatform.adapter.TechDetailOrQuestionFragmentPagerAdapter;
import cn.lrn517.techcomplatform.bean.techclassifydata;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailModel detailModel = new DetailModel();
    private Call call;
    private String[] mTitle;
    private String[] mTitleId;
    private int i;
    private FloatingActionButton add;

    private SharedPreferences sharedPreferences;
    private String ispassed;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_question, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        viewPager = view.findViewById(R.id.question_viewpager);
        tabLayout = view.findViewById(R.id.question_tablayout);
        add = view.findViewById(R.id.question_floatingActionButton);
    }

    private void initEvent(){
        ispassed = sharedPreferences.getString("ispassed" , null);

        call = detailModel.getTechClassifyData();
        Callback<List<techclassifydata>> callback = new Callback<List<techclassifydata>>() {
            @Override
            public void onResponse(Call<List<techclassifydata>> call, Response<List<techclassifydata>> response) {
                List<techclassifydata> data = response.body();
                i = data.size();
                mTitle = new String[data.size()];
                mTitleId = new String[data.size()];
                int j = 0;
                while( j < i ){
                    mTitle[j] = data.get(j).getTname();
                    mTitleId[j] = data.get(j).getTid();
                    j++;
                }
                doIt();
            }

            @Override
            public void onFailure(Call<List<techclassifydata>> call, Throwable t) {

            }
        };
        call.enqueue(callback);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( "2".equals(ispassed)){
                    Intent intent = new Intent( getActivity() , AddAskForFirstActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "没有通过身份认证，请提交或耐心等待", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void doIt(){

        int k = 0;
        while( k < i ){
            tabLayout.addTab(tabLayout.newTab().setText(mTitle[k]));
            k++;
        }
        viewPager.setAdapter(new TechDetailOrQuestionFragmentPagerAdapter(getActivity().getSupportFragmentManager() , mTitle , mTitleId,1));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        ispassed = sharedPreferences.getString("ispassed" , null);
    }

}
