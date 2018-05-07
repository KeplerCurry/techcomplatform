package cn.lrn517.techcomplatform.model;

import java.util.Map;

import cn.lrn517.techcomplatform.service.AddService;
import cn.lrn517.techcomplatform.service.serviceAddress;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lirun on 2018/3/27.
 */

public class AddModel {

    private Retrofit retrofit;
    private AddService addService;

    public AddModel(){
        retrofit = new Retrofit.Builder()
                .baseUrl(serviceAddress.SERVICE_ADDRESS+"/Json/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //发送提问帖
    public Call sendAsk(String tuid , String tdtitle , String tdcontent , String tid){
        addService = retrofit.create(AddService.class);
        return addService.sendAsk(tuid, tdtitle, tdcontent, tid);
    }

    //发送技术贴
    public Call sendTechnologyDetail(Map<String, RequestBody> params){
        addService = retrofit.create(AddService.class);
        return addService.sendTechnologyDetail(params);
    }

}
