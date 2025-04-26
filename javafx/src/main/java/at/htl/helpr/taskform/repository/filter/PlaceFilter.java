package at.htl.helpr.taskform.repository.filter;

import java.util.List;

public class PlaceFilter implements TaskFilter {

    private String city;

    public PlaceFilter(String city) {
        this.city = city;
    }

    @Override
    public void apply(StringBuilder query, List<Object> params) {
        query.append(" like '?'");
        params.add(city);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
