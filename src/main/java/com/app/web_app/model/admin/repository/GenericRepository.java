package com.app.web_app.model.admin.repository;

import java.util.List;

public interface GenericRepository <T> {

    List<T> findAll();
}
