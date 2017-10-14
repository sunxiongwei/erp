package com.sun.framework.toolbox;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable {
    /**
     * serialVersionUID TODO
     */
    private static final long serialVersionUID = -7498877508026528638L;

    /**
     * limit 最大记录数
     */
    private int limit = 25;

    /**
     * page 当前页面
     */
    private int page = 1;

    /**
     * total 总记录条数
     */
    private long total = 0;

    /**
     * 为前台更容易适应，添加此属性
     */
    private long totalPage = 0;

    /**
     * items 符合记录的分页数据
     */
    private List<T> items;

    /**
     * sort 排序字段，前台ExtJS设置
     */
    private String sort;

    /**
     * dir 排序方向
     */
    private String dir;

    /**
     * 获取属性{@link #limit limit}的值
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 给属性{@link #limit limit}设置新值
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 获取属性{@link #page page}的值
     */
    public int getPage() {
        return page;
    }

    /**
     * 给属性{@link #page page}设置新值
     */
    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalPage() {
        long remainder = this.total % this.limit;
        if (remainder == 0) {
            return this.total / this.limit;
        }
        return this.total / this.limit + 1;
    }

    /**
     * 获取属性{@link #total total}的值
     */
    public long getTotal() {
        return total;
    }

    /**
     * 给属性{@link #total total}设置新值
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取属性{@link #items items}的值
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * 给属性{@link #items items}设置新值
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * 获取起始位置
     * 
     * @return 起始位置
     */
    public Integer getStart() {
        return (page - 1) * limit;
    }

    /**
     * 获取属性{@link #sort sort}的值
     */
    public String getSort() {
        return sort;
    }

    /**
     * 给属性{@link #sort sort}设置新值
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 获取属性{@link #dir dir}的值
     */
    public String getDir() {
        return dir;
    }

    /**
     * 给属性{@link #dir dir}设置新值
     */
    public void setDir(String dir) {
        this.dir = dir;
    }
}
