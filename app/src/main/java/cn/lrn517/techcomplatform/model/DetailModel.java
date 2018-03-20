package cn.lrn517.techcomplatform.model;

import cn.lrn517.techcomplatform.service.DetailService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lirun on 2018/3/20.
 */

public class DetailModel {

    private Retrofit retrofit;
    private DetailService detailService;

    public DetailModel(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://119.29.60.195/techcomplatformAPI/Json/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //获取热门数据
    public Call getHotData( int page ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getHotData(page);
    }
}
