package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Tag;
import com.daktilo.daktilo_backend.payload.request.TagDTO;
import com.daktilo.daktilo_backend.repository.TagRepository;
import com.daktilo.daktilo_backend.service.TagService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    @GetMapping
    public ResponseEntity getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Tag> tags = tagRepository.findAll(pageRequest);

            return ResponseEntity.ok(tags);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Etiketleri gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Etikerleri gösterirken beklenmedik bir problem oluştu");
        }
    }

    @PostMapping(path="/admin/add")
    public ResponseEntity addTag(@NonNull @RequestBody TagDTO tagDTO){
        try{
            return ResponseEntity.ok(tagService.add(tagDTO));
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Etiketi eklerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Etiketi eklerken beklenmedik bir hata oluştu.");
        }
    }

    @DeleteMapping(path="/admin/delete/")
    public ResponseEntity deleteArticle(@PathVariable(name="tag") Tag tag){
        try{
            tagRepository.delete(tag);
            return ResponseEntity.ok().body("Silme işlemi başarılı");
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında beklenmedik bir hata oluştu.");
        }
    }
}
