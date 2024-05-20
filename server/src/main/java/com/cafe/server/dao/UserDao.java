package com.cafe.server.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private String email;

    private String name;

    private String password;

    private String contactNumber;
}
