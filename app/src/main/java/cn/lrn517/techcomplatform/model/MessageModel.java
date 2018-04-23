package cn.lrn517.techcomplatform.model;

import cn.lrn517.techcomplatform.service.MessageService;
import cn.lrn517.techcomplatform.service.serviceAddress;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lirun on 2018/4/10.
 */

public class MessageModel {

    private Retrofit retrofit;
    private MessageService messageService;

    public MessageModel(){
        retrofit = new Retrofit.Builder()
                .baseUrl(serviceAddress.SERVICE_ADDRESS+"/Json/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //发送消息
    public Call sendMessage(String userid ,String mineid ,String text){
        messageService = retrofit.create(MessageService.class);
        return messageService.sendMessage(userid, mineid, text);
    }

    //查看留言列表、信息等
    public Call loadMessageList(String mineid){
        messageService = retrofit.create(MessageService.class);
        return messageService.loadMessageList(mineid);
    }

    //通过指定uid查看留言
    public Call loadMessageByUid(String mineid, String userid){
        messageService = retrofit.create(MessageService.class);
        return messageService.loadMessageByUid(mineid, userid);
    }
}
