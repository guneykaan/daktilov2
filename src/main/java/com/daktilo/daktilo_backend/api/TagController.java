package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.entity.Tag;
import com.daktilo.daktilo_backend.payload.request.TagDTO;
import com.daktilo.daktilo_backend.repository.TagRepository;
import com.daktilo.daktilo_backend.service.TagService;
import com.daktilo.daktilo_backend.util.PageImplCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    @GetMapping
    public Page<Tag> getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        Pageable pageRequest = PageRequest.of(page,size);
        List<Tag> tags = tagRepository.findAll();

        return PageImplCustom.createPage(tags, pageRequest);
    }

    @GetMapping("/{tag}")
    public Page<Article> getArticlesByTag(
            @RequestParam(name="tag") String tag,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        Pageable pageRequest = PageRequest.of(page,size);

        List<Article> articles = tagRepository.findByTagName(tag).getArticles();

        return PageImplCustom.createPage(articles, pageRequest);
    }

    @PostMapping(path="/add")
    public Tag addTag(@NonNull @RequestBody TagDTO tagDTO){
        return tagService.add(tagDTO);
    }

    @PutMapping(path="/edit/{id}")
    public Tag updateTag(@NonNull @RequestBody TagDTO tagDTO,
                         @PathVariable("id") UUID id){
        return tagService.update(id,tagDTO);
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteArticle(@PathVariable(name="id") UUID id){
        tagRepository.deleteById(id);
    }
}
