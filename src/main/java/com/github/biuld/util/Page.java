package com.github.biuld.util;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by biuld on 2019/8/20.
 */
@Data
@Slf4j
public class Page<T> {

    private List<T> dataList;

    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private Boolean hasNext = false;

    public static <T> Page<T> of(PageInfo pageInfo, List<T> dataList) {
        Page<T> page = new Page<>();

        page.pageNum = pageInfo.getPageNum();
        page.pageSize = pageInfo.getPageSize();
        page.total = pageInfo.getTotal();
        page.dataList = dataList;
        page.hasNext = pageInfo.isHasNextPage();

        return page;
    }

    public static <T> Page<T> ofRaw(List<T> dataList, int pageNum, int pageSize) {
        Page<T> page = new Page<>();

        page.pageNum = pageNum;
        page.pageSize = pageSize;
        page.total = (long) dataList.size();
        page.dataList = spilt(dataList, pageNum, pageSize);
        page.hasNext = (pageNum * pageSize) > page.total;

        return page;
    }

    private static <T> List<T> spilt(List<T> dataList, int pageNum, int pageSize) {

        int total = dataList.size();
        int pageAmount = total / pageSize;

        //分页
        try {
            dataList = dataList.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } catch (IndexOutOfBoundsException e) {
            //当page大于最大页数时,自动取最后一页，当数据量不够时，不做分页
            if (pageNum > pageAmount)
                dataList = dataList.subList(pageAmount * pageSize, dataList.size());
            log.info("start:" + (pageNum - 1) * pageSize + " " +
                    "end:" + pageNum * pageSize + " " +
                    "total:" + total);
        }

        return dataList;
    }
}

