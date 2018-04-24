package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.time.temporal.IsoFields;
import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.techclassifydata;
import cn.lrn517.techcomplatform.model.DetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTechnologyDetailFirstActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView next;
    private Spinner spinner;
    private CheckBox yes,no;
    private EditText price;
    private LinearLayout pricelayout;
    private DetailModel detailModel = new DetailModel();
    Call call;
    private ArrayAdapter<String> tnameadapter;
    private String[] tid;
    private String[] tname;

    int isfree;
    double price_num;
    String tname_s;
    String tid_s;

    public static AddTechnologyDetailFirstActivity activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_technology_detail_first);
        activity = this;
        initView();
        initEvent();
    }

    private void initView(){
        next = (TextView) findViewById(R.id.add_technology_detail_first_next);
        spinner = (Spinner) findViewById(R.id.add_technology_detail_first_spinner);
        yes = (CheckBox) findViewById(R.id.add_technology_detail_first_check_yes);
        no = (CheckBox) findViewById(R.id.add_technology_detail_first_check_no);
        price = (EditText) findViewById(R.id.add_technology_detail_first_price);
        pricelayout = findViewById(R.id.add_technology_detail_first_price_layout);
        pricelayout.setVisibility(View.GONE);
        toolbar = findViewById(R.id.add_technology_detail_first_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initEvent(){
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
                tnameadapter = new ArrayAdapter<String>(AddTechnologyDetailFirstActivity.this , R.layout.support_simple_spinner_dropdown_item , tname );
                tnameadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(tnameadapter);
            }

            @Override
            public void onFailure(Call<List<techclassifydata>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tid_s = tid[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tid_s = tid[0];
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTechnologyDetailFirstActivity.this , AddTechnologyDetailFinishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid" , tid_s);
                bundle.putInt("isfree" , isfree);
                if( 1 == isfree ){

                }
                else{
                    price_num = Double.valueOf(price.getText().toString());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        selectIsfree();
    }

    private void selectIsfree(){
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b ){
                    no.setChecked(false);
                    pricelayout.setVisibility(View.GONE);
                    isfree = 1;
                }else{
                    pricelayout.setVisibility(View.VISIBLE);
                    isfree = 0;
                }
            }
        });

        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b ){
                    yes.setChecked(false);
                    pricelayout.setVisibility(View.VISIBLE);
                    isfree = 0;
                }
                else{
                    pricelayout.setVisibility(View.GONE);
                    isfree = 1;
                }
            }
        });
    }
}
