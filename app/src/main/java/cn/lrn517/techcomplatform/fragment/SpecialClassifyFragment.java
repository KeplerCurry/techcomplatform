package cn.lrn517.techcomplatform.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.AddTPZDetailActivity;
import cn.lrn517.techcomplatform.activity.ApplyForSpecialClassifyActivity;
import cn.lrn517.techcomplatform.adapter.TechPersonZoneListAdapter;
import cn.lrn517.techcomplatform.bean.techpersonzonelist;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialClassifyFragment extends Fragment {

    private FloatingActionButton applyfor;
    private LinearLayout zoneLayout;
    private TextView noZone;
    private View view;
    Call call;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    //测试数据
    int i = 1;

    public SpecialClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_special_classify, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        applyfor = view.findViewById(R.id.special_classify_apply_for);
        zoneLayout = view.findViewById(R.id.special_classify_zone);
        noZone = view.findViewById(R.id.special_classify_no_zone);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.special_classify_listview);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void initEvent(){

        if( 1 == i ){
            noZone.setVisibility(View.GONE);
            zoneLayout.setVisibility(View.VISIBLE);
        }else{
            noZone.setVisibility(View.VISIBLE);
            zoneLayout.setVisibility(View.GONE);
        }

        applyfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( 0 == i ){
                    Intent intent = new Intent(getActivity() , ApplyForSpecialClassifyActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity() , AddTPZDetailActivity.class);
                    startActivity(intent);
                }

            }
        });

        getTechPersonZoneListData();
    }

    private void getTechPersonZoneListData(){
        call = techPersonZoneModel.getTechPersonZoneList();
        Callback<List<techpersonzonelist>> listCallback = new Callback<List<techpersonzonelist>>() {
            @Override
            public void onResponse(Call<List<techpersonzonelist>> call, Response<List<techpersonzonelist>> response) {
                List data = response.body();
                TechPersonZoneListAdapter adapter = new TechPersonZoneListAdapter( getActivity() , data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<techpersonzonelist>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }

}
