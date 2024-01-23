package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.auth.LoginRequest;
import com.sanedge.inventoryspringboot.domain.request.auth.RegisterRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.domain.response.auth.TokenRefreshResponse;
import com.sanedge.inventoryspringboot.models.User;

public interface AuthService {
    public MessageResponse login(LoginRequest loginRequest);

    public MessageResponse register(RegisterRequest registerRequest);

    public TokenRefreshResponse refreshToken(String refreshToken);

    User getCurrentUser();

    public MessageResponse logout();
}
