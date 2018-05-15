package cn.lrn517.techcomplatform.service;

import java.util.List;
import java.util.Map;

import cn.lrn517.techcomplatform.bean.askData;
import cn.lrn517.techcomplatform.bean.commentAnswerData;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonForCommentAnswer;
import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.completeAnswerData;
import cn.lrn517.techcomplatform.bean.firstAnswerData;
import cn.lrn517.techcomplatform.bean.homeData;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techDetailData;
import cn.lrn517.techcomplatform.bean.techFirstComment;
import cn.lrn517.techcomplatform.bean.techclassifydata;
import cn.lrn517.techcomplatform.bean.userForTechDetailState;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    //技术贴/问题 数据获取
    @POST("techDetailOrQuestonByTid")
    Call<List<homeData>> getTechOrQuestionData(
            @Query("tid") String tid,
            @Query("state") int state,
            @Query("page") int page
    );

    //获取推荐页面数据
    @POST("commentData")
    Call<List<homeData>> getCommentData(
            @Query("page") int page
    );

    //获取技术贴数据
    @POST("load_detail_state_0")
    Call<techDetailData> getTechDetailData(
            @Query("tdid") String tdid
    );

    //获取用户是否对技术贴进行关注用户、点赞、收藏操作
    @POST("getUserA_L_C")
    Call<userForTechDetailState> getUsera_l_c(
            @Query("auid") String auid,
            @Query("uid") String uid,
            @Query("tdid") String tdid
    );

    //获取技术贴首次评论
    @POST("load_detail_state_0_firstcommentdata")
    Call<List<techFirstComment>> getTechFirstComment(
            @Query("tdid") String tdid
    );

    //获取技术贴回复内容
    @POST("load_detail_state_0_commentagaindata")
    Call<List<techCommentAgain>> getTechCommentAgain(
            @Query("cid") String cid
    );

    //首次评论技术贴
    @POST("send_detail_state_0_firstcomment")
    Call<commonForTech> sendFirstComment(
            @Query("tdid") String tdid,
            @Query("reviewer") String reviewer,
            @Query("content") String content
    );

    //回复首次评论技术贴
    @POST("send_detail_state_0_commentagain")
    Call<commonForTech> sendCommentAgain(
            @Query("cid") String cid,
            @Query("healer") String healer,
            @Query("content") String content
    );

    //加载提问帖主体数据
    @POST("load_detail_state_1")
    Call<askData> getAskData(
            @Query("tdid") String tdid
    );

    //加载提问帖回答数据
    @POST("load_detail_state_1_firstAnswerdata")
    Call<List<firstAnswerData>> getFirstAnswerData(
            @Query("tdid") String tdid
    );

    //判断用户是否关注该问题
    @POST("getUserAttentionQuestion")
    Call<common> getUserAttentionQuestion(
            @Query("uid") String uid,
            @Query("id") String id
    );

    //回答提问帖
    @Multipart
    @POST("send_detail_state_1_firstAnswer")
    Call<common> sendAnswerData(
            @PartMap Map<String, RequestBody> params
    );

    //评论回答内容
    @POST("send_detail_state_1_commentAnswer")
    Call<commonForCommentAnswer> sendCommentAnswerData(
            @Query("cid") String cid,
            @Query("healer") String healer,
            @Query("content") String content
    );

    //查看详细回答
    @POST("load_detail_state_1_answerData")
    Call<completeAnswerData> getCompleteAnswerData(
            @Query("cid") String cid
    );

    //获取用户是否对回答进行关注用户、点赞操作
    @POST("getUserForTheAnswer")
    Call<userForTechDetailState> getUserForTheAnswer(
            @Query("auid") String auid,
            @Query("uid") String uid,
            @Query("cid") String cid
    );

    //查看评论回答列表
    @POST("load_detail_state_1_commentAnswer")
    Call<List<commentAnswerData>> getCommentAnswerData(
            @Query("cid") String cid
    );

    //获取技术分类列表
    @POST("get_techclassify_data")
    Call<List<techclassifydata>> getTechclassifyData();

    //判断用户是否关注专栏
    @POST("getUserAttentionTPZ")
    Call<common> getUserAttentionTPZ(
            @Query("uid") String uid,
            @Query("id") String id
    );

}
