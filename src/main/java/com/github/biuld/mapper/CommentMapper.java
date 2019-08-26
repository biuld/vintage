package com.github.biuld.mapper;

import com.github.biuld.dto.CommentContentIdPair;
import com.github.biuld.model.Comment;
import com.github.biuld.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    //获取某条评论的第一条子评论
    @Select("select * from `comment` inner join (select min(id) as target from comment where parent_id=#{parentId}) T on id=T.target")
    Comment selectFirstComment(@Param("parentId") Integer parentId);

    //for loading user's profile
    @Select("select id, content from comment where id=#{userId}")
    List<CommentContentIdPair> getPairList(@Param("userId") Integer userId);
}