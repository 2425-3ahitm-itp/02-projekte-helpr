package at.htl.helpr.taskform.repository.filter;

import java.util.List;

public class PostalCodeFilter implements TaskFilter {

    private String postalCode;

    public PostalCodeFilter(String postalCode) {
        this.postalCode = postalCode;
    }


    @Override
    public void apply(StringBuilder query, List<Object> params) {
        query.append("location like '?%' ");
        params.add(postalCode);
    }

    public String getEffort() {
        return postalCode;
    }

    public void setEffort(String postalCode) {
        this.postalCode = postalCode;
    }
}
