package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Category;
import com.daktilo.daktilo_backend.payload.request.CategoryDTO;
import com.daktilo.daktilo_backend.repository.CategoryRepository;
import com.daktilo.daktilo_backend.service.CategoryService;
import com.daktilo.daktilo_backend.util.PageImplCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Page<Category> getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size
    ){
        Pageable pageRequest = PageRequest.of(page,size);
        List<Category> articles = categoryRepository.findAll();

        return PageImplCustom.createPage(articles, pageRequest);
    }

    @GetMapping(path="/{name}")
    public Category findByName(@PathVariable String name){
        return categoryRepository.findByCategoryName(name).get();
    }

    @PostMapping(path="/add")
    public Category addCategory(@NonNull @RequestBody CategoryDTO categoryDTO){
        return categoryService.add(categoryDTO);
    }

    @PutMapping(path="/edit/{id}")
    public Category updateCategory(@NonNull @RequestBody CategoryDTO categoryDTO,
                               @PathVariable("id") UUID id){
        return categoryService.update(id,categoryDTO);
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteArticle(@PathVariable(name="id") UUID id){
        categoryRepository.deleteById(id);
    }

}
