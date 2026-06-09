package com.travel.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public class PageResult<T> {

    private Long total;
    private Integer page;
    private Integer size;
    private List<T> list;

    public PageResult() {}

    public static <T> PageResult<T> from(IPage<T> page) {
        PageResult<T> r = new PageResult<>();
        r.total = page.getTotal();
        r.page = (int) page.getCurrent();
        r.size = (int) page.getSize();
        r.list = page.getRecords();
        return r;
    }

    public Long getTotal() { return total; }
    public void setTotal(Long total) { this.total = total; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }
}
