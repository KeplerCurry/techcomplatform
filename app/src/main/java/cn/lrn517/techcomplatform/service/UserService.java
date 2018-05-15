package cn.lrn517.techcomplatform.service;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.bean.commonEdit;
import cn.lrn517.techcomplatform.bean.commonForUserSend;
import cn.lrn517.techcomplatform.bean.commonGetDataFromEdit;
import cn.lrn517.techcomplatform.bean.homeattentiondata;
import cn.lrn517.techcomplatform.bean.loadUserInfo;
import cn.lrn517.techcomplatform.bean.searchlist;
import cn.lrn517.techcomplatform.bean.userBuyedData;
import cn.lrn517.techcomplatform.bean.userInfo;
import cn.lrn517.techcomplatform.bean.userSearchHistory;
import cn.lrn517.techcomplatform.bean.userapplyfordata;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    //修改个人信息-无头像
    @POST("editUserInfoNoPic")
    Call<commonEdit> editUserInfoNoPic(
            @Query("uid") String uid,
            @Query("ualiase") String ualiase,
            @Query("usex") int usex,
            @Query("uspecialline") String uspecialline
    );

    //修改个人信息-有头像
    @Multipart
    @POST("editUserInfoByPic")
    Call<commonEdit> editUserInfoByPic(
            @Part MultipartBody.Part file,
            @Query("uid") String uid,
            @Query("ualiase") String ualiase,
            @Query("usex") int usex,
            @Query("uspecialline") String uspecialline
    );

    //申请实名认证
    @Multipart
    @POST("apply_for_real_name")
    Call<common> applyForRealName(
            @Part MultipartBody.Part file,
            @Query("uid") String uid
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

    //判断用户在查看其他用户页面中是否关注该用户
    @POST("getUserAttentionUser")
    Call<common> getUserAttentionUser(
            @Query("uid") String uid,
            @Query("id") String id
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

    //获取用户已发布的帖子、问题、专栏贴列表
    //state为标识 0->帖子 1->问题 2->回答 3->专栏贴
    @POST("getUserSendList")
    Call<List<commonForUserSend>> getUserSendList(
            @Query("state") int state,
            @Query("uid") String uid
    );

    //修改页面通过id获取数据
    @POST("getDataByIdFromEdit")
    Call<commonGetDataFromEdit> getDataByIdFromEdit(
            @Query("state") int state,
            @Query("id") String id
    );

    //修改发表内容通用接口
    //state为标识 0->帖子 1->问题 2->回答 3->专栏贴
    @Multipart
    @POST("editSendByState")
    Call<common> editSendByState(
            @PartMap Map<String, RequestBody> params
    );

    //获取用户历史搜索记录
    @POST("getUserSearch")
    Call<List<userSearchHistory>> getUserSearch(
            @Query("uid") String uid
    );

    //搜索功能
    @POST("search")
    Call<List<searchlist>> search(
            @Query("uid") String uid,
            @Query("searchtext") String searchtext,
            @Query("state") int state
    );
 }
