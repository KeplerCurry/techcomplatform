package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobApplication;
import com.mob.MobSDK;

import java.util.Timer;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.LoginAndRegisterActivity;
import cn.lrn517.techcomplatform.activity.PaddingInfoActivity;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.userInfo;
import cn.lrn517.techcomplatform.model.UserModel;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private View view;
    Context context ;
    private Button get_test_code,evidence_test_code;
    private EditText telephone,test_code,password;
    String telephone_number;
    String test_code_number;
    private EventHandler eventHandler;
    private UserModel userModel = new UserModel();
    private Call call;

    private TimeCount timeCount = new TimeCount(60000,1000);

    private SharedPreferences sharedPreferences;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        //创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取到验证码的回调
                        Log.i("=============", "afterEvent: 获取成功");
                        getActivity().runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                            Toast.makeText(getActivity(), "获取验证码成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //提交验证码的回调
                        getActivity().runOnUiThread(new Thread(){
                            @Override
                            public void run() {
                              call = userModel.register(telephone_number , password.getText().toString());
                              Callback<common> commonCallback = new Callback<common>() {
                                    @Override
                                    public void onResponse(Call<common> call, Response<common> response) {
                                        common data = response.body();
                                        int success = data.getSuccess();
                                        switch ( success ){
                                            case 0:
                                                Toast.makeText( getActivity() , "注册失败" , Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                Toast.makeText( getActivity() , "注册成功！" , Toast.LENGTH_SHORT).show();
                                                login();
                                                break;
                                            case 2:
                                                Toast.makeText( getActivity() , "手机号已注册！" , Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<common> call, Throwable t) {
                                        Toast.makeText( getActivity() , "请求失败！" , Toast.LENGTH_SHORT).show();
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
        initEvent();
        return view;
    }

    private void initView(View view){
        get_test_code = view.findViewById(R.id.register_get_test_code);
        evidence_test_code = view.findViewById(R.id.register_evidence_test_code);
        telephone = view.findViewById(R.id.register_telephone);
        test_code = view.findViewById(R.id.register_test_code);
        password = view.findViewById(R.id.register_password);
    }




    private void initEvent(){
        get_test_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telephone_number = telephone.getText().toString();
                if( "".equals(telephone_number)){
                    Toast.makeText(getActivity(), "请输入手机号！", Toast.LENGTH_SHORT).show();
                }else{
                    if( isMobile(telephone_number)){
                        SMSSDK.getVerificationCode("86" , telephone_number);
                        timeCount.start();
                    }else{
                        Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        evidence_test_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test_code_number = test_code.getText().toString();
                SMSSDK.submitVerificationCode("86" , telephone_number , test_code_number);
            }
        });
    }

    private static boolean isMobile(String telephone){
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(telephone)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return telephone.matches(num);
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            get_test_code.setClickable(false);
            get_test_code.setText(millisUntilFinished / 1000 +"秒后重新获取");
        }

        @Override
        public void onFinish() {
            get_test_code.setText("重新获取验证码");
            get_test_code.setClickable(true);
        }
    }

    private void login(){
        call = userModel.login(telephone_number,password.getText().toString());
        Callback<userInfo> userInfoCallback = new Callback<userInfo>() {
            @Override
            public void onResponse(Call<userInfo> call, Response<userInfo> response) {
                userInfo data = response.body();
                if( 0 == data.getSuccess()) {
                    //Toast.makeText(getActivity() , "登录失败！" , Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("data" , data.toString());
                    saveUserInfo(data);
                    //Toast.makeText(getActivity() , "登录成功" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity() , PaddingInfoActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<userInfo> call, Throwable t) {

            }
        };
        call.enqueue(userInfoCallback);
    }

    private void saveUserInfo(userInfo data){
        sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid" , data.getUid());
        editor.putString("ualiase" , data.getUaliase());
        editor.putString("uphoto",  data.getUphoto());
        editor.putString("ispassed" , data.getIspassed());
        editor.putString("ulevel" , data.getUlevel());
        editor.putString("uexp" , data.getUexp());
        editor.putString("utype" , data.getUtype());
        editor.putString("ulogintime" , data.getUlogintime());
        editor.putString("uloginip" , data.getUloginip());
        editor.putString("tpzid" , data.getTpzid());
        editor.putInt("applyTPZState" , data.getApplyTPZState());
        editor.putInt("usex" , data.getUsex());
        editor.putString("uspecialline" , data.getUspecialline());
        editor.apply();
    }
}
