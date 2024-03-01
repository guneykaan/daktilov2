package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Category;
import com.daktilo.daktilo_backend.payload.request.CategoryDTO;
import com.daktilo.daktilo_backend.repository.CategoryRepository;
import com.daktilo.daktilo_backend.service.CategoryService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/v1")
    public List<Category> getAll(){
        List<Category> categories = categoryRepository.findAll();

        if(categories!=null || !categories.isEmpty()){
            return categories;
        }else{
            return null;
        }
    }

    @GetMapping("/v1/{name}")
    public Category findByName(@PathVariable String name){
        return categoryRepository.findByCategoryName(name).orElse(null);
    }

    @PostMapping("/v2/add")
    public Category addCategory(@NonNull @RequestBody CategoryDTO categoryDTO){
        return categoryService.add(categoryDTO);
    }

    @PutMapping("/v2/edit/{id}")
    public Category updateCategory(@NonNull @RequestBody CategoryDTO categoryDTO,
                               @PathVariable("id") UUID id){
        return categoryService.update(id,categoryDTO);
    }

    @DeleteMapping("/v2/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable(name="id") UUID id){
        try{
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Kategori silme işlemi başarılı.");
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında beklenmedik bir hata oluştu.");
        }
    }

}
