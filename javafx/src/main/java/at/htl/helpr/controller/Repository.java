package at.htl.helpr.controller;

import java.util.List;

public interface Repository <T> {
    public void create(T entity);
    public void update(T entity);
    public void delete(long id);
    public List<T> findAll();
    public T findById(long id);
}