package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.constants.Role;
import com.daktilo.daktilo_backend.entity.Token;
import com.daktilo.daktilo_backend.entity.TokenType;
import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.request.SignInRequest;
import com.daktilo.daktilo_backend.payload.response.AuthenticationResponse;
import com.daktilo.daktilo_backend.repository.TokenRepository;
import com.daktilo.daktilo_backend.repository.UserRepository;
import com.daktilo.daktilo_backend.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JwtService jwtService;

    private static final Role DEFAULT_USER_ROLE = Role.READER;

    public User handleSignIn(SignInRequest signInRequest){
        try {
            User user = new User();

            user.setUsername(signInRequest.getUsername());
            user.setFirstName(signInRequest.getFirstName());
            user.setLastName(signInRequest.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(signInRequest.getPassword()));
            user.setEmail(signInRequest.getEmail());
            user.setPhoneNumber(signInRequest.getPhoneNumber());
            user.setDateJoined(System.currentTimeMillis());
            user.setRole(DEFAULT_USER_ROLE);

            return userRepository.save(user);
        }catch(PersistenceException p){
            throw p;
        }catch(Exception e){
            throw e;
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
                token.setUser(user);
                token.setToken(jwtToken);
                token.setTokenType(TokenType.BEARER);
                token.setExpired(false);
                token.setRevoked(false);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse();
                        authResponse.setAccessToken(accessToken);
                        authResponse.setRefreshToken(refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
