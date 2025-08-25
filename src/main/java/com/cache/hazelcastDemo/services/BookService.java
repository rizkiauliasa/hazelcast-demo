package com.cache.hazelcastDemo.services;

import com.cache.hazelcastDemo.entities.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    List<Book> findAll();
    Book findById(Long id);
    void delete(Long id);
    void clearCache();
}
