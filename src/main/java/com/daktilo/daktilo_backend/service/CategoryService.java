package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.entity.Category;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.CategoryDTO;
import com.daktilo.daktilo_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DTOMapper dtoMapper;

    public Category add(CategoryDTO categoryDTO){
        Category category = dtoMapper.convertToCategoryEntity(categoryDTO);

        return categoryRepository.save(category);
    }

    public Category update(UUID id, CategoryDTO categoryDTO){
        Category category = dtoMapper.convertToCategoryEntity(categoryDTO);
        category.setId(id);

        return categoryRepository.save(category);
    }

}
