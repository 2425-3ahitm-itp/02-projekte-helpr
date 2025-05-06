package at.htl.helpr.taskform.repository.filter;

import java.util.ArrayList;
import java.util.List;

public class TaskQueryBuilder {

    private final List<TaskFilter> filterList = new ArrayList<>();
    private int paramsCount = 0;

    public TaskQueryBuilder addFilter(TaskFilter taskFilter) {
        this.filterList.add(taskFilter);
        paramsCount++;
        return this;
    }

    /**
     * Builds a query string from the filters
     *
     * @param initialQuery - the initial query to append the filters to at the end
     * @return the query string
     */
    public String buildQuery(String initialQuery, List<Object> params) {
        StringBuilder query = new StringBuilder(initialQuery);

        query.append(" where ");

        for (int i = 0; i < filterList.size(); i++) {
            TaskFilter filter = filterList.get(i);
            filter.apply(query, params);
            if (i < filterList.size() - 1) {
                query.append(" and ");
            }
        }

        return query.toString();
    }

    public int getParamsCount() {
        return paramsCount;
    }

}
