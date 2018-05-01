package cn.lrn517.techcomplatform.model;

import cn.lrn517.techcomplatform.service.DetailService;
import cn.lrn517.techcomplatform.service.serviceAddress;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

/**
 * Created by lirun on 2018/3/20.
 */

public class DetailModel {

    private Retrofit retrofit;
    private DetailService detailService;

    public DetailModel(){
        retrofit = new Retrofit.Builder()
                .baseUrl(serviceAddress.SERVICE_ADDRESS+"/Json/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //获取热门数据
    public Call getHotData( int page ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getHotData(page);
    }

    //技术贴/问题 数据获取
    public Call getTechOrQuestionData( String tid, int state , int page ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getTechOrQuestionData(tid, state, page);
    }

    //获取推荐页面数据
    public Call getCommentData( int page ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getCommentData(page);
    }

    //获取技术贴详情
    public Call getTechDetailData( String tdid ){
        detailService = retrofit.create(DetailService.class);
        return detailService.getTechDetailData(tdid);
    }

    //获取用户是否对技术贴进行关注用户、点赞、收藏操作
    public Call getUsera_l_c(String auid,String uid ,String tdid){
        detailService = retrofit.create(DetailService.class);
        return detailService.getUsera_l_c(auid, uid, tdid);
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

    //判断用户是否关注该问题
    public Call getUserAttentionQuestion(String uid ,String id){
        detailService = retrofit.create(DetailService.class);
        return detailService.getUserAttentionQuestion(uid, id);
    }

    //回答提问帖
    public Call sendAnswer(String tdid , String reviewer ,String content){
        detailService = retrofit.create(DetailService.class);
        return detailService.sendAnswerData(tdid,reviewer,content);
    }

    //评论回答内容
    public Call sendCommentAnswer(String cid,String healer,String content){
        detailService = retrofit.create(DetailService.class);
        return detailService.sendCommentAnswerData(cid,healer,content);
    }

    //查看详细回答
    public Call getCompleteAnswerData(String cid){
        detailService = retrofit.create(DetailService.class);
        return detailService.getCompleteAnswerData(cid);
    }

    //获取用户是否对回答进行关注用户、点赞操作
    public Call getUserForTheAnswer(String auid,String uid, String cid){
        detailService = retrofit.create(DetailService.class);
        return detailService.getUserForTheAnswer(auid, uid, cid);
    }

    //查看评论回答列表
    public Call getCommentAnswerData(String cid){
        detailService = retrofit.create(DetailService.class);
        return detailService.getCommentAnswerData(cid);
    }

    //获取技术分类列表
    public Call getTechClassifyData(){
        detailService = retrofit.create(DetailService.class);
        return detailService.getTechclassifyData();
    }

    //判断用户是否关注专栏
    public Call getUserAttentionTPZ(String uid, String id){
        detailService = retrofit.create(DetailService.class);
        return detailService.getUserAttentionTPZ(uid, id);
    }

}
