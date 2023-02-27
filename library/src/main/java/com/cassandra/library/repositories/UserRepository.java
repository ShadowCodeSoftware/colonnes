package com.cassandra.library.repositories;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.cassandra.library.models.User;

public interface UserRepository extends CassandraRepository<User, String> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
