package cn.lrn517.techcomplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText eTelephone,eOldpassword,eNewpassword,eIdentifyingcode;
    private Button send_code,submit;
    private String telephone;
    private String newpassword;
    private String oldpassword;
    private String identifyingcode;
    private UserModel userModel = new UserModel();
    private EventHandler eventHandler;
    private Call call;
    //测试数据
    private String uid = "20180319124601";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.alter_password_toolbar);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black);
        eTelephone = (EditText) findViewById(R.id.alter_password_telephone);
        eIdentifyingcode = (EditText) findViewById(R.id.alter_password_identifying_code);
        eNewpassword = (EditText) findViewById(R.id.alter_password_newpassword);
        eOldpassword = (EditText) findViewById(R.id.alter_password_oldpassword);
        send_code = (Button) findViewById(R.id.alter_password_get_code);
        submit = (Button) findViewById(R.id.alter_password_send);
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取到验证码的回调
                        Log.i("=============", "afterEvent: 获取成功");
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                                Toast.makeText(AlterPasswordActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //提交验证码的回调
                        runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                                Toast.makeText(AlterPasswordActivity.this, "验证成功!", Toast.LENGTH_SHORT).show();
                                call = userModel.alterUserPassword(uid,oldpassword,newpassword);
                                Callback<common> commonCallback = new Callback<common>() {
                                    @Override
                                    public void onResponse(Call<common> call, Response<common> response) {
                                        common data = response.body();
                                        int success = data.getSuccess();
                                        switch ( success ){
                                            case 0:
                                                Toast.makeText( AlterPasswordActivity.this , "修改失败" , Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                Toast.makeText( AlterPasswordActivity.this , "修改成功！" , Toast.LENGTH_SHORT).show();
                                                finish();
                                                break;
                                            case 2:
                                                Toast.makeText( AlterPasswordActivity.this , "原密码错误！" , Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<common> call, Throwable t) {
                                        Toast.makeText( AlterPasswordActivity.this , "请求失败！" , Toast.LENGTH_SHORT).show();
                                    }
                                };
                                call.enqueue(commonCallback);

                            }
                        });
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initEvent(){
        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telephone = eTelephone.getText().toString();
                SMSSDK.getVerificationCode("86" , telephone);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifyingcode = eIdentifyingcode.getText().toString();
                oldpassword = eOldpassword.getText().toString();
                newpassword = eNewpassword.getText().toString();
                SMSSDK.submitVerificationCode("86" , telephone , identifyingcode);
            }
        });
    }

    /*
     *销毁Activity后解除SMSSDK绑定，防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
