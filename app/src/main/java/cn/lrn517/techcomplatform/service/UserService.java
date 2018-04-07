package cn.lrn517.techcomplatform.service;

import java.util.List;

import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonAttentionData;
import cn.lrn517.techcomplatform.bean.userInfo;
import retrofit2.Call;
import retrofit2.http.POST;
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

    //通过state查看收藏列表、点赞、关注信息
    @POST("load_attention_by_state")
    Call<List<commonAttentionData>> getAttentionDataList(
            @Query("state") int state,
            @Query("uid") String uid
    );
}
