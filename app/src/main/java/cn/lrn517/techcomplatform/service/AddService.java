package cn.lrn517.techcomplatform.service;

import java.util.Map;

import cn.lrn517.techcomplatform.bean.addAnswerResult;
import cn.lrn517.techcomplatform.bean.addTechDetailResult;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/3/27.
 */

public interface AddService {

    //发送提问帖
    @Multipart
    @POST("send_ask")
    Call<addAnswerResult> sendAsk(
            @PartMap Map<String,RequestBody> params
    );

    //发送技术贴
    @Multipart
    @POST("send_technology_detail")
    Call<addTechDetailResult> sendTechnologyDetail(
            @PartMap Map<String, RequestBody> params
    );

}
