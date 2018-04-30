package cn.lrn517.techcomplatform.service;

import java.util.List;

import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.bean.commonEdit;
import cn.lrn517.techcomplatform.bean.homeattentiondata;
import cn.lrn517.techcomplatform.bean.loadUserInfo;
import cn.lrn517.techcomplatform.bean.userBuyedData;
import cn.lrn517.techcomplatform.bean.userInfo;
import cn.lrn517.techcomplatform.bean.userapplyfordata;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/3/19.
 */

public interface UserService {

    //手机号注册
    @POST("doRegForTelephone")
    Call<common> register(
            @Query("telephone") String telephone,
            @Query("password") String password
    );

    //登录
    @POST("doLogin")
    Call<userInfo> login(
            @Query("telephone") String telephone,
            @Query("password") String password
    );

    //修改个人信息
    @Multipart
    @POST("editUserInfo")
    Call<commonEdit> editUserInfo(
            @Part MultipartBody.Part file,
            @Query("uid") String uid,
            @Query("ualiase") String ualiase
    );

    //通过state查看收藏列表、点赞、关注信息
    @POST("load_attention_by_state")
    Call<List<commonAttentionData>> getAttentionDataList(
            @Query("state") int state,
            @Query("uid") String uid
    );

    //修改密码
    @POST("alter_user_password")
    Call<common> alterUserPassword(
            @Query("uid") String uid,
            @Query("oldpassword") String oldpassword,
            @Query("newpassword") String newpassword
    );

    //查看申请列表
    @POST("load_apply_for")
    Call<List<userapplyfordata>> getUserApplyFor(
            @Query("uid") String uid
    );

    //查看购买列表
    @POST("load_user_buyed")
    Call<List<userBuyedData>> getUserBuyedData(
            @Query("uid") String uid
    );

    //查看用户信息
    @POST("load_user_info")
    Call<loadUserInfo> getUserInfoData(
            @Query("uid") String uid
    );

    //获取用户个人发布的帖子、回答等列表
    @POST("load_user_send")
    Call<List<commonAttentionData>> getUserSendData(
            @Query("uid") String uid,
            @Query("state") int state
    );

    //点赞、收藏、关注(添加、取消)通用接口
    @POST("common_l_c_a")
    Call<common> common_l_c_a(
            @Query("flag") int flag,
            @Query("state") int state,
            @Query("id") String id,
            @Query("uid") String uid
    );

    //app关注内容
    @POST("attentionData")
    Call<homeattentiondata> attentionData(
            @Query("uid") String uid
    );
}
