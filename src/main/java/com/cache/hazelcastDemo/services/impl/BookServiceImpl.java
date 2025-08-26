package com.cache.hazelcastDemo.services.impl;


import com.cache.hazelcastDemo.entities.Author;
import com.cache.hazelcastDemo.entities.Book;
import com.cache.hazelcastDemo.repositories.BookRepository;
import com.cache.hazelcastDemo.services.AuthorService;
import com.cache.hazelcastDemo.services.BookService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final IMap<Long, Book> booksCache;

    public BookServiceImpl(AuthorService authorService, BookRepository bookRepository, HazelcastInstance hazelcastInstance) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.booksCache = hazelcastInstance.getMap("books");
    }

    @Override
    public Book save(Book book) {
        Author author = authorService.findById(book.getAuthor().getId());
        book.setAuthor(author);
        Book saved = bookRepository.save(book);
        if (booksCache!=null) booksCache.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public List<Book> findAll() {
        if (!booksCache.isEmpty()) {
            return booksCache.values().stream().toList();
        }

        List<Book> books = bookRepository.findAll();
        books.forEach(book -> booksCache.put(book.getId(), book));

        return books;
    }

    @Override
    public Book findById(Long id) {
        Book book = booksCache.get(id);
        if (book != null) return book;

        book = bookRepository.findById(id).orElse(null);
        if (book != null) booksCache.put(id, book);

        return book;
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
        booksCache.remove(id);
    }

    @Override
    public void clearCache() {
        booksCache.clear();
    }

}
