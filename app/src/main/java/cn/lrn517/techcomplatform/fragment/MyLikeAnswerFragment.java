package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyLikeCommonViewAdapter;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLikeAnswerFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Call call;
    private UserModel userModel = new UserModel();
    private MyLikeCommonViewAdapter myLikeCommonViewAdapter;
    private SharedPreferences sharedPreferences;
    String uid = "";
    public MyLikeAnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_like_answer, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", null);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.my_like_answer_recycylerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = userModel.getAttentionDataList(22,uid);
        Callback<List<commonAttentionData>> callback = new Callback<List<commonAttentionData>>() {
            @Override
            public void onResponse(Call<List<commonAttentionData>> call, Response<List<commonAttentionData>> response) {
                List data = response.body();
                if( 0 != data.size()){
                    myLikeCommonViewAdapter = new MyLikeCommonViewAdapter(getActivity() , data , 22);
                    recyclerView.setAdapter(myLikeCommonViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<commonAttentionData>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

}
