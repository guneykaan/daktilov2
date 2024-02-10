package com.daktilo.daktilo_backend.payload;

import com.daktilo.daktilo_backend.entity.*;
import com.daktilo.daktilo_backend.payload.request.*;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.repository.AuthorRepository;
import com.daktilo.daktilo_backend.repository.CategoryRepository;
import com.daktilo.daktilo_backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DTOMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ArticleRepository articleRepository;

    public Author convertToAuthorEntity(AuthorDTO authorDTO){
        Author author = new Author();

        author.setUsername(authorDTO.getUsername());
        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorEmail(authorDTO.getAuthorEmail());
        author.setPassword(authorDTO.getPassword());
        author.setAuthorAccountStatus(authorDTO.isAuthorAccountStatus());

        return author;
    }

    //TODO böyle bi entity dönünce update sırasında .save() patlıyor mu?
    public Category convertToCategoryEntity(CategoryDTO categoryDTO){
        Category category = categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).orElse(null);

        if(category==null) {
            category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryDesc(categoryDTO.getCategoryDesc());
        }
        return category;
    }

    public Article convertToArticleEntity(ArticleDTO articleDTO, UUID id,String method){
        Article article = null;

        if(id != null ) {
            article = articleRepository.findById(id).orElse(null);
        }

        if(article==null){
            if(Objects.equals(method, "update")){
                return null;
            }else{
                article = new Article();
            }
        }

        article.setCategories(articleDTO.getCategories().stream().map(
                this::convertToCategoryEntity).collect(Collectors.toSet())
        );
        article.setTags(articleDTO.getTags().stream().map(
                this::convertToTagEntity).collect(Collectors.toSet())
        );
        article.setAuthor(authorRepository.findById(articleDTO.getAuthorId()).orElse(null));
        article.setDatePosted(articleDTO.getDatePosted());
        article.setArticleContent(articleDTO.getArticleContent());
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
        Tag tag = tagRepository.findByTagName(tagDTO.getTagName()).orElse(null);

        if(tag==null){
            tag = new Tag();
            tag.setTagName(tagDTO.getTagName());
        }
        return tag;
    }


}
