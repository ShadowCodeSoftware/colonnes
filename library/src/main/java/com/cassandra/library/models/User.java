package com.cassandra.library.models;

import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
   
    private Role role;
}
