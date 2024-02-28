package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.request.LoginRequest;
import com.daktilo.daktilo_backend.payload.request.PasswordChangeDTO;
import com.daktilo.daktilo_backend.payload.request.SignInRequest;
import com.daktilo.daktilo_backend.payload.response.AuthenticationResponse;
import com.daktilo.daktilo_backend.repository.UserRepository;
import com.daktilo.daktilo_backend.security.JwtService;
import com.daktilo.daktilo_backend.service.AuthService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signIn")
    @Transactional
    public ResponseEntity signIn(@RequestBody SignInRequest signInRequest){
        List<User> matchingUsers = userRepository
                .findByEmailOrUsername(signInRequest.getEmail(), signInRequest.getUsername());
        if(!matchingUsers.isEmpty()){
            return ResponseEntity.badRequest().body("Girdiğiniz email veya kullanıcı adı ile kayıtlı bir kullanıcı mevcut.");
        }

        try{
          User user = authService.handleSignIn(signInRequest);
          return ResponseEntity.ok(user);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Kayıt olurken bir hata yaşandı.");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Kayıt olurken beklenmedik bir hata yaşandı.");
        }
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        try{
            User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(ResourceNotFoundException::new);
            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            AuthenticationResponse authResp = new AuthenticationResponse();
            authResp.setUser(user);
            authResp.setAccessToken(jwtToken);
            authResp.setRefreshToken(refreshToken);
            return ResponseEntity.ok(authResp);
        }catch(ResourceNotFoundException rnfe){
           return ResponseEntity.badRequest().body("Giriş yapmaya çalıştığınız email ile kayıtlı bir kullanıcı bulunamadı");
        }
    }

    @PostMapping("/changePassword")
    @Transactional
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        String oldPass = passwordChangeDTO.getOldPassword();
        User user = userRepository.findByUsername(passwordChangeDTO.getUsername()).orElse(null);

        if(user == null){
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı");
        }

        String password = user.getPassword();
        boolean matches = bCryptPasswordEncoder.matches(oldPass, password);
        if(matches){
            user.setPassword(bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword()));
            return ResponseEntity.ok("Şifre başarıyla değiştirildi");
        }else{
            return ResponseEntity.badRequest().body("Girdiğiniz eski şifre doğru değil");
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        authService.refreshToken(request,response);
    }

}
