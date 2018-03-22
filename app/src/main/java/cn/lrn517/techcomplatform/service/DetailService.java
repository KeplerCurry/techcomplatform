package cn.lrn517.techcomplatform.service;

import java.util.List;

import cn.lrn517.techcomplatform.bean.commonForTech;
import cn.lrn517.techcomplatform.bean.homeData;
import cn.lrn517.techcomplatform.bean.techCommentAgain;
import cn.lrn517.techcomplatform.bean.techDetailData;
import cn.lrn517.techcomplatform.bean.techFirstComment;
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

    //获取技术贴数据
    @POST("load_detail_state_0")
    Call<techDetailData> getTechDetailData(
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
}