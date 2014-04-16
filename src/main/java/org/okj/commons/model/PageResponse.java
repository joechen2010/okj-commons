package org.okj.commons.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PageResponse<E> {
    
    private int pageSize = 10;
    private int pageNumber = 1;
    private long totalCount;
    private long start;
    private long end;
    private List<E> list;

    public PageResponse(long totalCount, List<E> list) {
        super();
        this.totalCount = totalCount;
        this.list = list;
    }

    public PageResponse() {
        super();
        this.totalCount = 0;
        this.list = new ArrayList<E>();
    }

    public PageResponse(PageRequest pageRequest) {
        super();
        this.totalCount = 0;
        this.list = new ArrayList<E>();
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotalPage() {
        if (totalCount % pageSize == 0)
            return totalCount / pageSize;
        else
            return totalCount / pageSize + 1;
    }

   
}
