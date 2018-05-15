package cn.lrn517.techcomplatform.model;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.lrn517.techcomplatform.service.UserService;
import cn.lrn517.techcomplatform.service.serviceAddress;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(serviceAddress.SERVICE_ADDRESS+"/Json/json/")
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

    //修改用户信息-有头像
    public Call editUserInfoByPic(MultipartBody.Part file,String uid,String ualiase,int usex,String uspecialline){
        userService = retrofit.create(UserService.class);
        return userService.editUserInfoByPic(file, uid, ualiase, usex, uspecialline);
    }

    //修改用户信息-无头像
    public Call editUserInfoNoPic(String uid,String ualiase,int usex,String uspecialline){
        userService = retrofit.create(UserService.class);
        return userService.editUserInfoNoPic(uid, ualiase, usex, uspecialline);
    }

    //申请实名认证
    public Call applyForRealName(MultipartBody.Part file , String uid){
        userService = retrofit.create(UserService.class);
        return userService.applyForRealName(file, uid);
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

    //判断用户在查看其他用户页面中是否关注该用户
    public Call getUserAttentionUser(String uid, String id){
        userService = retrofit.create(UserService.class);
        return userService.getUserAttentionUser(uid, id);
    }

    //获取用户个人发布的帖子、回答等列表
    public Call getUserSendData(String uid, int state){
        userService = retrofit.create(UserService.class);
        return userService.getUserSendData(uid, state);
    }

    //点赞、收藏、关注(添加、取消)通用接口
    public Call common_like_collect_attention(int flag , int state, String id , String uid){
        userService = retrofit.create(UserService.class);
        return userService.common_l_c_a(flag, state, id, uid);
    }

    //app关注内容
    public Call attentionData(String uid){
        userService = retrofit.create(UserService.class);
        return userService.attentionData(uid);
    }

    //获取用户已发布的帖子、问题、专栏贴列表
    //state为标识 0->帖子 1->问题 2->回答 3->专栏贴
    public Call getUserSendList(int state ,String uid){
        userService = retrofit.create(UserService.class);
        return userService.getUserSendList(state, uid);
    }

    //修改页面通过id获取数据
    public Call getDataByIdFromEdit(int state , String id){
        userService = retrofit.create(UserService.class);
        return userService.getDataByIdFromEdit(state, id);
    }

    //修改发表内容通用接口
    //state为标识 0->帖子 1->问题 2->回答 3->专栏贴
    public Call editSendByState(Map<String, RequestBody> params){
        userService = retrofit.create(UserService.class);
        return userService.editSendByState(params);
    }

    //获取用户历史搜索记录
    public Call getUserSearch(String uid){
        userService = retrofit.create(UserService.class);
        return userService.getUserSearch(uid);
    }

    //搜索
    public Call search(String uid, String searchtext , int state){
        userService = retrofit.create(UserService.class);
        return userService.search(uid, searchtext,state);
    }
}
