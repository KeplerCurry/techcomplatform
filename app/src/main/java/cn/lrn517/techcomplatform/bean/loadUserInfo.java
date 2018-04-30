package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/4/9.
 */

public class loadUserInfo {

    /**
     * ualiase : tchCST4601
     * ulevel : 1
     * utype : 0
     * attention_user : 0
     * user_attention : 2
     */

    private String ualiase;
    private String ulevel;
    private String utype;
    private String attention_user;
    private String user_attention;
    private String uphoto;

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public String getUaliase() {
        return ualiase;
    }

    public void setUaliase(String ualiase) {
        this.ualiase = ualiase;
    }

    public String getUlevel() {
        return ulevel;
    }

    public void setUlevel(String ulevel) {
        this.ulevel = ulevel;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getAttention_user() {
        return attention_user;
    }

    public void setAttention_user(String attention_user) {
        this.attention_user = attention_user;
    }

    public String getUser_attention() {
        return user_attention;
    }

    public void setUser_attention(String user_attention) {
        this.user_attention = user_attention;
    }
}
