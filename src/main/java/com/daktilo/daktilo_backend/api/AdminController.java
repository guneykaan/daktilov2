package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.request.UserDTO;
import com.daktilo.daktilo_backend.repository.UserRepository;
import com.daktilo.daktilo_backend.service.UserService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/panel/v1")
//TODO spring security
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add/user")
    @Transactional
    public ResponseEntity addUser(@RequestBody UserDTO userDTO){
        try{
            User user = userService.addUser(userDTO);
            return ResponseEntity.ok(user);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Kullanıcı eklerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Kullanıcı eklerken beklenmedik bir hata oluştu.");
        }
    }

    @PutMapping(path="/edit/{id}")
    public ResponseEntity updateUser(@PathVariable("id") UUID id, @RequestBody UserDTO userDTO){
        try{
            User user = userService.update(id,userDTO);
            return ResponseEntity.ok(user);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Kullanıcı güncellenirken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Kullanıcı güncellenirken beklenmedik bir hata oluştu.");
        }
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity removeUser(@PathVariable("id") UUID id){
        try {
            User user = userRepository.findById(id).orElse(null);
            if(user!=null){
                userRepository.deleteById(id);
                return ResponseEntity.ok().body("Silme işlemi başarılı");
            }else{
                return ResponseEntity.badRequest().body("Silmek istediğiniz kullanıcı bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında beklenmedik bir hata oluştu.");
        }
    }

}
