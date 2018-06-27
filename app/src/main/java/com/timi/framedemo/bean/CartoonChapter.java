package com.timi.framedemo.bean;

import java.util.Date;

/**
 * 漫画章节列表
 */
public class CartoonChapter {

    private int id;             //章节编号
    private String chapterId;   //章节id
    private String cartoonId;   //漫画编号
    private String chapterCover;//章节封面
    private String chapterName; //章节名称
    private int praiseNum;      //章节点赞数量
    private Date createdate;    //创建时间

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(String cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getChapterCover() {
        return chapterCover;
    }

    public void setChapterCover(String chapterCover) {
        this.chapterCover = chapterCover;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }
}

