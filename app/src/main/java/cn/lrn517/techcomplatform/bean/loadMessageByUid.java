package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/4/10.
 */

public class loadMessageByUid {

    /**
     * mid : m-2
     * receiveid : 20180319155823
     * sendid : 20180319124601
     * text : 2
     * createtime : 2018-04-10 15:00:12
     * isread : 0
     * ualiase : tchCST4601
     */

    private String mid;
    private String receiveid;
    private String sendid;
    private String text;
    private String createtime;
    private int isread;
    private String ualiase;
    private String uphoto;

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(String receiveid) {
        this.receiveid = receiveid;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public String getUaliase() {
        return ualiase;
    }

    public void setUaliase(String ualiase) {
        this.ualiase = ualiase;
    }
}
