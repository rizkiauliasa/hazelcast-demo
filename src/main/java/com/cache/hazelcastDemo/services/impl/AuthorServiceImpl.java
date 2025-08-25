package com.cache.hazelcastDemo.services.impl;


import com.cache.hazelcastDemo.entities.Author;
import com.cache.hazelcastDemo.repositories.AuthorRepository;
import com.cache.hazelcastDemo.services.AuthorService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final IMap<Long, Author> authorsCache;

    public AuthorServiceImpl(AuthorRepository authorRepository, HazelcastInstance hazelcastInstance) {
        this.authorRepository = authorRepository;
        this.authorsCache = hazelcastInstance.getMap("authors");
    }

    @Override
    public Author save(Author author) {
        Author saved = authorRepository.save(author);
        if (authorsCache!=null) authorsCache.put(saved.getId(), author);
        return saved;
    }

    @Override
    public List<Author> findAll() {
        if (!authorsCache.isEmpty()) {
            return authorsCache.values().stream().collect(Collectors.toList());
        }

        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> authorsCache.put(author.getId(), author));

        return authors;
    }

    @Override
    public Author findById(Long id) {
        Author author = authorsCache.get(id);
        if (author!=null) return author;

        author = authorRepository.findById(id).orElse(null);
        if (author!=null) authorsCache.put(id, author);

        return author;
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
        authorsCache.remove(id);
    }

    @Override
    public void clearCache() {
        authorsCache.clear();
    }
}
