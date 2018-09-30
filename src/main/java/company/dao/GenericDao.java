package company.dao;

import java.util.List;

public interface GenericDao<T, Long> {

    void create(T t);

    T read(Long id);

    T update(Long id);

    void delete(Long id);

    List<T> readAll();
}