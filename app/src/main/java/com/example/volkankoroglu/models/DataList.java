package com.example.volkankoroglu.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataList {

    private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    private List<User> users = null;
    private Ad ad;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public DataList() {
    }

    /**
     *
     * @param total
     * @param perPage
     * @param ad
     * @param users
     * @param totalPages
     * @param page
     */
    public DataList(Integer page, Integer perPage, Integer total, Integer totalPages, List<User> users, Ad ad) {
        super();
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.users = users;
        this.ad = ad;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setData(List<User> users) {
        this.users = users;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}