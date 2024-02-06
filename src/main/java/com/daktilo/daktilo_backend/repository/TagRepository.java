package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{

    public Tag findByTagName(String tag);

}
