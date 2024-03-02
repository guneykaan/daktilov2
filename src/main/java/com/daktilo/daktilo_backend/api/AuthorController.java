package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.constants.Role;
import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.User;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.UserDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.repository.UserRepository;
import com.daktilo.daktilo_backend.service.UserService;
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

@RequestMapping("/author")
public class AuthorController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    DTOMapper dtoMapper;

    @GetMapping("/v1")
    public ResponseEntity getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            Page<User> authors = userRepository.findAllByRole(pageRequest, Role.AUTHOR);
            List<UserDTO> authorDto = authors.stream().map(dtoMapper::convertToUserDTO).toList();
            if (authorDto!=null && !authorDto.isEmpty()){
                return ResponseEntity.ok(authorDto);
            }else{
                return ResponseEntity.badRequest().body("Yazar bulunamadı");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarları çekerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarları çekerken beklenmedik bir hata oluştu");
        }
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity getOneById(@PathVariable UUID id){
        try{
            User author = userRepository.findById(id).orElse(null);
            if(author!=null){
                return ResponseEntity.ok(dtoMapper.convertToUserDTO(author));
            }else{
                return ResponseEntity.badRequest().body("Yazar bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarı bulmaya çalışırken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarı bulmaya çalışırken beklenmedik bir hata oluştu");
        }
    }

    @GetMapping("/v1/{id}/articles")
    public ResponseEntity getAuthorArticles(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size,
            @PathVariable(name="id") UUID id){
        try {
            Pageable pageRequest = PageRequest.of(page, size);

            Page<Article> articles =
                    articleRepository.findByAuthor_IdOrderByDatePostedDesc(id, pageRequest);

            if (articles!=null && !articles.isEmpty()){
                return ResponseEntity.ok(articles);
            }else{
                return ResponseEntity.badRequest().body("Haber bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarın yazılarını bulmaya çalışırken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarın yazılarını bulmaya çalışırken beklenmedik bir hata oluştu");
        }
    }

    @PutMapping("/v2/{id}/deactivate/{status}")
    public ResponseEntity changeAuthorAccountStatus(@PathVariable(name="id") UUID id,
                                          @PathVariable(name="status") boolean status){
        try{
            User author = userRepository.findById(id).orElse(null);
            if(author!=null){
                author.setAccountNonLocked(status);
                User saved = userRepository.save(author);

                UserDTO authorDTO = dtoMapper.convertToUserDTO(saved);
                return ResponseEntity.ok(authorDTO);
            }else{
                return ResponseEntity.badRequest().body("Yazar bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarın durumunu güncellerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarın durumunu güncellerken beklenmedik bir hata oluştu");
        }
    }

    @PutMapping("/v2/edit/{id}")
    public ResponseEntity editAuthorAccount(
            @PathVariable(name="id") UUID id,
            @RequestBody UserDTO userDTO){
        try{
            User author = userService.update(id,userDTO);
            UserDTO authorDTO = dtoMapper.convertToUserDTO(author);
            return ResponseEntity.ok(authorDTO);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarı güncellemeye çalışırken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarı güncellemeye çalışırken beklenmedik bir hata oluştu");
        }
    }

    @DeleteMapping("/v2/{id}")
    public ResponseEntity removeAuthor(@PathVariable(name="id") UUID id){
        try {
            User author = userRepository.findById(id).orElse(null);

            if (author != null) {
                userRepository.deleteById(id);
                return ResponseEntity.ok().body("Silme işlemi başarılı");
            } else {
                return ResponseEntity.badRequest().body("Silmek istediğiniz yazar bulunamadı.");
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
