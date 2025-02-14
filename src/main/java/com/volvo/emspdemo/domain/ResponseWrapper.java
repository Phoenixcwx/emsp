package com.volvo.emspdemo.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapper<T> {
    private String message;
    private String code;
    private T data;
    private PageMetadata page;

    // 构造方法
    public ResponseWrapper(String message, String code, T data, PageMetadata page) {
        this.message = message;
        this.code = code;
        this.data = data;
        this.page = page;
    }

    public static <T> ResponseWrapper<T> success(T data, PageMetadata page) {
        return new ResponseWrapper<T>("success", "200", data, page);
    }

    public static <T> ResponseWrapper<T> success(T data) {
        long dataSize = data == null ? 0 : 1;
        if(data instanceof Collection) {
            dataSize = ((Collection<?>)data).size();
        }
        return new ResponseWrapper<T>("success", "200", data, PageMetadata.all(dataSize));
    }

    public static <T> ResponseWrapper<T> fail(String message, String  code) {
        return new ResponseWrapper<T>(message, code, null, null);
    }

    // 分页元数据
    @Data
    public static class PageMetadata {
        private int currentPage;
        private int totalPages;
        private long totalItems;
        private int pageSize;

        public PageMetadata(int currentPage, int totalPages, long totalItems, int pageSize) {
            this.currentPage = currentPage;
            this.totalPages = totalPages;
            this.totalItems = totalItems;
            this.pageSize = pageSize;
        }

        public PageMetadata() {
        }

        public static PageMetadata empty() {
            return new PageMetadata(0, 0, 0, 0);
        }

        public static PageMetadata all(long totalItems) {
            return new PageMetadata(1, 1, totalItems, 1);
        }
    }

}
