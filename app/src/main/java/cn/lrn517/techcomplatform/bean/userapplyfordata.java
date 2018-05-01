package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/4/8.
 */

public class userapplyfordata {

    private String uafid;
    private String uid;
    private String tpzname;
    private String tid;
    private String createtime;
    private int state;
    private String pic;
    private int flag;
    private String tpzid;

    public String getTpzid() {
        return tpzid;
    }

    public void setTpzid(String tpzid) {
        this.tpzid = tpzid;
    }

    public String getUafid() {
        return uafid;
    }

    public void setUafid(String uafid) {
        this.uafid = uafid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTpzname() {
        return tpzname;
    }

    public void setTpzname(String tpzname) {
        this.tpzname = tpzname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
