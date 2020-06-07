package com.wednesday.machinetest.model;

import java.util.List;

public class Result {
    private List<ArtiestDetails> results = null;

    private Integer resultCount;

    public List<ArtiestDetails> getResults() {
        return results;
    }

    public void setResults(List<ArtiestDetails> results) {
        this.results = results;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    @Override
    public String toString() {
        return "Result{" +
                "results=" + results +
                ", resultCount=" + resultCount +
                '}';
    }
}
