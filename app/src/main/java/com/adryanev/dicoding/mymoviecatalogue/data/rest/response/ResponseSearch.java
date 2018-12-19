package com.adryanev.dicoding.mymoviecatalogue.data.rest.response;

import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSearch {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search> getResults() {
        return results;
    }

    public void setResults(List<com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search> results) {
        this.results = results;
    }

}
