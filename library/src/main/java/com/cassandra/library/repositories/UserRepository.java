package com.cassandra.library.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.cassandra.library.models.User;

public interface UserRepository extends CassandraRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> deleteByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findByNameContaining(String name);

}
