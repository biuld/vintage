package com.github.biuld.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(hidden = true)
    @Column(name = "gmt_create")
    private Date gmtCreate;

    @ApiModelProperty(hidden = true)
    @Column(name = "gmt_modify")
    private Date gmtModify;

    @ApiModelProperty(hidden = true)
    @Column(name = "last_modified_by")
    public Integer lastModifiedBy;

    @Column(name = "user_id")
    @ApiModelProperty(hidden = true)
    private Integer userId;

    @Column(name = "view_count")
    @ApiModelProperty(hidden = true)
    private Integer viewCount;

    @ApiModelProperty("标题")
    @NotEmpty(message = "title can't be empty")
    private String title;

    @ApiModelProperty("摘要")
    @NotEmpty(message = "abstract can't be empty")
    private String abs;

    @ApiModelProperty("文章内容")
    @NotEmpty(message = "content can't be empty")
    private String content;

    @Transient
    @ApiModelProperty("标签列表")
    private List<String> tagDescList;
}