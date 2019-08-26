package com.github.biuld.mapper;

import com.github.biuld.mapper.sql.TagSql;
import com.github.biuld.model.Tag;
import com.github.biuld.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    @SelectProvider(type = TagSql.class, method = "getTagListSql")
    List<Tag> getTagList(String keyword);
}