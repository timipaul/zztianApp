package com.timi.framedemo.bean;

import java.util.Date;

/**
 * 收藏
 */
public class Collect {

    private int id;
    private int userId;      //用户ID
    private int cartoonId;       //收藏小说、漫画、社区ID
    private String chapterSection;      //漫画、小说章节
    private String chapterName;         //章节名称
    private String status;   //取消、存在
    private Date voteTime;   //收藏时间

    private String bookName; //书名
    private String chapterCover;  //封面

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getChapterCover() {
        return chapterCover;
    }

    public void setChapterCover(String chapterCover) {
        this.chapterCover = chapterCover;
    }

    public int getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(int cartoonId) {
        this.cartoonId = cartoonId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }


    public String getChapterSection() {
        return chapterSection;
    }

    public void setChapterSection(String chapterSection) {
        this.chapterSection = chapterSection;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }


    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", userId=" + userId +
                ", cartoonId=" + cartoonId +
                ", chapterSection='" + chapterSection + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", status='" + status + '\'' +
                ", voteTime=" + voteTime +
                ", bookName='" + bookName + '\'' +
                ", chapterCover='" + chapterCover + '\'' +
                '}';
    }
}
