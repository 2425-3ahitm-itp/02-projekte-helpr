package at.htl.helpr.taskform.repository.filter;

import java.util.List;

public class SearchFilter implements TaskFilter {

    private final String searchQuery;

    public SearchFilter(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public void apply(StringBuilder query, List<Object> params) {
        query.append("? % ANY(STRING_TO_ARRAY(title,' '))");
        params.add(searchQuery);
    }
}
