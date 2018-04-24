package cn.lrn517.techcomplatform.bean;

import java.util.List;

/**
 * Created by lirun on 2018/4/24.
 */

public class homeattentiondata {

    private List<UserBean> user;
    private List<DetailBean> detail;
    private List<TechpersonzoneBean> techpersonzone;

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public List<TechpersonzoneBean> getTechpersonzone() {
        return techpersonzone;
    }

    public void setTechpersonzone(List<TechpersonzoneBean> techpersonzone) {
        this.techpersonzone = techpersonzone;
    }

    public static class UserBean {
        /**
         * uid : 20180319124601
         * ualiase : kepler1
         * ulevel : 1
         * utype : 0
         * uphoto : default.jpg
         */

        private String uid;
        private String ualiase;
        private String ulevel;
        private String utype;
        private String uphoto;

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

        public String getUphoto() {
            return uphoto;
        }

        public void setUphoto(String uphoto) {
            this.uphoto = uphoto;
        }
    }

    public static class DetailBean {
        /**
         * tdid : td-10
         * tdtitle : 1兆等于多少人民币
         * attention : 11
         * answer : 11
         */

        private String tdid;
        private String tdtitle;
        private String attention;
        private String answer;

        public String getTdid() {
            return tdid;
        }

        public void setTdid(String tdid) {
            this.tdid = tdid;
        }

        public String getTdtitle() {
            return tdtitle;
        }

        public void setTdtitle(String tdtitle) {
            this.tdtitle = tdtitle;
        }

        public String getAttention() {
            return attention;
        }

        public void setAttention(String attention) {
            this.attention = attention;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    public static class TechpersonzoneBean {
        /**
         * tpzname : kepler
         * tpzid : tpz-1
         * ualiase : kepler1
         * uphoto : default.jpg
         */

        private String tpzname;
        private String tpzid;
        private String ualiase;
        private String uphoto;

        public String getTpzname() {
            return tpzname;
        }

        public void setTpzname(String tpzname) {
            this.tpzname = tpzname;
        }

        public String getTpzid() {
            return tpzid;
        }

        public void setTpzid(String tpzid) {
            this.tpzid = tpzid;
        }

        public String getUaliase() {
            return ualiase;
        }

        public void setUaliase(String ualiase) {
            this.ualiase = ualiase;
        }

        public String getUphoto() {
            return uphoto;
        }

        public void setUphoto(String uphoto) {
            this.uphoto = uphoto;
        }
    }
}
