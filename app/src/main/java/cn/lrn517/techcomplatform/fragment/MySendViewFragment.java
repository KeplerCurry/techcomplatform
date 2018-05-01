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
import cn.lrn517.techcomplatform.adapter.MySendCommonViewAdapter;
import cn.lrn517.techcomplatform.bean.commonForUserSend;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySendViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private MySendCommonViewAdapter mySendCommonViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private SharedPreferences sharedPreferences;
    private String uid;
    private int state;

    private UserModel userModel = new UserModel();
    private Call call;

    public MySendViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_send_view, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        state = getArguments().getInt("state");
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" ,null);
        recyclerView = view.findViewById(R.id.my_send_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = userModel.getUserSendList(state,uid);
        Callback<List<commonForUserSend>> callback = new Callback<List<commonForUserSend>>() {
            @Override
            public void onResponse(Call<List<commonForUserSend>> call, Response<List<commonForUserSend>> response) {
                List data = response.body();
                if( 0 != data.size()){
                    mySendCommonViewAdapter = new MySendCommonViewAdapter(getActivity(),data,state);
                    recyclerView.setAdapter(mySendCommonViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<commonForUserSend>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

}
