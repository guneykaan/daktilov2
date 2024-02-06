package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Query("SELECT a FROM Article a JOIN a.categories c WHERE c.categoryName = :categoryName ORDER BY a.datePosted DESC")
    List<Article> findByCategories_CategoryNameOrderByDatePostedDesc(@Param("categoryName") String categoryName);

    public List<Article> findAllByOrderByDatePostedDesc();

    public List<Article> findByAuthor_IdOrderByDatePostedDesc(UUID id);

    public List<Article> findByTags_TagNameOrderByDatePostedDesc(String tag);

}
