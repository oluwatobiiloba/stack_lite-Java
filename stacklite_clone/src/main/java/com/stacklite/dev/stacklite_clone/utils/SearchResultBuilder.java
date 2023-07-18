package com.stacklite.dev.stacklite_clone.utils;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultBuilder {

    public static Map<String, Object> buildResult(List<?> items, Page<?> itemPage) {
        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("count", itemPage.getTotalElements());
        result.put("page", itemPage.getNumber());
        result.put("pageSize", itemPage.getSize());
        result.put("totalPages", itemPage.getTotalPages());
        return result;
    }

}
