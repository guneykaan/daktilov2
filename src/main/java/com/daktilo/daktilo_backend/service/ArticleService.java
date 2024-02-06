package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.Tag;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.ArticleDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    DTOMapper dtoMapper;

    public Article add(ArticleDTO articleDTO){
        Article article = dtoMapper.convertToArticleEntity(articleDTO);

        return articleRepository.save(article);
    }

    public Article update(UUID id, ArticleDTO articleDTO){
        Article article = dtoMapper.convertToArticleEntity(articleDTO);

        article.setArticleId(id);

        return articleRepository.save(article);
    }

}
