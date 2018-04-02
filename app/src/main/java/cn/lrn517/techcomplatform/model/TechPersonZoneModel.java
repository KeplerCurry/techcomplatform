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

    //发表个人专栏文章
    public Call sendTechPersonZoneDetail(String tpzdid, String tpzdtitle, String tpzdcontent, int isfree, double price){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.sendtechpersonzonedetail(tpzdid, tpzdtitle, tpzdcontent, isfree, price);
    }

    //获取专栏列表
    public Call getTechPersonZoneList(){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.gettechpersonzonelist();
    }

    //获取查看个人专栏用户信息
    public Call getTechPersonZoneUserinfo(String tpzid){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.gettechpersonzoneuserinfo(tpzid);
    }

    //获取专栏文章列表
    public Call getTechPersonZoneDetailList(String tpzid){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.gettechpersonzonedetaillist(tpzid);
    }

    //查看专栏文章
    public Call getTechPersonZoneDetailData(String tpzdid){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.gettechpersonzonedetaildata(tpzdid);
    }

    //加载专栏文章首次回复内容
    public Call getTechPersonZoneFirstCommentData(String tpzdid){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.gettpzfirstcommentdata(tpzdid);
    }

    //加载对首次回复的回复内容
    public Call getTechPersonZoneCommentAgainData(String tpzcid){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.gettpzcommentagain(tpzcid);
    }

    //专栏文章首次回复
    public Call sendTechPersonZoneFirstComment(String tpzdid, String reviewer, String content){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.sendtpzfirstcomment(tpzdid, reviewer, content);
    }

    //对首次评论的回复
    public Call sendTechPersonZoneCommentAgain(String tpzcid, String healer, String content){
        techPersonZoneService = retrofit.create(TechPersonZoneService.class);
        return techPersonZoneService.sendtpzcommentagain(tpzcid, healer, content);
    }
}
