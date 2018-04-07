package cn.lrn517.techcomplatform.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MyCollectionViewAdapter;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectionTechDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private UserModel userModel = new UserModel();
    private Call call;

    //测试数据
    String uid = "20180319124601";
    public MyCollectionTechDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_collection_techdetail, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.my_collection_techdetail_recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = userModel.getAttentionDataList(31 , uid);
        Callback<List<commonAttentionData>> listCallback = new Callback<List<commonAttentionData>>() {
            @Override
            public void onResponse(Call<List<commonAttentionData>> call, Response<List<commonAttentionData>> response) {
                List data = response.body();
                recyclerView.setAdapter(new MyCollectionViewAdapter(getActivity() , data));
            }

            @Override
            public void onFailure(Call<List<commonAttentionData>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }
}
