package cn.lrn517.techcomplatform.bean;

/**
 * Created by lirun on 2018/4/2.
 */

public class techpersonzonedetaildata {
    /**
     * tpzdtitle : 呵呵
     * tpzdcontent : <?php
     namespace Json\Controller;
     use Think\Controller;
     class JsonController extends Controller {

     public function test(){
     $user = M("user");
     $c = $user ->select();
     dump($c);
     }

     //手机号注册
     public function doRegForTelephone(){
     $user  = M("user");
     $user_data['utelephone'] = I('request.telephone');
     if( !$user->where($user_data)->find() )
     {
     $user_data['upassword'] = md5( I('request.password'));
     $user_data['uid'] = date("YmdHis",time());
     $user_data['ualiase'] = "tch_".date("YmdHis" , time());
     $user_data['ispassed'] = 0;
     $user_data['ulevel'] = 1;
     $user_data['uexp'] = 0;
     $user_data['utype'] = 0;
     if( $user->add($user_data) )
     {
     $data['success'] = 1;
     $this->ajaxReturn($data);
     }
     else
     {
     $data['success'] = 0;
     $this->ajaxReturn($data);
     }
     }
     else
     {
     $data['success'] = 2;
     $this->ajaxReturn($data);
     }
     }
     //测试获取IP
     public function getIPTest(){
     $ip = get_client_ip();
     dump($ip);
     }

     //用户登录
     public function doLogin(){
     $user = M('user');
     $login_data['utelephone'] = I('request.telephone');
     $login_data['upassword'] = md5( I('request.password'));
     if( $data = $user->where($login_data)->find() )
     {
     $id['uid'] = $data['uid'];
     $data_return['uid'] = $data['uid'];
     $data_return['ualiase'] = $data['ualiase'];
     $data_return['ispassed'] = $data['ispassed'];
     $data_return['ulevel'] = $data['ulevel'];
     $data_return['uexp'] = $data['uexp'];
     if( NULL != $data['ulogintime'])
     {
     $data_return['ulogintime'] = $data['ulogintime'];
     }
     else
     {
     $data_return['ulogintime'] = "0";
     }
     if( NULL != $data['uloginip'])
     {
     $data_return['uloginip'] = $data['uloginip'];
     }
     else
     {
     $data_return['uloginip'] = "0";
     }
     $data_save['ulogintime'] = date("Y:m:d H:m:s" , time());
     $data_save['uloginip'] = get_client_ip();
     if( $user -> where($id) ->save($data_save) )
     {
     $loginannal = M('loginannal');
     $data_save['uid'] = $id['uid'];
     if( $loginannal ->add($data_save) )
     {
     $data_return['success'] = 1;
     //echo json_encode($data_return,JSON_UNESCAPED_UNICODE);
     $this->ajaxReturn($data_return);
     }
     }

     }
     else
     {
     $data['success'] = 0;
     $this->ajaxReturn($data);
     }
     }
     //app热门内容
     public function hotData(){
     $techdetail = M('techdetail');
     $data = $techdetail -> find();
     $this->ajaxReturn($data);
     }

     public function addTestData(){
     $i = 2;
     while( $i < 32 )
     {
     $data['tdid'] = "td-".$i;
     $data['tid'] = "t-124";
     $data['tuid'] = "20180319124601";
     $data['tdtitle'] = "code".$i;
     $data['']
     }

     }
     }
     * tpzdfirsttime : 2018-04-02 19:03:28
     * tpzname : kepler
     * ualiase : tchCST582你好好3
     */

    private String tpzdtitle;
    private String tpzdcontent;
    private String tpzdfirsttime;
    private String tpzname;
    private String ualiase;
    private String like;
    private String uphoto;
    private String uid;
    private String uspecialline;

    public String getUspecialline() {
        return uspecialline;
    }

    public void setUspecialline(String uspecialline) {
        this.uspecialline = uspecialline;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public String getTpzdtitle() {
        return tpzdtitle;
    }

    public void setTpzdtitle(String tpzdtitle) {
        this.tpzdtitle = tpzdtitle;
    }

    public String getTpzdcontent() {
        return tpzdcontent;
    }

    public void setTpzdcontent(String tpzdcontent) {
        this.tpzdcontent = tpzdcontent;
    }

    public String getTpzdfirsttime() {
        return tpzdfirsttime;
    }

    public void setTpzdfirsttime(String tpzdfirsttime) {
        this.tpzdfirsttime = tpzdfirsttime;
    }

    public String getTpzname() {
        return tpzname;
    }

    public void setTpzname(String tpzname) {
        this.tpzname = tpzname;
    }

    public String getUaliase() {
        return ualiase;
    }

    public void setUaliase(String ualiase) {
        this.ualiase = ualiase;
    }
}
