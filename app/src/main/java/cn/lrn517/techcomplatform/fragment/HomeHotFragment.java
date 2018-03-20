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

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.HotDataRecyclerViewAdapter;
import cn.lrn517.techcomplatform.bean.homeData;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeHotFragment extends Fragment {

    private View view;
    List data;
    private RecyclerView recyclerView;
    int page = 0;
    int page_copy = -1;
    Call call;
    DetailModel detailModel = new DetailModel();
    HotDataRecyclerViewAdapter hotDataRecyclerViewAdapter;

    public HomeHotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home_hot, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.home_hot_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        call = detailModel.getHotData(page);
        Callback<List<homeData>> listCallback = new Callback<List<homeData>>() {
            @Override
            public void onResponse(Call<List<homeData>> call, Response<List<homeData>> response) {
                data = response.body();
                if( null != data )
                {
                    hotDataRecyclerViewAdapter = new HotDataRecyclerViewAdapter(getActivity() , data);
                    recyclerView.setAdapter(hotDataRecyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<homeData>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);

        /*
        加载更多
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if( newState == RecyclerView.SCROLL_STATE_IDLE ){
                    LoadMoreData();
                }
            }
        });

    }

    private void LoadMoreData(){
        page++;
        if( -1 == page_copy )
        {
            call = detailModel.getHotData(page);
            Callback<List<homeData>> listCallback = new Callback<List<homeData>>() {
                @Override
                public void onResponse(Call<List<homeData>> call, Response<List<homeData>> response) {
                    List new_data = response.body();
                    //Log.i("datasize" , "datasize:"+data.size()+"new:"+new_data.size());
                    if( 0 != new_data.size() )
                    {
                        data.addAll(new_data);
                        hotDataRecyclerViewAdapter.notifyDataSetChanged();
                        Log.i("test" , "===================="+page);
                    }
                    else
                    {
                        page_copy = page;
                        Log.i("test" , "+++++++++++++++++++");
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
