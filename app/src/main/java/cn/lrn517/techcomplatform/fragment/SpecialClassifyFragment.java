package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.AddTPZDetailActivity;
import cn.lrn517.techcomplatform.activity.ApplyForSpecialClassifyActivity;
import cn.lrn517.techcomplatform.activity.BrowseTechPersonZoneActivity;
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
    private TextView noZone,state_1;
    private View view;
    private Button go;
    Call call;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;

    //测试数据
    int i = 0;

    public SpecialClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_special_classify, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        i = sharedPreferences.getInt("applyTPZState" , -1);
        Log.i("i" , "i = "+i);
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
        state_1 = view.findViewById(R.id.special_classify_state_1);
        go = view.findViewById(R.id.special_classify_go_button);
    }


    private void initEvent(){

        if( 2 == i ){
            noZone.setVisibility(View.GONE);
            state_1.setVisibility(View.GONE);
            zoneLayout.setVisibility(View.VISIBLE);
        }else if( 0 == i){
            noZone.setVisibility(View.VISIBLE);
            state_1.setVisibility(View.GONE);
            zoneLayout.setVisibility(View.GONE);
        }else{
            noZone.setVisibility(View.GONE);
            state_1.setVisibility(View.VISIBLE);
            zoneLayout.setVisibility(View.GONE);
        }

        applyfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( 0 == i ){
                    Intent intent = new Intent(getActivity() , ApplyForSpecialClassifyActivity.class);
                    startActivity(intent);
                }else if( 2 == i){
                    Intent intent = new Intent(getActivity() , AddTPZDetailActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "已申请专栏，请等待审核.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , BrowseTechPersonZoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tpzid" , sharedPreferences.getString("tpzid" , null));
                intent.putExtras(bundle);
                startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getActivity().getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        i = sharedPreferences.getInt("applyTPZState" , -1);
        Log.i("i" , "i = "+i);
    }
}
