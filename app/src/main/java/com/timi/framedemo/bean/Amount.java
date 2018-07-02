package com.timi.framedemo.bean;

import java.util.Date;


/**
 * 用户金额表
 */
public class Amount {

    private int id ;
    private String userId;            //用户编号
    private int integralNum;          //积分数量
    private int month_num;            //月票
    private int rollNum;              //阅读卷数量
    private int zzbNum;               //自在币数量
    private Date createtime;          //创建时间
    private Date updatetime;          //修改时间




    private String phone;        //手机号码
    private String nickName;     //昵称
    private String password;     //密码
    private String headimg;      //头像
    private String QQ;
    private String weixin;
    private String userType;     //用户类型
    private String sex;          //性别
    private String intro;        //签名
    private Date birthday;       //生日
    private String cover;        //个人封面
    private int ifsign;       //今天是否签到
    private int signCount;    //连续签到数


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getRollNum() {
        return rollNum;
    }

    public void setRollNum(int rollNum) {
        this.rollNum = rollNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(int integralNum) {
        this.integralNum = integralNum;
    }



    public int getZzbNum() {
        return zzbNum;
    }

    public void setZzbNum(int zzbNum) {
        this.zzbNum = zzbNum;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getIfsign() {
        return ifsign;
    }

    public void setIfsign(int ifsign) {
        this.ifsign = ifsign;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getMonth_num() {
        return month_num;
    }

    public void setMonth_num(int month_num) {
        this.month_num = month_num;
    }
}
