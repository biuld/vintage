package com.github.biuld.model;

import javax.persistence.*;
import lombok.Data;

@Table(name = "post_tag_map")
@Data
public class PostTagMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "tag_id")
    private Integer tagId;
}