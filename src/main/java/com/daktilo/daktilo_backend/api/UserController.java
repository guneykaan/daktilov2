package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Category;
import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.repository.UserRepository;
import com.daktilo.daktilo_backend.util.PageImplCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Deprecated
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Page<User> getAll(@RequestParam(name="page", defaultValue="0") int page,
                             @RequestParam(name="size", defaultValue="3") int size){

        Pageable pageRequest = PageRequest.of(page,size);
        List<User> users = userRepository.findAll();

        return PageImplCustom.createPage(users, pageRequest);
    }

    @GetMapping(path="/{id}")
    public User getById(@PathVariable("id") UUID id){
        return userRepository.findById(id).get();
    }


    @GetMapping(path="/{username}")
    public User getByUsername(@PathVariable("username") String username){
        return userRepository.findByUsername(username);
    }

    @PostMapping(path="/create")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping(path="/edit/{id}")
    public User updateUser(@PathVariable("id") UUID id,@RequestBody User user){
        return userRepository.save(user);
    }

    @DeleteMapping(path="/delete/{id}")
    public void removeUser(@PathVariable("id") UUID id){
        userRepository.deleteById(id);
    }

}
