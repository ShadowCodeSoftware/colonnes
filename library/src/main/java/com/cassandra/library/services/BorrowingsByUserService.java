package com.cassandra.library.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassandra.library.models.BorrowingsByUser;
import com.cassandra.library.repositories.BorrowingsByUserRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;

@Service
public class BorrowingsByUserService {
    @Autowired
    private BorrowingsByUserRepository borrowingsByUserRepository;

    public BorrowingsByUser newBorrowingsByUser(BorrowingsByUser borrowingsByUser) {
        var bookid = borrowingsByUser.getBookId();
        System.out.println(bookid);

        BorrowingsByUser borrowingsByUserToSave = new BorrowingsByUser(borrowingsByUser.getUserEmail(),
                borrowingsByUser.getBookId(), Uuids.timeBased(), "on", LocalDate.now(),
                LocalDate.now().plusDays(10),
                null);

        return borrowingsByUserRepository.save(borrowingsByUserToSave);

    }
}
