package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;

import cn.lrn517.techcomplatform.R;

public class AddAskForFirstActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView next;
    private EditText tdtitle,tdcontent;
    String tdtitle_s = "";
    String tdcontent_s = "";
    public static AddAskForFirstActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ask_for_first);
        activity = this;
        initView();
        initEvent();
    }

    private void initView(){
        next = (TextView) findViewById(R.id.add_ask_for_first_next);
        tdtitle = (EditText) findViewById(R.id.add_ask_for_first_tdtitle);
        tdcontent = (EditText) findViewById(R.id.add_ask_for_first_tdcontent);
        toolbar = findViewById(R.id.add_ask_for_first_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initEvent(){

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tdcontent_s = tdcontent.getText().toString();
                tdtitle_s = tdtitle.getText().toString();
                if( !"".equals(tdcontent_s) && !"".equals(tdtitle_s)){
                    Intent intent = new Intent( AddAskForFirstActivity.this , AddAskForFinishActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tdtitle" , tdtitle_s);
                    bundle.putString("tdcontent" , tdcontent_s);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddAskForFirstActivity.this, "请将问题标题或问题描述填写完整！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
