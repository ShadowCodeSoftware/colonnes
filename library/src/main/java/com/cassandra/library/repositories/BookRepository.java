package com.cassandra.library.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cassandra.library.models.Book;

@Repository
public interface BookRepository extends CassandraRepository<Book, UUID> {
    @AllowFiltering
  List<Book> findByAvailable(boolean available);
  
  List<Book> findByTitleContaining(String title);
}
