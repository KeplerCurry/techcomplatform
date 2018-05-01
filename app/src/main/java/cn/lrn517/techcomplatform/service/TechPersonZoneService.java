package cn.lrn517.techcomplatform.service;

import java.util.List;

import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.bean.commonForSendTPZ;
import cn.lrn517.techcomplatform.bean.commonForTPZ;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaildata;
import cn.lrn517.techcomplatform.bean.techpersonzonedetaillist;
import cn.lrn517.techcomplatform.bean.techpersonzonelist;
import cn.lrn517.techcomplatform.bean.techpersonzoneuserinfo;
import cn.lrn517.techcomplatform.bean.tpzCommentAgain;
import cn.lrn517.techcomplatform.bean.tpzFirstComment;
import cn.lrn517.techcomplatform.bean.userForTechDetailState;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lirun on 2018/4/1.
 */

public interface TechPersonZoneService {
    //申请个人专栏
    @POST("apply_for_tech_person_zone")
    Call<common> applyfortechpersonzone(
            @Query("uid") String uid,
            @Query("tpzname") String tpzname,
            @Query("tid") String tid
    );

    //发表个人专栏文章
    @POST("send_tech_person_zone_detail")
    Call<commonForSendTPZ> sendtechpersonzonedetail(
            @Query("tpzid") String tpzid,
            @Query("tpzdtitle") String tpzdtitle,
            @Query("tpzdcontent") String tpzdcontent,
            @Query("isfree") int isfree,
            @Query("price") double price
    );
    //获取专栏列表
    @POST("load_tech_person_zone_list")
    Call<List<techpersonzonelist>> gettechpersonzonelist();

    //获取查看专栏详情用户信息
    @POST("load_tech_person_zone_userinfo")
    Call<techpersonzoneuserinfo> gettechpersonzoneuserinfo(
            @Query("tpzid") String tpzid
    );

    //获取专栏文章数据
    @POST("load_tech_person_zone_detail_list_by_tpzid")
    Call<List<techpersonzonedetaillist>> gettechpersonzonedetaillist(
            @Query("tpzid") String tpzid
    );

    //查看专栏文章
    @POST("load_tech_person_zone_detail_data")
    Call<techpersonzonedetaildata> gettechpersonzonedetaildata(
            @Query("tpzdid") String tpzdid
    );

    //加载专栏文章首次评论内容
    @POST("load_tech_person_zone_detail_firstcommentdata")
    Call<List<tpzFirstComment>> gettpzfirstcommentdata(
            @Query("tpzdid") String tpzdid
    );

    //加载对专栏首次评论的回复
    @POST("load_tech_person_zone_detail_commentagaindata")
    Call<List<tpzCommentAgain>> gettpzcommentagain(
            @Query("tpzcid") String tpzcid
    );

    //对专栏文章首次回复
    @POST("send_tech_person_zone_detail_firstcomment")
    Call<commonForTPZ> sendtpzfirstcomment(
            @Query("tpzdid") String tpzdid,
            @Query("reviewer") String reviewer,
            @Query("content") String content
    );

    //对首次回复进行回复
    @POST("send_tech_person_zone_detail_commentagain")
    Call<commonForTPZ> sendtpzcommentagain(
            @Query("tpzcid") String tpzcid,
            @Query("healer") String healer,
            @Query("content") String content
    );

    //获取用户是否对专栏贴进行关注用户、点赞、收藏操作
    @POST("getUserA_L_C_TPZ")
    Call<userForTechDetailState> getUsera_l_c_TPZ(
            @Query("auid") String auid,
            @Query("uid") String uid,
            @Query("tpzdid") String tpzdid
    );
}
