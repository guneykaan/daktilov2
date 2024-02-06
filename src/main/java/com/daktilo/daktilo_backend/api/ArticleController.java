package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.payload.request.ArticleDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.repository.TagRepository;
import com.daktilo.daktilo_backend.service.ArticleService;
import com.daktilo.daktilo_backend.util.PageImplCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ArticleService articleService;

    @GetMapping
    public Page<Article> getAllOrderedByDate(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size
    ){
       Pageable pageRequest = PageRequest.of(page,size);
       List<Article> articles = articleRepository.findAllByOrderByDatePostedDesc();

       return PageImplCustom.createPage(articles, pageRequest);
    }

    @GetMapping(path="/category/{category}")
    public Page<Article> findByCategory(
            @PathVariable("category") String category,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        Pageable pageRequest = PageRequest.of(page,size);
        List<Article> articles = articleRepository.
                findByCategories_CategoryNameOrderByDatePostedDesc(category);

        return PageImplCustom.createPage(articles,pageRequest);
    }

    @GetMapping(path="/tag/{tag}")
    public Page<Article> findByTags(
            @PathVariable("tag") String tag,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        Pageable pageRequest = PageRequest.of(page,size);
        List<Article> articles = articleRepository.findByTags_TagNameOrderByDatePostedDesc(tag);
        return PageImplCustom.createPage(articles, pageRequest);
    }

    @GetMapping(path="/read/{id}")
    public Article readArticle(@PathVariable("id") UUID id){
        Article article = articleRepository.findById(id).get();
        Long viewCount = article.getViewCount();
        article.setViewCount(++viewCount);
        return  articleRepository.save(article);
    }

    /*@GetMapping(path="/search/")
    public List<Article> searchArticles(@RequestParam("keyword") String keyword){

    }*/

    @GetMapping(path="/{id}")
    public Article findById(@PathVariable("id") UUID id){
        return articleRepository.findById(id).get();
    }

    @PostMapping(path="/add")
    public Article addArticle(@NonNull @RequestBody ArticleDTO articleDTO){
        if(articleDTO.getDatePosted() == null)
            articleDTO.setDatePosted(new Timestamp(System.currentTimeMillis()));

        return articleService.add(articleDTO);
        //TODO tag yeni mi, yeniyse save et burada, bu noktada ne exception fırlatıyor
        //UK üzerinden fırlatıyorsa exceptionı loglara bas geç.
    }

    @PutMapping(path="/edit/{id}")
    public Article updateArticle(@NonNull @RequestBody ArticleDTO articleDTO,
                               @PathVariable("id") UUID id){
        Article article = articleService.update(id,articleDTO);
        return article;
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteArticle(@PathVariable(name="id") UUID id){
        articleRepository.deleteById(id);
    }


    @PatchMapping(path="/activate/{id}/{activeStatus}")
    public Article changeActiveStatus(@PathVariable(name="id") UUID id,
                                      @PathVariable("activeStatus") boolean activeStatus){
        Article article = articleRepository.findById(id).get();
        article.setActive(activeStatus);
        return articleRepository.save(article);
    }

    //TODO
    //manşet haberler
    //manşet sıralaması
    //sıralamaya göre dizme

}
