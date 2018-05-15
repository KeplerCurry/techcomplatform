package cn.lrn517.techcomplatform.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.addAnswerResult;
import cn.lrn517.techcomplatform.bean.techclassifydata;
import cn.lrn517.techcomplatform.model.AddModel;
import cn.lrn517.techcomplatform.model.DetailModel;
import it.sephiroth.android.library.easing.Linear;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAskForFinishActivity extends AppCompatActivity {

    private ImageView send;
    private Spinner spinner;
    private Toolbar toolbar;
    private CheckBox check_yes,check_no;
    private LinearLayout priceLayout;
    private EditText ePrice;
    private DetailModel detailModel = new DetailModel();
    private AddModel addModel = new AddModel();
    Call call;
    String tdtitle = "";
    String tdcontent = "";
    String tid_s = "";
    int price = 0;
    int isfree;
    private ArrayAdapter<String> tnameadapter;
    private String[] tid;
    private String[] tname;
    private SharedPreferences sharedPreferences;
    Map<String, RequestBody> params = new HashMap<>();

    String tuid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ask_for_finish);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = findViewById(R.id.add_ask_for_finish_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send = (ImageView) findViewById(R.id.add_ask_for_finish_send);
        spinner = (Spinner) findViewById(R.id.add_ask_for_finish_spinner);
        check_yes = findViewById(R.id.add_ask_for_finish_check_yes);
        check_no = findViewById(R.id.add_ask_for_finish_check_no);
        priceLayout = findViewById(R.id.add_ask_for_finish_price_layout);
        ePrice = findViewById(R.id.add_ask_for_finish_price);
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        tuid = sharedPreferences.getString("uid" , null );
    }

    private void initEvent(){

        priceLayout.setVisibility(View.GONE);
        getStringData();

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
                tnameadapter = new ArrayAdapter<String>( AddAskForFinishActivity.this , R.layout.support_simple_spinner_dropdown_item , tname);
                tnameadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(tnameadapter);
            }

            @Override
            public void onFailure(Call<List<techclassifydata>> call, Throwable t) {

            }
        };
        call.enqueue(listCallback);

        check_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isfree = 0;
                    priceLayout.setVisibility(View.VISIBLE);
                    check_no.setChecked(false);
                }else{
                    isfree = 1;
                    priceLayout.setVisibility(View.GONE);
                    check_no.setChecked(true);
                }
            }
        });

        check_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isfree = 1;
                    priceLayout.setVisibility(View.GONE);
                    check_yes.setChecked(false);
                }else{
                    isfree = 0;
                    priceLayout.setVisibility(View.VISIBLE);
                    check_yes.setChecked(true);
                }
            }
        });


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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddAskForFinishActivity.this);
                dialog.setTitle("确认发布？");
                dialog.setNegativeButton("我在想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.setPositiveButton("确定发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendAnswer();
                    }
                });
                dialog.show();
            }
        });
    }

    private void getStringData(){
        Bundle bundle = getIntent().getExtras();
        tdcontent = bundle.getString("tdcontent");
        tdtitle = bundle.getString("tdtitle");
    }


    private void sendAnswer(){
        if( 0 == isfree){
            price = Integer.valueOf(ePrice.getText().toString());
        }
        params.put("tuid" , toRequestBody(tuid));
        params.put("tdtitle" , toRequestBody(tdtitle));
        params.put("tdcontent" , toRequestBody(tdcontent));
        params.put("tid" , toRequestBody(tid_s));
        params.put("isfree" , toRequestBody(String.valueOf(isfree)));
        params.put("price",toRequestBody(String.valueOf(price)));
        call = addModel.sendAsk(params);
        Callback<addAnswerResult> addAnswerResultCallback = new Callback<addAnswerResult>() {
            @Override
            public void onResponse(Call<addAnswerResult> call, Response<addAnswerResult> response) {
                addAnswerResult data = response.body();
                if( 1 == data.getSuccess() ){
                    Intent intent = new Intent( AddAskForFinishActivity.this , AskDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tdid" , data.getTdid().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(AddAskForFinishActivity.this , "发布提问成功！" , Toast.LENGTH_SHORT).show();
                    AddAskForFirstActivity.activity.finish();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<addAnswerResult> call, Throwable t) {

            }
        };
        call.enqueue(addAnswerResultCallback);
    }

    private static RequestBody toRequestBody(String t){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain") , t);
        return requestBody;
    }

}
