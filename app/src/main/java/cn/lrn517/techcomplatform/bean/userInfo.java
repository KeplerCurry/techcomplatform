package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/3/19.
 */

public class userInfo {
    /**
     * uid : 20180319163431
     * ualiase : tchCST3431
     * ispassed : 0
     * ulevel : 1
     * uexp : 0
     * ulogintime : 2018-03-19 18:03:34
     * uloginip : 123.92.219.143
     */

    private String uid;
    private String ualiase;
    private String ispassed;
    private String ulevel;
    private String uexp;
    private String ulogintime;
    private String uloginip;
    private String uphoto;
    private String tpzid;
    private int applyTPZState;

    public String getTpzid() {
        return tpzid;
    }

    public void setTpzid(String tpzid) {
        this.tpzid = tpzid;
    }

    public int getApplyTPZState() {
        return applyTPZState;
    }

    public void setApplyTPZState(int applyTPZState) {
        this.applyTPZState = applyTPZState;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    private int success;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUaliase() {
        return ualiase;
    }

    public void setUaliase(String ualiase) {
        this.ualiase = ualiase;
    }

    public String getIspassed() {
        return ispassed;
    }

    public void setIspassed(String ispassed) {
        this.ispassed = ispassed;
    }

    public String getUlevel() {
        return ulevel;
    }

    public void setUlevel(String ulevel) {
        this.ulevel = ulevel;
    }

    public String getUexp() {
        return uexp;
    }

    public void setUexp(String uexp) {
        this.uexp = uexp;
    }

    public String getUlogintime() {
        return ulogintime;
    }

    public void setUlogintime(String ulogintime) {
        this.ulogintime = ulogintime;
    }

    public String getUloginip() {
        return uloginip;
    }

    public void setUloginip(String uloginip) {
        this.uloginip = uloginip;
    }
}
