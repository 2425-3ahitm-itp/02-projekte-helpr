package at.htl.helpr.taskform.repository.filter;

import java.time.LocalDate;
import java.util.List;

public class DateFromToFilter implements TaskFilter {

    private final LocalDate fromDate;
    private final LocalDate toDate;

    public DateFromToFilter(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void apply(StringBuilder query, List<Object> params) {
        query.append("created_at between ? and ?");
        params.add(fromDate);
        params.add(toDate);
    }
}
