package cn.lrn517.techcomplatform.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.activity.LoginAndRegisterActivity;
import cn.lrn517.techcomplatform.bean.userInfo;
import cn.lrn517.techcomplatform.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private View view;
    private EditText telephone,password;
    private Button submit;
    private UserModel userModel = new UserModel();
    private Call call;
    private SharedPreferences sharedPreferences;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        telephone = view.findViewById(R.id.login_telephone);
        password = view.findViewById(R.id.login_password);
        submit = view.findViewById(R.id.login_submit);
    }

    private void initEvent(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telephone_number = telephone.getText().toString();
                String password_number = password.getText().toString();
                call = userModel.login(telephone_number,password_number);
                Callback<userInfo> userInfoCallback = new Callback<userInfo>() {
                    @Override
                    public void onResponse(Call<userInfo> call, Response<userInfo> response) {
                        userInfo data = response.body();
                        if( 0 == data.getSuccess()) {
                            Toast.makeText(getActivity() , "登录失败！" , Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("data" , data.toString());
                            saveUserInfo(data);
                            Toast.makeText(getActivity() , "登录成功" , Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<userInfo> call, Throwable t) {

                    }
                };
                call.enqueue(userInfoCallback);
            }
        });
    }

    private void saveUserInfo(userInfo data){
        sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid" , data.getUid().toString());
        editor.putString("ualiase" , data.getUaliase().toString());
        editor.putString("uphoto",  data.getUphoto().toString());
        editor.putString("ispassed" , data.getIspassed().toString());
        editor.putString("ulevel" , data.getUlevel().toString());
        editor.putString("uexp" , data.getUexp().toString());
        editor.putString("ulogintime" , data.getUlogintime().toString());
        editor.putString("uloginip" , data.getUloginip().toString());
        editor.commit();
    }

}
