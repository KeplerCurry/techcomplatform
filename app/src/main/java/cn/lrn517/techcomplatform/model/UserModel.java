package cn.lrn517.techcomplatform.model;

import cn.lrn517.techcomplatform.service.UserService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lirun on 2018/3/19.
 */

public class UserModel {

    private Retrofit retrofit;
    private UserService userService;

    public UserModel(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://47.95.198.79/techcomplatformAPI/Json/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //手机号注册
    public Call register(String telephone , String password){
        userService = retrofit.create(UserService.class);
        return userService.register(telephone,password);
    }
    //登录
    public Call login(String telephone, String password){
        userService = retrofit.create(UserService.class);
        return userService.login(telephone,password);
    }
}
