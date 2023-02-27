package com.cassandra.library.models;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table(value = "user_by_email")
public class User {

    @PrimaryKeyColumn(name = "user_email", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String email;

    @Column("user_name")
    @CassandraType(type = Name.TEXT)
    private String name;

    @Column("phone_number")
    @CassandraType(type = Name.TEXT)
    private String phoneNumber;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
    }

}
