package com.github.biuld.mapper.sql;

import com.github.biuld.dto.params.UserParams;
import org.apache.ibatis.jdbc.SQL;

public class UserSql {

    public String getUserListSql(UserParams params) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");

                if (params.getEmail() != null)
                    WHERE("email like \"%" + params.getEmail() + "%\"");

                if (params.getUsername() != null)
                    WHERE("username like \"%" + params.getUsername() + "%\"");

            }
        }.toString();
    }
}
