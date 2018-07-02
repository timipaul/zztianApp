package com.timi.framedemo.bean;

import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 */
public class Comment {
    String name; //评论者

    public Comment(){}

    private String id;// id
    private String customerId;// 用户id
    private String parentCommentId;// 父评论id
    private String contentId;// 内容id
    private String content;// 评论内容
    private Date commentDate;// 评论日期
    private Date commentTime;// 评论时间
    private Integer state;// 评论的状态
    private Integer type;// 评论类型


    // =================================
    private List<Comment> replyComment; // 评论回复信息
    private Customer customer;// 评论者信息
    private Customer replyCustomer; // 回复评论的人


    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", parentCommentId='" + parentCommentId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", content='" + content + '\'' +
                ", commentDate=" + commentDate +
                ", commentTime=" + commentTime +
                ", state=" + state +
                ", type=" + type +
                ", replyComment=" + replyComment +
                ", customer=" + customer +
                ", replyCustomer=" + replyCustomer +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customer_id) {
        this.customerId = customer_id;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Comment> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<Comment> replyComment) {
        this.replyComment = replyComment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getReplyCustomer() {
        return replyCustomer;
    }

    public void setReplyCustomer(Customer replyCustomer) {
        this.replyCustomer = replyCustomer;
    }

    public Comment(String name, String content){
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
