package cn.lrn517.techcomplatform.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.input.InputManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.adapter.MessageViewAdapter;
import cn.lrn517.techcomplatform.bean.loadMessageByUid;
import cn.lrn517.techcomplatform.bean.sendMessageData;
import cn.lrn517.techcomplatform.model.MessageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText eText;
    private ImageView send;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private MessageViewAdapter messageViewAdapter;
    private InputMethodManager inputMethodManager;
    private SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Call call;
    private MessageModel messageModel = new MessageModel();

    private String mineid = "";
    private String userid = "";
    private String text = "";
    private String ualiase = "";
    List datacommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = findViewById(R.id.send_message_toolbar);
        eText = findViewById(R.id.send_message_text);
        send = findViewById(R.id.send_message_send);
        linearLayoutManager = new LinearLayoutManager(MessageActivity.this);
        recyclerView = findViewById(R.id.send_message_recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = findViewById(R.id.send_message_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorBasic));
        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("userid");
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        mineid = sharedPreferences.getString("uid" , null);
        ualiase = sharedPreferences.getString("ualiase" , null);
        toolbar = (Toolbar) findViewById(R.id.send_message_toolbar);
        toolbar.setTitle("私信");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initEvent(){
        swipeRefreshLayout.setRefreshing(true);
        getData();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                text = eText.getText().toString();
                if( "".equals(text)){

                }else{
                    swipeRefreshLayout.setRefreshing(true);
                    call = messageModel.sendMessage(userid,mineid,text);
                    Callback<sendMessageData> callback1 = new Callback<sendMessageData>() {
                        @Override
                        public void onResponse(Call<sendMessageData> call, Response<sendMessageData> response) {
                            sendMessageData data = response.body();
                            if( 1 == data.getSuccess() ){
                                eText.setText("");
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                                datacommon.clear();
                                getData();
                                Toast.makeText(MessageActivity.this, "发送消息成功！", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MessageActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<sendMessageData> call, Throwable t) {

                        }
                    };
                    call.enqueue(callback1);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                Toast.makeText(MessageActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){
        call = messageModel.loadMessageByUid(mineid,userid);
        Callback<List<loadMessageByUid>> callback = new Callback<List<loadMessageByUid>>() {
            @Override
            public void onResponse(Call<List<loadMessageByUid>> call, Response<List<loadMessageByUid>> response) {
                datacommon = response.body();
                swipeRefreshLayout.setRefreshing(false);
                messageViewAdapter = new MessageViewAdapter(MessageActivity.this,datacommon,2,mineid);
                recyclerView.setAdapter(messageViewAdapter);
                recyclerView.scrollToPosition(messageViewAdapter.getItemCount()-1);

            }
            @Override
            public void onFailure(Call<List<loadMessageByUid>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }

}
