package com.github.biuld.dto.view;

import com.github.biuld.model.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentView extends Comment {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "回复的用户名")
    private String replyUserName;

    @ApiModelProperty(value = "第一条子评论")
    private Comment firstSubComment;

    @ApiModelProperty(value = "子评论数")
    private Integer subCommentNum;
}
