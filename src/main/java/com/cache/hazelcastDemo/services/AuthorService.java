package com.cache.hazelcastDemo.services;

import com.cache.hazelcastDemo.entities.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);
    List<Author> findAll();
    Author findById(Long id);
    void delete(Long id);
    void clearCache();
}