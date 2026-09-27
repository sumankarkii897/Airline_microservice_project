package com.himalayan.payload.response;

import java.util.List;
import lombok.*;

@Data
@Builder
public class PageResponse <T>{
    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

}
