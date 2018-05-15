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
import cn.lrn517.techcomplatform.adapter.SearchCommonViewAdapter;
import cn.lrn517.techcomplatform.bean.searchlist;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFinalFragment extends Fragment {

    int i;
    public String searchtext = null;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private UserModel userModel = new UserModel();
    private String uid = null;
    private Call call;
    private SearchCommonViewAdapter searchCommonViewAdapter;

    public SearchFinalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_final, container, false);
        i = getArguments().getInt("state");
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        searchtext = getArguments().getString("searchtext");
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.search_final_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = userModel.search(uid,searchtext,i);
        switch (i){
            case 0:
                Callback<List<searchlist>> callback = new Callback<List<searchlist>>() {
                    @Override
                    public void onResponse(Call<List<searchlist>> call, Response<List<searchlist>> response) {
                        List data = response.body();
                        if( 0 != data.size()){
                            searchCommonViewAdapter = new SearchCommonViewAdapter(getActivity(),data,i);
                            recyclerView.setAdapter(searchCommonViewAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<searchlist>> call, Throwable t) {

                    }
                };
                call.enqueue(callback);
                break;
            case 1:
                Callback<List<searchlist>> callback1 = new Callback<List<searchlist>>() {
                    @Override
                    public void onResponse(Call<List<searchlist>> call, Response<List<searchlist>> response) {
                        List data = response.body();
                        if( 0 != data.size()){
                            searchCommonViewAdapter = new SearchCommonViewAdapter(getActivity(),data,i);
                            recyclerView.setAdapter(searchCommonViewAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<searchlist>> call, Throwable t) {

                    }
                };
                call.enqueue(callback1);
                break;
            case 2:
                Callback<List<searchlist>> callback2 = new Callback<List<searchlist>>() {
                    @Override
                    public void onResponse(Call<List<searchlist>> call, Response<List<searchlist>> response) {
                        List data = response.body();
                        if( 0 != data.size()){
                            searchCommonViewAdapter = new SearchCommonViewAdapter(getActivity(),data,i);
                            recyclerView.setAdapter(searchCommonViewAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<searchlist>> call, Throwable t) {

                    }
                };
                call.enqueue(callback2);
                break;
            case 3:
                Callback<List<searchlist>> callback3 = new Callback<List<searchlist>>() {
                    @Override
                    public void onResponse(Call<List<searchlist>> call, Response<List<searchlist>> response) {
                        List data = response.body();
                        if( 0 != data.size()){
                            searchCommonViewAdapter = new SearchCommonViewAdapter(getActivity(),data,i);
                            recyclerView.setAdapter(searchCommonViewAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<searchlist>> call, Throwable t) {

                    }
                };
                call.enqueue(callback3);
                break;
        }
    }
}
