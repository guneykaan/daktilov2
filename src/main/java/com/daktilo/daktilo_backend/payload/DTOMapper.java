package com.daktilo.daktilo_backend.payload;

import com.daktilo.daktilo_backend.entity.*;
import com.daktilo.daktilo_backend.payload.request.*;
import com.daktilo.daktilo_backend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DTOMapper {

    @Autowired
    AuthorRepository authorRepository;

    public Author convertToAuthorEntity(AuthorDTO authorDTO){
        Author author = new Author();

        author.setUsername(authorDTO.getUsername());
        author.setAuthorName(authorDTO.getAuthorName());
        author.setPassword(authorDTO.getPassword());
        author.setAuthorAccountStatus(authorDTO.isAuthorAccountStatus());

        return author;
    }

    //TODO böyle bi entity dönünce update sırasında .save() patlıyor mu?
    public Category convertToCategoryEntity(CategoryDTO categoryDTO){
        Category category = new Category();

        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDesc(categoryDTO.getCategoryDesc());

        return category;
    }

    public Article convertToArticleEntity(ArticleDTO articleDTO){
        Article article = new Article();

        article.setCategories(articleDTO.getCategories());
        article.setTags(articleDTO.getTags().stream().map(
                tagDTO-> convertToTagEntity(tagDTO)).collect(Collectors.toSet())
        );
        article.setAuthor(authorRepository.findById(articleDTO.getAuthorId()).get());
        article.setDatePosted(articleDTO.getDatePosted());
        article.setArticleTitle(articleDTO.getArticleContent());
        article.setArticleTitle(articleDTO.getArticleTitle());
        article.setCommentStatus(articleDTO.isCommentStatus());
        article.setActive(articleDTO.isActive());

        return article;
    }

    public Admin convertToAdminEntity(AdminDTO adminDTO){
        Admin admin = new Admin();

        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());
        admin.setRole(adminDTO.getRole());
        return admin;
    }

    public Tag convertToTagEntity(TagDTO tagDTO){
        Tag tag = new Tag();

        tag.setTagName(tagDTO.getTagName());
        return tag;
    }


}
