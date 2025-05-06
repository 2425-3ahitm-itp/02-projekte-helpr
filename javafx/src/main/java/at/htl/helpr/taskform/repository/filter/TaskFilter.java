package at.htl.helpr.taskform.repository.filter;

import java.util.List;

public interface TaskFilter {

    void apply(StringBuilder query, List<Object> params);

}
