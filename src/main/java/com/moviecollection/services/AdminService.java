package com.moviecollection.services;

import com.moviecollection.models.Admin;

public class AdminService {

    public Admin verifyAdmin(String password) throws  Exception{
        Admin admin = this.verifyAdmin(password);
        if(admin == null)throw new Exception("Incorrect password");
        return admin;
    }
}
