package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Query("SELECT a FROM Article a JOIN a.categories c WHERE c.categoryName = :categoryName ORDER BY a.datePosted DESC")
    Page<Article> findByCategories_CategoryNameOrderByDatePostedDesc(@Param("categoryName") String categoryName, Pageable pageable);

    Page<Article> findAllByOrderByDatePostedDesc(Pageable pageable);

    Page<Article> findByAuthor_IdOrderByDatePostedDesc(UUID id, Pageable pageable);

    @Query("SELECT a FROM Article a INNER JOIN a.tags t WHERE t.tagName IN :tagNames ORDER BY a.datePosted DESC")
    Page<Article> findByTags_TagNameOrderByDatePostedDesc(List<String> tagNames, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.inSlider = true ORDER BY a.datePosted DESC")
    Page<Article> findByInSliderTrueOrderByDatePostedDesc(Pageable pageable);

    //TODO kırık
    @Query("SELECT a FROM Article a INNER JOIN a.categories c " +
            "WHERE c.categoryName = :categoryName " +
            "AND a.inSlider = true " +
            "ORDER BY a.datePosted DESC")
    Page<Article> findByInSliderTrueAndCategories_CategoryNameOrderByDatePostedDesc(@Param("categoryName") String categoryName, Pageable pageable);

}
