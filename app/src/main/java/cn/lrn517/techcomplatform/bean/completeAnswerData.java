package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/3/25.
 */

public class completeAnswerData {


    /**
     * ualiase : tchCST4601
     * cid : c-1
     * content : 第一次评论真好
     * chit : 1
     * ctime : 2018-03-23 20:42:35
     * commentcount : 3
     * success : 1
     */

    private String ualiase;
    private String cid;
    private String content;
    private String chit;
    private String ctime;
    private String commentcount;
    private int success;

    public String getUaliase() {
        return ualiase;
    }

    public void setUaliase(String ualiase) {
        this.ualiase = ualiase;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChit() {
        return chit;
    }

    public void setChit(String chit) {
        this.chit = chit;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}

