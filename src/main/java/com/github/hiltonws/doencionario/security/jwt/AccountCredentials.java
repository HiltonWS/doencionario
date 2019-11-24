package com.github.hiltonws.doencionario.security.jwt;

import lombok.Data;

@Data
public class AccountCredentials {
    private String username;
    private String password;
}
