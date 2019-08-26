package com.github.biuld.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "post_id")
    @ApiModelProperty("文章id")
    private Integer postId;

    @Column(name = "parent_id")
    @ApiModelProperty("父评论id，0-没有父评论")
    private Integer parentId=0;

    @Column(name = "reply_user_id")
    @ApiModelProperty("回复的用户id")
    private Integer replyUserId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", hidden = true)
    private Integer userId;

    @ApiModelProperty(hidden = true)
    @Column(name = "gmt_create")
    private Date gmtCreate;

    @ApiModelProperty(value = "评论内容", required = true)
    @NotEmpty(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(hidden = true)
    private String request;
}