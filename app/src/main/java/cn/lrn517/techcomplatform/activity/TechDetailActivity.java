package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.techDetailData;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechDetailActivity extends AppCompatActivity {

    private TextView ualiase,tdtitle,tname,tdcontent,tdfirsttime;
    private String tdid;
    private Call call;
    private DetailModel detailModel = new DetailModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_detail);
        initView();
        initEvent();
    }

    private void initView(){
        ualiase = (TextView) findViewById(R.id.tech_detail_ualiase);
        tdcontent = (TextView) findViewById(R.id.tech_detail_tdcontent);
        tdfirsttime = (TextView) findViewById(R.id.tech_detail_tdfirsttime);
        tname = (TextView) findViewById(R.id.tech_detail_tname);
        tdtitle = (TextView) findViewById(R.id.tech_detail_tdtitle);
    }

    private void initEvent(){
        Bundle bundle = getIntent().getExtras();
        tdid = bundle.getString("tdid");
        call = detailModel.getTechDetailData(tdid);
        Callback<techDetailData> techDetailDataCallback = new Callback<techDetailData>() {
            @Override
            public void onResponse(Call<techDetailData> call, Response<techDetailData> response) {
                techDetailData data = response.body();
                ualiase.setText(data.getUaliase().toString());
                tdcontent.setText(data.getTdcontent().toString());
                tdfirsttime.setText(data.getTdfirsttime().toString());
                tname.setText(data.getTname().toString());
                tdtitle.setText(data.getTdtitle().toString());
            }

            @Override
            public void onFailure(Call<techDetailData> call, Throwable t) {

            }
        };
        call.enqueue(techDetailDataCallback);
    }
}
