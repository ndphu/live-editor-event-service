package com.example.eventservice.service;


import com.example.eventservice.model.UserInfo;

public interface TokenService {
    UserInfo parseToken(String token);
}
