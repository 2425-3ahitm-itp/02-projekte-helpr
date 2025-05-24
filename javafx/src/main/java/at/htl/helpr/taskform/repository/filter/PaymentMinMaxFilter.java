package at.htl.helpr.taskform.repository.filter;

import java.util.List;

public class PaymentMinMaxFilter implements TaskFilter {

    private int min, max;

    public PaymentMinMaxFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void apply(StringBuilder query, List<Object> params) {
        query.append("reward between ? and ?");
        params.add(min);
        params.add(max);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
