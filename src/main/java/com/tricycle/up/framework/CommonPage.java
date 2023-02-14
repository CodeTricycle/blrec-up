package com.tricycle.up.framework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonPage implements Serializable {
    private Integer currentPage=1;
    private Integer pageSize=10;
    private Integer totalCount=0;
    private Integer totalPage=0;
    private Object data = new ArrayList();

    public CommonPage() {

    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CommonPage(long totalCount, int currentPage, int pageSize, List list) {
        super();
        this.totalCount = Math.toIntExact(totalCount);
        //计算总页数
        int pageCount = (int) (totalCount / pageSize);
        if (totalCount % pageSize > 0) {
            pageCount++;
        }
        this.totalPage = pageCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;

        this.data = list;
    }
}
