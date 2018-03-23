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

    //获取技术贴详情
    public Call getTechDetailData( String tdid ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getTechDetailData(tdid);
    }

    //获取技术贴首次评论内容列表
    public Call getTechFirstComment( String tdid ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getTechFirstComment(tdid);
    }

    //获取技术贴回复内容列表
    public Call getTechCommentAgain( String cid ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getTechCommentAgain(cid);
    }

    //首次评论技术贴
    public Call sendFirstComment(String tdid, String reviewer, String content ){
        detailService = retrofit.create(DetailService.class);
        return detailService.sendFirstComment(tdid ,reviewer , content );
    }

    //回复首次评论技术贴

    public Call sendCommentAgain(String cid , String healer , String content){
        detailService = retrofit.create(DetailService.class);
        return detailService.sendCommentAgain(cid , healer , content );
    }

    //加载提问帖主体数据
    public Call getAskData(String tdid){
        detailService = retrofit.create(DetailService.class);
        return detailService.getAskData(tdid);
    }

    //加载提问帖回答数据
    public Call getFirstAnswerData(String tdid ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getFirstAnswerData(tdid);
    }
}
