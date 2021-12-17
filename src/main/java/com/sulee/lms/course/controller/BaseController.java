package com.sulee.lms.course.controller;

import com.sulee.lms.util.PageUtil;

public class BaseController {

    public String getPagerHtml(Long totalCount, long pageSize, long pageIndex, String queryString){
        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);

        return pageUtil.pager();
    }

}
