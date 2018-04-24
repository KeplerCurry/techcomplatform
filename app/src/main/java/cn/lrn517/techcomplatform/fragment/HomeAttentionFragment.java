package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.HomeAttentionCommonViewAdapter;
import cn.lrn517.techcomplatform.bean.homeattentiondata;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeAttentionFragment extends Fragment {

    private TextView unloginlayout;
    private ScrollView loginlayout;
    private TextView no_user,no_question,no_tpz;
    private RecyclerView user_rv,question_rv,tpz_rv;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager2,linearLayoutManager3;
    private View view;

    private SharedPreferences sharedPreferences;
    private String uid = null;
    private Call call;
    private UserModel userModel = new UserModel();
    private HomeAttentionCommonViewAdapter homeAttentionCommonViewAdapter;

    List userdata;
    List questiondata;
    List tpzdata;

    public HomeAttentionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_attention, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        unloginlayout = view.findViewById(R.id.home_attention_unlogin_layout);
        loginlayout = view.findViewById(R.id.home_attention_login_layout);
        no_user = view.findViewById(R.id.home_attention_login_no_user);
        no_question = view.findViewById(R.id.home_attention_login_no_question);
        no_tpz = view.findViewById(R.id.home_attention_login_no_tpz);
        user_rv = view.findViewById(R.id.home_attention_login_user_rv);
        question_rv = view.findViewById(R.id.home_attention_login_question_rv);
        tpz_rv = view.findViewById(R.id.home_attention_login_tpz_rv);
        linearLayoutManager = new LinearLayoutManager( getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        user_rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        question_rv.setLayoutManager(linearLayoutManager2);
        linearLayoutManager3 = new LinearLayoutManager(getActivity());
        tpz_rv.setLayoutManager(linearLayoutManager3);
    }

    private void initEvent(){
        start();
    }

    private void start(){
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        if( null != uid){
            unloginlayout.setVisibility(View.GONE);
            loginlayout.setVisibility(View.VISIBLE);
            getData();
        }else{
            unloginlayout.setVisibility(View.VISIBLE);
            loginlayout.setVisibility(View.GONE);
        }
    }

    private void getData(){
        call = userModel.attentionData(uid);
        Callback<homeattentiondata> callback = new Callback<homeattentiondata>() {
            @Override
            public void onResponse(Call<homeattentiondata> call, Response<homeattentiondata> response) {
                homeattentiondata data = response.body();
                userdata = data.getUser();
                questiondata = data.getDetail();
                tpzdata = data.getTechpersonzone();
                Log.i("size" , "userdata:"+userdata.size()+"questiondata:"+questiondata.size()+"tpzdata:"+tpzdata.size());
                setUser();
                setQuesion();
                setTPZ();
            }

            @Override
            public void onFailure(Call<homeattentiondata> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }


    private void setUser(){
        if( 0 != userdata.size()){
            no_user.setVisibility(View.GONE);
            user_rv.setVisibility(View.VISIBLE);
            homeAttentionCommonViewAdapter = new HomeAttentionCommonViewAdapter( getActivity() , userdata , 11);
            user_rv.setAdapter(homeAttentionCommonViewAdapter);

        }else{
            no_user.setVisibility(View.VISIBLE);
            user_rv.setVisibility(View.GONE);
        }
    }

    private void setQuesion(){
        if( 0 != questiondata.size()){
            no_question.setVisibility(View.GONE);
            question_rv.setVisibility(View.VISIBLE);
            homeAttentionCommonViewAdapter = new HomeAttentionCommonViewAdapter( getActivity() , questiondata , 12);
            question_rv.setAdapter(homeAttentionCommonViewAdapter);

        }else{
            no_question.setVisibility(View.VISIBLE);
            question_rv.setVisibility(View.GONE);
        }
    }

    private void setTPZ(){
        if( 0 != tpzdata.size()){
            no_tpz.setVisibility(View.GONE);
            tpz_rv.setVisibility(View.VISIBLE);
            homeAttentionCommonViewAdapter = new HomeAttentionCommonViewAdapter( getActivity() , tpzdata , 13);
            tpz_rv.setAdapter(homeAttentionCommonViewAdapter);

        }else{
            no_tpz.setVisibility(View.VISIBLE);
            tpz_rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        Log.i("msg","销毁Attention视图");
        super.onDestroyView();
    }

}
