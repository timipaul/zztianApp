package com.timi.framedemo.bean;

import java.util.Date;


/**
 *  发帖表
 */
public class Topic{


    private int id;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 用户编号 */
    private String userId;
    /** 版块编号 */
    private String boardId;
    /** 发帖时间 */
    private Date publishtime;
    /** 修改时间 */
    private Date modifytime;
    /** 回复数量 */
    private int replycount;
    /** 点击数量 */
    private int clickcount;
    /** 点赞数量 */
    private int praisecount;
    /** 用户昵称 */
    private String nickName;
    /** 用户头像 */
    private String headimg;
    /**评论图片*/
    private String contentImg;

    public int getPraisecount() {
        return praisecount;
    }

    public void setPraisecount(int praisecount) {
        this.praisecount = praisecount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public int getReplycount() {
        return replycount;
    }

    public void setReplycount(int replycount) {
        this.replycount = replycount;
    }

    public int getClickcount() {
        return clickcount;
    }

    public void setClickcount(int clickcount) {
        this.clickcount = clickcount;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }
}
