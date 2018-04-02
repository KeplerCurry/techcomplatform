package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.TechPersonZoneDetailListAdapter;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaillist;
import cn.lrn517.techcomplatform.bean.techpersonzoneuserinfo;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseTechPersonZoneActivity extends AppCompatActivity {

    private TextView tpzname,tname,ualiase,detailcount;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    Call call;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    String tpzid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_tech_person_zone);
        initView();
        initEvent();
    }

    private void initView(){
        Bundle bundle = getIntent().getExtras();
        tpzid = bundle.getString("tpzid");
        toolbar = (Toolbar) findViewById(R.id.browse_tech_person_zone_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black);
        tpzname = (TextView) findViewById(R.id.browse_tech_person_zone_tpzname);
        tname = (TextView) findViewById(R.id.browse_tech_person_zone_tname);
        ualiase = (TextView) findViewById(R.id.browse_tech_person_zone_ualiase);
        detailcount = (TextView) findViewById(R.id.browse_tech_person_zone_detailcount);
        linearLayoutManager = new LinearLayoutManager(BrowseTechPersonZoneActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.browse_tech_person_zone_listview);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initEvent(){
        getUserInfo();
        getDetailList();
    }

    private void getUserInfo(){
        call = techPersonZoneModel.getTechPersonZoneUserinfo(tpzid);
        Callback<techpersonzoneuserinfo> techpersonzoneuserinfoCallback = new Callback<techpersonzoneuserinfo>() {
            @Override
            public void onResponse(Call<techpersonzoneuserinfo> call, Response<techpersonzoneuserinfo> response) {
                techpersonzoneuserinfo data = response.body();
                tpzname.setText(data.getTpzname().toString());
                toolbar.setTitle(data.getTpzname().toString());
                ualiase.setText(data.getUaliase().toString());
                tname.setText(data.getTname().toString());
                detailcount.setText(data.getListcount().toString()+"篇文章");
            }

            @Override
            public void onFailure(Call<techpersonzoneuserinfo> call, Throwable t) {

            }
        };
        call.enqueue(techpersonzoneuserinfoCallback);
    }

    private void getDetailList(){
        call = techPersonZoneModel.getTechPersonZoneDetailList(tpzid);
        Callback<List<techpersonzonedetaillist>> listCallback = new Callback<List<techpersonzonedetaillist>>() {
            @Override
            public void onResponse(Call<List<techpersonzonedetaillist>> call, Response<List<techpersonzonedetaillist>> response) {
                List data = response.body();
                TechPersonZoneDetailListAdapter techPersonZoneDetailListAdapter = new TechPersonZoneDetailListAdapter(BrowseTechPersonZoneActivity.this , data);
                recyclerView.setAdapter(techPersonZoneDetailListAdapter);
            }

            @Override
            public void onFailure(Call<List<techpersonzonedetaillist>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }


}
