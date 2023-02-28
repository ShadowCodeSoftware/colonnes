package com.cassandra.library.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassandra.library.models.Book;
import com.cassandra.library.models.BorrowingsByUser;
import com.cassandra.library.repositories.BookRepository;
import com.cassandra.library.repositories.BorrowingsByUserRepository;
import com.cassandra.library.services.BorrowingsByUserService;

@RestController
@RequestMapping("/api/borrows")
public class BorrowsController {

    @Autowired
    private BorrowingsByUserService borrowingsByUserService;

    @Autowired
    private BorrowingsByUserRepository borrowingsByUserRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/by-user")
    public ResponseEntity<String> newBorrowingsByUser(@RequestBody BorrowingsByUser borrowingsByUser) {

        try {
            borrowingsByUserService.newBorrowingsByUser(borrowingsByUser);

            Optional<Book> book = bookRepository.findById(borrowingsByUser.getBookId());
            if (book.isPresent()) {
                Book bookToUpdate = book.get();
                bookToUpdate.setAvailable(false);
                bookRepository.save(bookToUpdate);

                return new ResponseEntity<>("borrows done successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("borrows failed, book not found",
                        HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<List<BorrowingsByUser>> getAllBorrowingsByUser() {
        try {
            List<BorrowingsByUser> borrows = new ArrayList<BorrowingsByUser>();

            borrowingsByUserRepository.findAll().forEach(borrows::add);

            if (borrows.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(borrows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<BorrowingsByUser>> getborrowsByUser(@PathVariable("email") String email) {
        try {
            List<BorrowingsByUser> borrows = new ArrayList<BorrowingsByUser>();
            borrowingsByUserRepository.findAllByUserEmail(email).forEach(borrows::add);

            if (borrows.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(borrows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/returned/{timeUuid}")
    public ResponseEntity<String> updateborrowsByUser(@PathVariable("timeUuid") UUID timeUuid,
            @RequestBody BorrowingsByUser borrowingsByUser) {
        try {
            Optional<BorrowingsByUser> borrowsToUpdate = borrowingsByUserRepository.findByTimeUuid(timeUuid);

            if (borrowsToUpdate.isPresent()) {
                BorrowingsByUser updatedBorrows = borrowsToUpdate.get();
                updatedBorrows.setReturnDate(LocalDate.now());
                updatedBorrows.setBorrowingStatus("off");

                Optional<Book> book = bookRepository.findById(borrowingsByUser.getBookId());

                if (book.isPresent()) {
                    Book bookToUpdate = book.get();
                    bookToUpdate.setAvailable(true);
                    bookRepository.save(bookToUpdate);

                } else {
                    return new ResponseEntity<>("book not found!", HttpStatus.NOT_FOUND);
                }
                borrowingsByUserRepository.save(updatedBorrows);
                return new ResponseEntity<>("updated successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("borrowing not found!", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}