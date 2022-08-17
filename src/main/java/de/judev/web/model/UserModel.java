package de.judev.web.model;

import lombok.Data;

@Data
public class UserModel {
    private Long id;
    private String email;
    private String password;
}