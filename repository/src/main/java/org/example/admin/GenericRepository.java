package org.example.admin;

import java.util.List;

public interface GenericRepository <T> {

    List<T> findAll();
}
