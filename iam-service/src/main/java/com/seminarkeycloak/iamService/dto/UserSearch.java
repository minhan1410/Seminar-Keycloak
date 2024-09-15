package com.seminarkeycloak.iamService.dto;

import lombok.Data;

@Data
public class UserSearch {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
//    private String phoneNumber;
//    private String address;
    private int pageNumber;
    private int pageSize;
}
