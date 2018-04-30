package cn.lrn517.techcomplatform.bean;

import java.util.List;

/**
 * Created by lirun on 2018/4/10.
 */

public class loadMessageData {

    /**
     * count : 1
     * list : [{"sendid":"20180319155823","ualiase":"tchCST582你好好3"},{"sendid":"4465464","ualiase":"www"}]
     */

    private String count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * sendid : 20180319155823
         * ualiase : tchCST582你好好3
         */

        private String sendid;
        private String ualiase;
        private String uphoto;

        public String getUphoto() {
            return uphoto;
        }

        public void setUphoto(String uphoto) {
            this.uphoto = uphoto;
        }

        public String getSendid() {
            return sendid;
        }

        public void setSendid(String sendid) {
            this.sendid = sendid;
        }

        public String getUaliase() {
            return ualiase;
        }

        public void setUaliase(String ualiase) {
            this.ualiase = ualiase;
        }
    }
}
