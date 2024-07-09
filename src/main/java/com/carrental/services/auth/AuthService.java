package com.carrental.services.auth;


import com.carrental.dto.SignupRequest;
import com.carrental.dto.UserDto;

public interface AuthService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

}
