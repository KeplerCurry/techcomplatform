package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobApplication;
import com.mob.MobSDK;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.LoginAndRegisterActivity;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private View view;
    Context context ;
    private Button get_test_code,evidence_test_code;
    private EditText telephone,test_code;
    String telephone_number;
    String test_code_number;
    private EventHandler eventHandler;

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
                              Toast.makeText(getActivity(), "验证成功!", Toast.LENGTH_SHORT).show();

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
    }


    protected void onDestory(){
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private void initEvent(){
        get_test_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telephone_number = telephone.getText().toString();
                //Toast.makeText(getActivity() , telephone_number , Toast.LENGTH_SHORT).show();
                Log.i("telephone", telephone_number+"");
                SMSSDK.getVerificationCode("86" , telephone_number);
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
}
