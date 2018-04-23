package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/3/25.
 */

public class commentAnswerData {

    /**
     * cid : c-1
     * healer : 20180319124601
     * content : 评论的这哪
     * catime : 2018-03-22 18:30:19
     */

    private String cid;
    private String uphoto;
    private String ualiase;
    private String content;
    private String catime;

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUaliase() {
        return ualiase;
    }

    public void setUaliase(String ualiase) {
        this.ualiase = ualiase;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCatime() {
        return catime;
    }

    public void setCatime(String catime) {
        this.catime = catime;
    }
}
