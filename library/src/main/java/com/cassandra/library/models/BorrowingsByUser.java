package com.cassandra.library.models;

import java.util.UUID;
import java.time.LocalDate;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "borrowings_by_user")
public class BorrowingsByUser {

    @PrimaryKeyColumn(name = "user_email", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userEmail;

    @PrimaryKeyColumn(name = "book_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = Name.UUID)
    private UUID bookId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CassandraType(type = Name.TIMEUUID)
    private UUID timeUuid;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    @CassandraType(type = Name.TEXT)
    private String borrowingStatus;

    @CassandraType(type = Name.DATE)
    @Column("created_at")
    private LocalDate createdAt;

    @Column("expiration_date")
    @CassandraType(type = Name.DATE)
    private LocalDate expirationDelay;

    @Column("return_date")
    @CassandraType(type = Name.DATE)
    private LocalDate returnDate;

    public BorrowingsByUser() {

    }

    public BorrowingsByUser(String userEmail, UUID bookId, UUID timeUuid, String borrowingStatus, LocalDate createdAt,
            LocalDate expirationDelay, LocalDate returnDate) {
        this.userEmail = userEmail;
        this.bookId = bookId;
        this.timeUuid = timeUuid;
        this.borrowingStatus = borrowingStatus;
        this.createdAt = createdAt;
        this.expirationDelay = expirationDelay;
        this.returnDate = returnDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public UUID getTimeUuid() {
        return timeUuid;
    }

    public void setTimeUuid(UUID timeUuid) {
        this.timeUuid = timeUuid;
    }

    public String getBorrowingStatus() {
        return borrowingStatus;
    }

    public void setBorrowingStatus(String borrowingStatus) {
        this.borrowingStatus = borrowingStatus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getExpirationDelay() {
        return expirationDelay;
    }

    public void setExpirationDelay(LocalDate expirationDelay) {
        this.expirationDelay = expirationDelay;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowingsByUser [userEmail=" + userEmail + ", bookId=" + bookId + ", timeUuid=" + timeUuid
                + ", borrowingStatus=" + borrowingStatus + ", createdAt=" + createdAt + ", expirationDelay="
                + expirationDelay + ", returnDate=" + returnDate + "]";
    }

}
