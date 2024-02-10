package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{

    public Optional<Tag> findByTagName(String tag);

    public Page<Tag> findAll(Pageable pageable);

}
