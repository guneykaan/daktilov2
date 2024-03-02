package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.repository.UserRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAll(@RequestParam(name="page", defaultValue="0") int page,
                                 @RequestParam(name="size", defaultValue="3") int size){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<User> users = userRepository.findAll(pageRequest);

            if(users != null && !users.isEmpty()){
                return ResponseEntity.ok(users);
            }else {
                return ResponseEntity.badRequest().body("Hiçbir kullanıcı bulunamadı");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Kullanıcıları gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Kullanıcıları gösterirken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping("/{id}")
    public User getById(@RequestParam("id") UUID id){
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping
    public User getByUsername(@RequestParam("username") String username){
        return userRepository.findByUsername(username).orElse(null);
    }

}
