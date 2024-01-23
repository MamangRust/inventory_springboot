package com.sanedge.inventoryspringboot.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanedge.inventoryspringboot.domain.request.auth.LoginRequest;
import com.sanedge.inventoryspringboot.domain.request.auth.RegisterRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.domain.response.auth.AuthResponse;
import com.sanedge.inventoryspringboot.domain.response.auth.TokenRefreshResponse;
import com.sanedge.inventoryspringboot.enums.ERole;
import com.sanedge.inventoryspringboot.exception.RefreshTokenException;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.RefreshToken;
import com.sanedge.inventoryspringboot.models.Role;
import com.sanedge.inventoryspringboot.models.User;
import com.sanedge.inventoryspringboot.repository.RoleRepository;
import com.sanedge.inventoryspringboot.repository.UserRepository;
import com.sanedge.inventoryspringboot.security.JwtProvider;
import com.sanedge.inventoryspringboot.security.UserDetailsImpl;
import com.sanedge.inventoryspringboot.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenServiceImpl refreshTokenServiceImpl;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder,
            RefreshTokenServiceImpl refreshTokenServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenServiceImpl = refreshTokenServiceImpl;

    }

    @Override
    public MessageResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateAccessToken(authentication);

        long expiresAt = jwtProvider.getjwtExpirationMs();
        Date date = new Date();
        date.setTime(expiresAt);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional<RefreshToken> existingRefreshToken = refreshTokenServiceImpl.findByUser(user);

        if (existingRefreshToken.isPresent()) {
            refreshTokenServiceImpl.updateExpiryDate(existingRefreshToken.get());
        } else {
            refreshTokenServiceImpl.deleteByUserId(userDetails.getId());
            RefreshToken refreshToken = refreshTokenServiceImpl.createRefreshToken(userDetails.getId());
            existingRefreshToken = Optional.of(refreshToken);
        }

        AuthResponse authResponse = AuthResponse.builder()
                .access_token(jwt)
                .refresh_token(existingRefreshToken.get().getToken())
                .expiresAt(dateFormat.format(date))
                .username(userDetails.getUsername())
                .build();

        log.info("User successfully logged in: {}", userDetails.getUsername());

        return MessageResponse.builder().message("Berhasil login").data(authResponse).build();
    }

    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        log.info("User registered successfully: {}", user.getUsername());

        return MessageResponse.builder().message("Successs create user").data(null).statusCode(200).build();

    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return this.userRepository.findByUsername(authentication.getName())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User name not found - " + authentication.getName()));
    }

    @Override
    public TokenRefreshResponse refreshToken(String refreshToken) {
        return refreshTokenServiceImpl.findByToken(refreshToken)
                .map(refreshTokenServiceImpl::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtProvider.generateTokenFromUsername(user.getUsername());

                    return new TokenRefreshResponse(newAccessToken, refreshToken);
                })
                .orElseThrow(() -> new RefreshTokenException(refreshToken,
                        "Invalid or expired refresh token"));
    }

    @Override
    public MessageResponse logout() {
        refreshTokenServiceImpl.deleteByUserId(getCurrentUser().getId());
        log.info("User logged out successfully");
        return MessageResponse.builder().message("Logout success").statusCode(200).build();
    }
}
