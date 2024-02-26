package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.request.PasswordChangeDTO;
import com.daktilo.daktilo_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel/v1/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

}
