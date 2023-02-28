package com.cassandra.library.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.cassandra.library.models.BorrowingsByUser;

public interface BorrowingsByUserRepository extends CassandraRepository<BorrowingsByUser, UUID> {
    List<BorrowingsByUser> findAllByUserEmail(String userEmail);

    @AllowFiltering
    Optional<BorrowingsByUser> findByTimeUuid(UUID timeUuid);
}
