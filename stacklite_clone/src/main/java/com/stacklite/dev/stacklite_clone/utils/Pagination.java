package com.stacklite.dev.stacklite_clone.utils;

import com.stacklite.dev.stacklite_clone.Model.User;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Pagination {
    public static Pageable createPageable(Map<String, String> queryParameters) {

        // Extract pagination and sorting values using the PaginationUtil class
        Integer page = queryParameters.containsKey("page") ? Integer.parseInt(queryParameters.get("page")) : null;
        Integer pageSize = queryParameters.containsKey("pageSize") ? Integer.parseInt(queryParameters.get("pageSize"))
                : null;
        String sortField = queryParameters.getOrDefault("sortField", null);
        Sort.Direction sortDirection = queryParameters.containsKey("sortDirection")
                ? Sort.Direction.valueOf(queryParameters.get("sortDirection").toUpperCase())
                : null;

        // Set default values if parameters are not provided
        int defaultPage = 0;
        int defaultPageSize = 10;
        String defaultSortField = "id";
        Sort.Direction defaultSortDirection = Sort.Direction.ASC;

        // Apply default values if necessary
        int pageNum = page != null ? page : defaultPage;
        int size = pageSize != null ? pageSize : defaultPageSize;
        String field = sortField != null ? sortField : defaultSortField;
        Sort.Direction direction = sortDirection != null ? sortDirection : defaultSortDirection;

        // Create the Pageable object with the values
        return PageRequest.of(pageNum, size, Sort.by(direction, field));
    }

    public static Page<User> convertToPage(Optional<User> optionalUser, Pageable pageable) {
        User user = optionalUser.orElse(null);
        List<User> userList = user != null ? Collections.singletonList(user) : Collections.emptyList();
        return new PageImpl<>(userList, pageable, userList.size());
    }
}
