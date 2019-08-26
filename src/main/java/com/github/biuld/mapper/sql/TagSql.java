package com.github.biuld.mapper.sql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class TagSql {

    public String getTagListSql(String keyword) {
        return new SQL() {
            {
                SELECT("*");
                FROM("tag");

                if (!StringUtils.isEmpty(keyword))
                    WHERE("tag_desc like \"%"+keyword+"%\"");

                ORDER_BY("id DESC");
            }
        }.toString();
    }
}
