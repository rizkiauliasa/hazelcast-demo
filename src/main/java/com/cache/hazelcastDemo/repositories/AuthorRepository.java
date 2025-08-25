package com.cache.hazelcastDemo.repositories;

import com.cache.hazelcastDemo.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
