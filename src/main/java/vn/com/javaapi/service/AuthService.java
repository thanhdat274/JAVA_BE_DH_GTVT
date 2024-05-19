package vn.com.javaapi.service;

import vn.com.javaapi.bean.AuthResponse;
import vn.com.javaapi.entity.Users;

public interface AuthService {
    AuthResponse Signup(Users users);

    AuthResponse Login(Users users);

    //AuthResponse ForgotPassword(Users users);
    //
    //AuthResponse ResetPassword(String token, Users users);
}
