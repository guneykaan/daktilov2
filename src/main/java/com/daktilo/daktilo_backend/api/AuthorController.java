package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.Author;
import com.daktilo.daktilo_backend.payload.request.AuthorDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.repository.AuthorRepository;
import com.daktilo.daktilo_backend.service.AuthorService;
import com.daktilo.daktilo_backend.util.PageImplCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping
    public Page<Author> getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        Pageable pageRequest = PageRequest.of(page,size);
        List<Author> authors = authorRepository.findAll();

        return PageImplCustom.createPage(authors, pageRequest);
    }

    @GetMapping("/{id}")
    public Author getOneById(@PathVariable UUID id){
        return authorRepository.findById(id).get();
    }

    @GetMapping("/{id}/articles")
    public Page<Article> getAuthorArticles(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size,
            @PathVariable(name="id") UUID id){
        Pageable pageRequest = PageRequest.of(page,size);

        List<Article> articles =
                articleRepository.findByAuthor_IdOrderByDatePostedDesc(id);

        return PageImplCustom.createPage(articles, pageRequest);
    }

    @PostMapping("/{id}/deactivate/{status}")
    public void changeAuthorAccountStatus(@PathVariable(name="id") UUID id,
                                          @PathVariable(name="status") boolean status){
        Author author = authorRepository.findById(id).get();
        author.setAuthorAccountStatus(status);
        authorRepository.save(author);
    }

    @PostMapping("/add")
    public Author addAuthor(@RequestBody AuthorDTO authorDTO){
        return authorService.add(authorDTO);
    }

    @PutMapping("/edit/{id}")
    public Author editAuthorAccount(
            @PathVariable(name="id") UUID id,
            @RequestBody AuthorDTO authorDTO){
        return authorService.update(id,authorDTO);
    }

    @DeleteMapping("/{id}")
    public void removeAuthor(@PathVariable(name="id") UUID id){
        authorRepository.deleteById(id);
    }
}
