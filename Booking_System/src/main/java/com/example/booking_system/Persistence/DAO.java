package com.example.booking_system.Persistence;
import java.util.List;

public interface DAO<T> {
    public boolean add(T entity);
    public T read(int id);

    public List<T> readAll();
    public boolean remove(int id);
    public boolean update(T entity);
}
