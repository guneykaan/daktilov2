package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment,UUID> {

    public List<Comment> findByUserId(UUID userId);

    public List<Comment> findByArticle_ArticleId(UUID articleId);
}
