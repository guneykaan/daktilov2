package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.Comment;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
@Deprecated
public class CommentController {

  /*  @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public Page<Comment> getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        Pageable pageRequest = PageRequest.of(page,size);
        List<Comment> comments = commentRepository.findAll(pag);

        return PageImplCustom.createPage(comments, pageRequest);    }

    @GetMapping("/{userId}")
    public List<Comment> getCommentsMadeByUser(@PathVariable(name="userId") UUID userId){
        return commentRepository.findByUserId(userId);
    }

    @GetMapping("/{articleId}")
    public List<Comment> getCommentsByArticle(@PathVariable(name="articleId") UUID articleId){
       return commentRepository.findByArticle_ArticleId(articleId);
    }

    //test
    @PostMapping("/{articleId}")
    public Comment postCommentToArticle(@PathVariable(name="articleId") UUID articleId,
                                        @RequestBody Comment comment){
        Article article = articleRepository.findById(articleId).get();
        article.getComments().add(comment);
        articleRepository.save(article);
        return comment;
    }

    @PutMapping("/edit/{id}")
    public Comment editComment(@PathVariable(name="{id}") UUID id,
                               @RequestBody Comment comment){
        return commentRepository.save(comment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable(name="{id}") UUID id){
        commentRepository.deleteById(id);
    }*/
}
