package cn.lrn517.techcomplatform.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyAttentionCommonViewAdapter;
import cn.lrn517.techcomplatform.adapter.MyLikeCommonViewAdapter;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoCommonFragment extends Fragment {

    //控制变量
    int i;
    private View view;
    private LinearLayout detail_ll,answer_ll,tpzdetail_ll;
    private TextView detail_tv,answer_tv,tpzdetail_tv;
    private RecyclerView detail_rv,answer_rv,tpzdetail_rv;
    private LinearLayoutManager linearLayoutManager;
    private UserModel userModel = new UserModel();
    private MyLikeCommonViewAdapter myLikeCommonViewAdapter;
    Call call;

    String uid = "";

    public UserInfoCommonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_info_common, container, false);
        i = getArguments().getInt("id");
        Log.i("id" , "id:"+i);
        if( null != getArguments().getString("uid")){
            uid = getArguments().getString("uid").toString();
            Log.i("uid" , uid+":uid");
        }
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        detail_ll = view.findViewById(R.id.user_info_common_detail_layout);
        answer_ll = view.findViewById(R.id.user_info_common_answer_layout);
        tpzdetail_ll = view.findViewById(R.id.user_info_common_tpzdetail_layout);
        detail_tv = view.findViewById(R.id.user_info_common_detail_text);
        answer_tv = view.findViewById(R.id.user_info_common_answer_text);
        tpzdetail_tv = view.findViewById(R.id.user_info_common_tpzdetail_text);
        detail_rv = view.findViewById(R.id.user_info_common_detail_recyclerview);
        answer_rv = view.findViewById(R.id.user_info_common_answer_recyclerview);
        tpzdetail_rv = view.findViewById(R.id.user_info_common_tpzdetail_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
    }

    private void initEvent(){
        switch ( i ){
            case 11:
                selectDetail();
                break;
            case 12:
                selectAnswer();
                break;
            case 13:
                selectTPZdetail();
                break;
        }
    }

    private void selectDetail(){
        answer_ll.setVisibility(View.GONE);
        tpzdetail_ll.setVisibility(View.GONE);
        call = userModel.getUserSendData(uid,1);
        Callback<List<commonAttentionData>> callback = new Callback<List<commonAttentionData>>() {
            @Override
            public void onResponse(Call<List<commonAttentionData>> call, Response<List<commonAttentionData>> response) {
                List data = response.body();
                if( 0 == data.size() ){

                }else{
                    detail_tv.setVisibility(View.GONE);
                    myLikeCommonViewAdapter = new MyLikeCommonViewAdapter(getActivity() , data , 21);
                    detail_rv.setLayoutManager(linearLayoutManager);
                    detail_rv.setAdapter(myLikeCommonViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<commonAttentionData>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void selectAnswer(){
        detail_ll.setVisibility(View.GONE);
        tpzdetail_ll.setVisibility(View.GONE);
        call = userModel.getUserSendData(uid,2);
        Callback<List<commonAttentionData>> callback = new Callback<List<commonAttentionData>>() {
            @Override
            public void onResponse(Call<List<commonAttentionData>> call, Response<List<commonAttentionData>> response) {
                List data = response.body();
                if( 0 == data.size() ){

                }else{
                    answer_tv.setVisibility(View.GONE);
                    myLikeCommonViewAdapter = new MyLikeCommonViewAdapter(getActivity() , data , 22);
                    answer_rv.setLayoutManager(linearLayoutManager);
                    answer_rv.setAdapter(myLikeCommonViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<commonAttentionData>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void selectTPZdetail(){
        answer_ll.setVisibility(View.GONE);
        detail_ll.setVisibility(View.GONE);
        call = userModel.getUserSendData(uid,3);
        Callback<List<commonAttentionData>> callback = new Callback<List<commonAttentionData>>() {
            @Override
            public void onResponse(Call<List<commonAttentionData>> call, Response<List<commonAttentionData>> response) {
                List data = response.body();
                if( 0 == data.size() ){

                }else{
                    tpzdetail_tv.setVisibility(View.GONE);
                    myLikeCommonViewAdapter = new MyLikeCommonViewAdapter(getActivity() , data , 23);
                    tpzdetail_rv.setLayoutManager(linearLayoutManager);
                    tpzdetail_rv.setAdapter(myLikeCommonViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<commonAttentionData>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

    private void selectMore(){
        answer_ll.setVisibility(View.GONE);
        tpzdetail_ll.setVisibility(View.GONE);
        detail_ll.setVisibility(View.GONE);
    }

}
