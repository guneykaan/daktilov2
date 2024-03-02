package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.ArticleDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    DTOMapper dtoMapper;

    @Transactional
    public Article add(ArticleDTO articleDTO){
        Article article = dtoMapper.convertToArticleEntity(articleDTO,null,"add");

        return articleRepository.save(article);
    }

    public Article update(UUID id, ArticleDTO articleDTO){
        Article article = dtoMapper.convertToArticleEntity(articleDTO,id,"update");

        return articleRepository.save(article);
    }

}
