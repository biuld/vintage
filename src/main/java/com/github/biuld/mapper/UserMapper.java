package com.github.biuld.mapper;

import com.github.biuld.dto.params.UserParams;
import com.github.biuld.mapper.sql.UserSql;
import com.github.biuld.model.User;
import com.github.biuld.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @SelectProvider(type = UserSql.class, method = "getUserListSql")
    List<User> getUserList(UserParams params);
}