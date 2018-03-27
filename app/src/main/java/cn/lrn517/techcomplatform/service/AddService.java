package cn.lrn517.techcomplatform.service;

import cn.lrn517.techcomplatform.bean.addAnswerResult;
import cn.lrn517.techcomplatform.bean.addTechDetailResult;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/3/27.
 */

public interface AddService {

    //发送提问帖
    @POST("send_ask")
    Call<addAnswerResult> sendAsk(
            @Query("tuid") String tuid,
            @Query("tdtitle") String tdtitle,
            @Query("tdcontent") String tdcontent,
            @Query("tid") String tid
    );

    //发送技术贴
    @POST("send_technology_detail")
    Call<addTechDetailResult> sendTechnologyDetail(
            @Query("tuid")  String tuid,
            @Query("tdtitle")  String tdtitle,
            @Query("tdcontent")  String tdcontent,
            @Query("tid")  String tid,
            @Query("isfree")  int isfree,
            @Query("price")  double price
    );

}
