package cn.lrn517.techcomplatform.model;


import cn.lrn517.techcomplatform.service.TechPersonZoneService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lirun on 2018/4/1.
 */

public class TechPersonZoneModel {

    private Retrofit retrofit;
    private TechPersonZoneService techPersonZoneService;

    public TechPersonZoneModel(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://47.95.198.79/techcomplatformAPI/Json/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //申请个人专栏
    public Call applyForTechPersonZone(String uid , String tpzname, String tid){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.applyfortechpersonzone(uid, tpzname, tid);
    }
}
