package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.UserDTO;
import com.daktilo.daktilo_backend.repository.UserRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DTOMapper dtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return user;
    }

    public User addUser(UserDTO userDTO){
        User user = dtoMapper.convertToUserEntity(userDTO,null);

        try{
            return userRepository.save(user);
        }catch(PersistenceException p){
            throw p;
        }catch(Exception e){
            throw e;
        }
    }

    public User update(UUID id, UserDTO userDTO){
        User user = dtoMapper.convertToUserEntity(userDTO, id);

        try{
            return userRepository.save(user);
        }catch(PersistenceException p){
            throw p;
        }catch(Exception e){
            throw e;
        }
    }
}
