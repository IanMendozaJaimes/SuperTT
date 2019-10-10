package com.equipo.superttapp.util;

import java.util.List;

public class BusinessResult<T> {
    private Integer code = ResultCodes.ERROR;
    private T result;
    private List<T> results;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
