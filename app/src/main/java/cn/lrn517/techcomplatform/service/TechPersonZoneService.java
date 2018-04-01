package cn.lrn517.techcomplatform.service;

import cn.lrn517.techcomplatform.bean.common;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/4/1.
 */

public interface TechPersonZoneService {

    @POST("apply_for_tech_person_zone")
    Call<common> applyfortechpersonzone(
            @Query("uid") String uid,
            @Query("tpzname") String tpzname,
            @Query("tid") String tid
    );
}
