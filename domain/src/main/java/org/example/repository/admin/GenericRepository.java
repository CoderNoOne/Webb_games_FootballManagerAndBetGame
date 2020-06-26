package org.example.repository.admin;

import org.example.annotations.NoJpaRepository;
import org.springframework.context.annotation.Profile;

import java.util.List;

@NoJpaRepository
public interface GenericRepository <T> {

    List<T> findAll();
}
