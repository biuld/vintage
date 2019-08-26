package com.github.biuld.mapper;

import com.github.biuld.dto.PostParams;
import com.github.biuld.dto.PostTitleIdPair;
import com.github.biuld.mapper.sql.PostSql;
import com.github.biuld.model.Post;
import com.github.biuld.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @SelectProvider(type = PostSql.class, method = "getPostListSql")
    List<Post> getPostList(PostParams postParams);

    //for loading user's profile
    @Select("select id, title from post where user_id=#{userId}")
    List<PostTitleIdPair> getPairList(@Param("userId") Integer userId);
}