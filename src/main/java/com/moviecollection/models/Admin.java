package com.moviecollection.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin {

    private String password = "admin";
    private String createdAt;
    private String updatedAt;


}

