package com.cassandra.library.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassandra.library.models.Book;
import com.cassandra.library.repositories.BookRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book){
        Book bookToSave = new Book(Uuids.timeBased(), book.getTitle(), book.getAuthor(), book.getGenre(), true);
        return bookRepository.save(bookToSave);
    }


}
