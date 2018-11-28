package com.co.ametech.heroicapp.dao;

import java.util.List;

/**
 * Created by Alan M.E
 */

public interface BaseDao<E, T> {

    void add(E e);
    boolean delete(T id);
    E find(T id);
    List<E> toList();

}
