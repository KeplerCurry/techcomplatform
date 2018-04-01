package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.techclassifydata;
import cn.lrn517.techcomplatform.model.DetailModel;
import cn.lrn517.techcomplatform.model.TechPersonZoneModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyForSpecialClassifyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button;
    private Spinner spinner;
    private EditText tpzname;
    private DetailModel detailModel = new DetailModel();
    private TechPersonZoneModel techPersonZoneModel = new TechPersonZoneModel();
    private Call call;
    private ArrayAdapter<String> tnameadapter;
    private String[] tid;
    private String[] tname;
    String tid_s = "";
    String tpzname_s = "";

    //测试数据
    String uid = "20180319155823";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_special_classify);
        initView();
        initEvent();
    }

    private void initView(){
        tpzname = (EditText) findViewById(R.id.apply_for_special_classify_tpzname);
        spinner = (Spinner) findViewById(R.id.apply_for_special_classify_spinner);
        toolbar = (Toolbar) findViewById(R.id.apply_for_special_classify_toolbar);
        toolbar.setTitle("申请专栏-XXX");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black);

        button = (Button) findViewById(R.id.apply_for_special_classify_button);
    }

    private void initEvent(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getTechClassify();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tid_s = tid[i];
                Log.i("tid" , ""+tid_s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tid_s = tid[0];
                Log.i("tid" , ""+tid_s);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendApplyForSpecialClassify();
            }
        });

    }

    private void getTechClassify(){
        call = detailModel.getTechClassifyData();
        Callback<List<techclassifydata>> listCallback = new Callback<List<techclassifydata>>() {
            @Override
            public void onResponse(Call<List<techclassifydata>> call, Response<List<techclassifydata>> response) {
                List data = response.body();
                tid = new String[data.size()];
                tname = new String[data.size()];
                for(int i = 0 ; i < data.size() ; i++){
                    final techclassifydata body = (techclassifydata) data.get(i);
                    tid[i] = body.getTid().toString();
                    tname[i] = body.getTname().toString();
                }
                tnameadapter = new ArrayAdapter<String>( ApplyForSpecialClassifyActivity.this , R.layout.support_simple_spinner_dropdown_item , tname);
                tnameadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(tnameadapter);
            }

            @Override
            public void onFailure(Call<List<techclassifydata>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);
    }

    private void sendApplyForSpecialClassify(){
        tpzname_s = tpzname.getText().toString();
        call = techPersonZoneModel.applyForTechPersonZone(uid , tpzname_s , tid_s);
        Callback<common> commonCallback = new Callback<common>() {
            @Override
            public void onResponse(Call<common> call, Response<common> response) {
                common data = response.body();
                if( 1 == data.getSuccess()){
                    Toast.makeText(ApplyForSpecialClassifyActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                    //这时候在share里保存申请状态，防止重复申请

                    finish();
                }
            }

            @Override
            public void onFailure(Call<common> call, Throwable t) {

            }
        };
        call.enqueue(commonCallback);
    }
}
