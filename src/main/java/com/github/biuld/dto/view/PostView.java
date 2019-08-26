package com.github.biuld.dto.view;

import com.github.biuld.model.Post;
import com.github.biuld.model.Tag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostView extends Post {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("标签")
    private List<Tag> tagList;

    @Column(name = "comment_count")
    @ApiModelProperty("评论计数")
    private Integer commentCount;
}
