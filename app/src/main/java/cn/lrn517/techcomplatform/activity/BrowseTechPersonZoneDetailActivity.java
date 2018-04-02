package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaildata;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseTechPersonZoneDetailActivity extends AppCompatActivity {

    private TextView tpzdtitle,ualiase,tpzname,tpzdcontent,tpzdfirsttime;
    private Toolbar toolbar;
    private LinearLayout tocomment;
    Call call;
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    String tpzdid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_tech_person_zone_detail);
        initView();
        initEvent();
    }

    private void initView(){
        tocomment = (LinearLayout) findViewById(R.id.browse_tech_person_zone_detail_tocomment);
        toolbar = (Toolbar) findViewById(R.id.browse_tech_person_zone_detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black);
        tpzdtitle = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzdtitle);
        ualiase = (TextView) findViewById(R.id.browse_tech_person_zone_detail_ualiase);
        tpzname = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzname);
        tpzdcontent = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzdcontent);
        tpzdfirsttime = (TextView) findViewById(R.id.browse_tech_person_zone_detail_tpzdfirsttime);
        Bundle bundle = getIntent().getExtras();
        tpzdid = bundle.getString("tpzdid");
    }

    private void initEvent(){
        call = techPersonZoneModel.getTechPersonZoneDetailData(tpzdid);
        Callback<techpersonzonedetaildata> techpersonzonedetaildataCallback = new Callback<techpersonzonedetaildata>() {
            @Override
            public void onResponse(Call<techpersonzonedetaildata> call, Response<techpersonzonedetaildata> response) {
                techpersonzonedetaildata data = response.body();
                toolbar.setTitle(data.getTpzdtitle().toString());
                tpzdtitle.setText(data.getTpzdtitle().toString());
                ualiase.setText(data.getUaliase().toString());
                tpzdfirsttime.setText("发表于  "+data.getTpzdfirsttime().toString());
                tpzdcontent.setText(data.getTpzdcontent().toString());
            }

            @Override
            public void onFailure(Call<techpersonzonedetaildata> call, Throwable t) {

            }
        };
        call.enqueue(techpersonzonedetaildataCallback);

        tocomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseTechPersonZoneDetailActivity.this  , CommentTPZDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tpzdid" , tpzdid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}
