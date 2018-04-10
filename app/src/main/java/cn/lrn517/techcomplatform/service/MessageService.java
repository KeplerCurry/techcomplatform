package cn.lrn517.techcomplatform.service;

import java.util.List;

import cn.lrn517.techcomplatform.bean.loadMessageByUid;
import cn.lrn517.techcomplatform.bean.loadMessageData;
import cn.lrn517.techcomplatform.bean.sendMessageData;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/4/10.
 */

public interface MessageService {

    //发送留言
    @POST("send_message")
    Call<sendMessageData> sendMessage(
            @Query("userid") String userid,
            @Query("mineid") String mineid,
            @Query("text") String text
    );

    //查看留言列表、信息等
    @POST("load_message_list")
    Call<loadMessageData> loadMessageList(
            @Query("mineid") String mineid
    );

    //通过制定uid查看留言
    @POST("load_message_by_uid")
    Call<List<loadMessageByUid>> loadMessageByUid(
            @Query("mineid") String mineid,
            @Query("userid") String userid
    );
}
