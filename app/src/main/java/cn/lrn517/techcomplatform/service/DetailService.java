package cn.lrn517.techcomplatform.service;

import java.util.List;

import cn.lrn517.techcomplatform.bean.homeData;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/3/20.
 */

public interface DetailService {
    //获取热门页面数据
    @POST("hotData")
    Call<List<homeData>> getHotData(
            @Query("page") int page
    );
}
