package company.dao;

import java.util.List;

public interface Dao<T> {

    T findById(Long id);
    List<T> findAll();
    void add(T t);
    void updateById(Long id, T entity);
    void deleteById(Long id);

}
