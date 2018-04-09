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

    //通过state查看收藏列表、点赞、关注信息
    public Call getAttentionDataList(int state, String uid){
        userService = retrofit.create(UserService.class);
        return userService.getAttentionDataList(state,uid);
    }

    //修改密码
    public Call alterUserPassword(String uid, String oldpassword, String newpassword){
        userService = retrofit.create(UserService.class);
        return userService.alterUserPassword(uid, oldpassword, newpassword);
    }

    //查看申请列表
    public Call getUserApplyFor(String uid){
        userService = retrofit.create(UserService.class);
        return userService.getUserApplyFor(uid);
    }

    //查看购买列表
    public Call getUserBuyedData(String uid){
        userService = retrofit.create(UserService.class);
        return userService.getUserBuyedData(uid);
    }

    //查看用户信息
    public Call getUserInfoData(String uid){
        userService = retrofit.create(UserService.class);
        return userService.getUserInfoData(uid);
    }

    //获取用户个人发布的帖子、回答等列表
    public Call getUserSendData(String uid, int state){
        userService = retrofit.create(UserService.class);
        return userService.getUserSendData(uid, state);
    }
}
