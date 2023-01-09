package com.mainproject.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponseDto {

    private Object data;

    private PageInfo pageInfo;

    @Data
    @AllArgsConstructor
    static private class PageInfo {
        private int page;
        private int size;
        private Long totalElements;
        private int totalPages;
    }

    public static PageResponseDto of(List list, Page page) {
        PageInfo pageInfoResponse = new PageInfo(
                page.getNumber()+1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return new PageResponseDto(list,pageInfoResponse);
    }
}
