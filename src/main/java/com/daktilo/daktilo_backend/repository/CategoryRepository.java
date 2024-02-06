package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    public abstract Optional<Category> findByCategoryName(String name);

}
