package com.github.biuld.mapper.sql;

import com.github.biuld.dto.PostParams;
import com.github.biuld.util.Time;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class PostSql {

    public String getPostListSql(PostParams postParams) {

        Time time=new Time();

        return new SQL() {
            {
                SELECT("*");
                FROM("post");

                if (postParams.getUserId()!=null)
                    WHERE("user_id="+postParams.getUserId());

                if (!StringUtils.isEmpty(postParams.getTitle()))
                    WHERE("title like \"%"+postParams.getTitle()+"%\"");

                if (!StringUtils.isEmpty(postParams.getKeyword())) {
                    for (String key:postParams.getKeyword().split(",")) {
                        WHERE("LOCATE(\""+key+"\",content)>0");
                    }
                }

                if (postParams.getSortByTime()!=null) {

                    String duration="\""+time.getTodayBegin()+"\" and \""+time.getTodayEnd()+"\"";

                    switch (postParams.getSortByTime()) {
                        case 1:
                            break;
                        case 2:
                            duration="\""+time.getWeekBegin()+"\" and \""+time.getWeekEnd()+"\"";
                            break;
                        case 3:
                            duration="\""+time.getMonthBegin()+"\" and \""+time.getMonthEnd()+"\"";
                            break;
                        case 4:
                            duration="\""+time.getYearBegin()+"\" and \""+time.getYearEnd()+"\"";
                            break;
                    }

                    WHERE("gmt_create between "+duration);
                }

                ORDER_BY("id DESC");

            }
        }.toString();
    }
}
