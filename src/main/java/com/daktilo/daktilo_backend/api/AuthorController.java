package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.Author;
import com.daktilo.daktilo_backend.payload.request.AuthorDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.repository.AuthorRepository;
import com.daktilo.daktilo_backend.service.AuthorService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController

@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public ResponseEntity getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            Page<Author> authors = authorRepository.findAll(pageRequest);

            if (authors!=null && !authors.isEmpty()){
                return ResponseEntity.ok(authors);
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

    @GetMapping("/{id}")
    public ResponseEntity getOneById(@PathVariable UUID id){
        try{
            Author author = authorRepository.findById(id).orElse(null);
            if(author!=null){
                return ResponseEntity.ok(author);
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

    @GetMapping("/{id}/articles")
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

    @PostMapping("/{id}/deactivate/{status}")
    public ResponseEntity changeAuthorAccountStatus(@PathVariable(name="id") UUID id,
                                          @PathVariable(name="status") boolean status){
        try{
            Author author = authorRepository.findById(id).orElse(null);
            if(author!=null){
                author.setAuthorAccountStatus(status);
                Author saved = authorRepository.save(author);
                return ResponseEntity.ok(saved);
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

    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody AuthorDTO authorDTO){
        try{
            Author author = authorService.add(authorDTO);
            return ResponseEntity.ok(author);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazar eklemeye çalışırken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazar eklemeye çalışırken beklenmedik bir hata oluştu");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity editAuthorAccount(
            @PathVariable(name="id") UUID id,
            @RequestBody AuthorDTO authorDTO){
        try{
            Author author = authorService.update(id,authorDTO);
            return ResponseEntity.ok(author);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarı güncellemeye çalışırken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Yazarı güncellemeye çalışırken beklenmedik bir hata oluştu");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeAuthor(@PathVariable(name="id") UUID id){
        try {
            Author author = authorRepository.findById(id).orElse(null);

            if (author != null) {
                authorRepository.deleteById(id);
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