package com.cache.hazelcastDemo.repositories;

import com.cache.hazelcastDemo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
