package com.algo.domain.common.utils;

import com.algo.domain.entities.Challenge;
import io.quarkus.mongodb.panache.PanacheQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PagedEntity<T> {
    private List<T> entities;
    private int pageSize;
    private int pageNumber;
    private int totalNumberOfPages;

    public PagedEntity<T> of(PanacheQuery<T> panacheQuery) {
        List<T> entities = panacheQuery.list();
        int pageSize = panacheQuery.page().size;
        int pageNumber = panacheQuery.page().index;
        int totalNumberOfPages = (int) Math.ceil((double) panacheQuery.count() / pageSize);
        return new PagedEntity<>(entities, pageSize, pageNumber, totalNumberOfPages);
    }
}
