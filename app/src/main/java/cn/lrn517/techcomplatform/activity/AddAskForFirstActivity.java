package cn.lrn517.techcomplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import javax.microedition.khronos.egl.EGLDisplay;

import cn.lrn517.techcomplatform.R;

public class AddAskForFirstActivity extends AppCompatActivity {


    private ImageView close;
    private TextView next;
    private EditText tdtitle,tdcontent;
    String tdtitle_s = "";
    String tdcontent_s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ask_for_first);
        initView();
        initEvent();
    }

    private void initView(){
        close = (ImageView) findViewById(R.id.add_ask_for_first_close);
        next = (TextView) findViewById(R.id.add_ask_for_first_next);
        tdtitle = (EditText) findViewById(R.id.add_ask_for_first_tdtitle);
        tdcontent = (EditText) findViewById(R.id.add_ask_for_first_tdcontent);
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
                }
            }
        });
    }
}
