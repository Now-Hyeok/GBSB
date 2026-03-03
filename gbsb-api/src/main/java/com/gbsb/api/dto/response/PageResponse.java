package com.gbsb.api.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Custom paginated response that matches the frontend's expected format:
 * { content: [...], page: 0, size: 12, totalElements: 100, totalPages: 9, last: false, first: true }
 *
 * Spring Boot's PagedModel serializes differently (nested page object),
 * so we use this flat structure instead.
 */
@Getter
public class PageResponse<T> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean last;
    private final boolean first;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
        this.first = page.isFirst();
    }

    public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last, boolean first) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
        this.first = first;
    }
}
