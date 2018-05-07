package cn.lrn517.techcomplatform.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.TechDetailOrQuestionDataRecyclerViewAdapter;
import cn.lrn517.techcomplatform.bean.homeData;
import cn.lrn517.techcomplatform.listener.MoreRecyclerOnScrollListener;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechDetailOrQuestionViewFragment extends Fragment {

    private View view;
    //private TextView test;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String tid;
    private int state;
    List data;
    int page = 1;
    int page_copy = -1;
    Call call;
    DetailModel detailModel = new DetailModel();
    TechDetailOrQuestionDataRecyclerViewAdapter techDetailOrQuestionDataRecyclerViewAdapter;

    public TechDetailOrQuestionViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_question_view, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        //test = view.findViewById(R.id.question_view_test);
        swipeRefreshLayout = view.findViewById(R.id.question_view_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorBasic));
        recyclerView = view.findViewById(R.id.question_view_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        tid = getArguments().getString("tid");
        state = getArguments().getInt("state");
        swipeRefreshLayout.setRefreshing(true);
        getFirstData();

        recyclerView.addOnScrollListener(new MoreRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                techDetailOrQuestionDataRecyclerViewAdapter.setLoadState(techDetailOrQuestionDataRecyclerViewAdapter.LOADING);
                if( page == page_copy ){
                    techDetailOrQuestionDataRecyclerViewAdapter.setLoadState(techDetailOrQuestionDataRecyclerViewAdapter.LOADING_END);
                }else{
                    LoadMoreData();
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                techDetailOrQuestionDataRecyclerViewAdapter.notifyDataSetChanged();
                page = 1;
                page_copy = -1;
                getFirstData();
                Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFirstData(){
        call = detailModel.getTechOrQuestionData(tid,state,page);
        Callback<List<homeData>> listCallback = new Callback<List<homeData>>() {
            @Override
            public void onResponse(Call<List<homeData>> call, Response<List<homeData>> response) {
                data = response.body();
                if( null != data )
                {
                    techDetailOrQuestionDataRecyclerViewAdapter = new TechDetailOrQuestionDataRecyclerViewAdapter(getActivity() , data);
                    recyclerView.setAdapter(techDetailOrQuestionDataRecyclerViewAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<homeData>> call, Throwable t) {
                Log.i("network" , "获取失败");
            }
        };
        call.enqueue(listCallback);
    }

    private void LoadMoreData(){
        page++;
        if( -1 == page_copy )
        {
            call = detailModel.getTechOrQuestionData(tid,state,page);
            Callback<List<homeData>> listCallback = new Callback<List<homeData>>() {
                @Override
                public void onResponse(Call<List<homeData>> call, Response<List<homeData>> response) {
                    List new_data = response.body();
                    //Log.i("datasize" , "datasize:"+data.size()+"new:"+new_data.size());
                    if( 0 != new_data.size() )
                    {
                        data.addAll(new_data);
                        techDetailOrQuestionDataRecyclerViewAdapter.notifyDataSetChanged();
                        Log.i("test" , "===================="+page);
                        techDetailOrQuestionDataRecyclerViewAdapter.setLoadState(techDetailOrQuestionDataRecyclerViewAdapter.LOADING_COMPLETE);
                    }
                    else
                    {
                        page_copy = page;
                        Log.i("test" , "+++++++++++++++++++");
                        techDetailOrQuestionDataRecyclerViewAdapter.setLoadState(techDetailOrQuestionDataRecyclerViewAdapter.LOADING_END);
                    }
                }

                @Override
                public void onFailure(Call<List<homeData>> call, Throwable t) {

                }
            };
            call.enqueue(listCallback);
        }
        else
        {

        }

    }

}
