package at.htl.helpr.taskform.repository.filter;

import java.util.List;

public class EffortFilter implements TaskFilter {

    private int effort;

    public EffortFilter(int effort) {
        this.effort = effort;
    }


    @Override
    public void apply(StringBuilder query, List<Object> params) {
        query.append("effort = ? ");
        params.add(effort);
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }
}
