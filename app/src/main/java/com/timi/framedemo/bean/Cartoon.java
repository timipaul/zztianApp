package com.timi.framedemo.bean;

import java.util.Date;


/**
 * 漫画类
 */
public class Cartoon {


    private int id;
    private String userId;          //用户id
    private String author;          //作者
    private String bookName;        //漫画书名
    private String intro;           //作品介绍
    private String cover;           //漫画封面
    private String bookType;        //漫画类型
    private String type;            //类型， 1：漫画； 2：小说； 3：图文

    private int clickNum;           //点击量
    private int collectNum;         //收藏量

    private int praiseNum;          //点赞量
    private Date createTime;       //创建时间

    private String recordNum;     //记录条数

    private String ifConcern;     //是否关注


    @Override
    public String toString() {
        return "Cartoon{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", intro='" + intro + '\'' +
                ", cover='" + cover + '\'' +
                ", bookType='" + bookType + '\'' +
                ", type='" + type + '\'' +
                ", clickNum=" + clickNum +
                ", collectNum=" + collectNum +
                ", praiseNum=" + praiseNum +
                ", createTime=" + createTime +
                ", recordNum='" + recordNum + '\'' +
                ", ifConcern='" + ifConcern + '\'' +
                '}';
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getIfConcern() {
        return ifConcern;
    }

    public void setIfConcern(String ifConcern) {
        this.ifConcern = ifConcern;
    }



    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }



    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }





    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
