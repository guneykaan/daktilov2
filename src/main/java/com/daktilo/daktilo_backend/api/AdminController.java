package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.constants.Role;
import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.request.RoleRequest;
import com.daktilo.daktilo_backend.payload.request.UserDTO;
import com.daktilo.daktilo_backend.repository.UserRepository;
import com.daktilo.daktilo_backend.service.UserService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
//TODO spring security
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private List<Role> roles = List.of(Role.ADMIN,Role.AUTHOR,Role.READER);

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

    @PutMapping(path="/update/{userId}/user-role")
    public ResponseEntity updateUserRole(@PathVariable("userId") UUID id, @RequestBody RoleRequest role){
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Role newUserRole = roles.stream().filter(
                    r -> r.toString().equals(role.getRole())
            ).findFirst().orElse(null);
            if (newUserRole == null) {
                return ResponseEntity.badRequest().body("Lütfen rolünü değiştirmek istediğiniz kullanıcı için yeni bir rol tanımlayınız");
            }
            user.setRole(newUserRole);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.badRequest().body("Aradığınız kullanıcı bulunamadı.");
        }
    }

}
