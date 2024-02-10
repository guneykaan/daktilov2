package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Article;
import com.daktilo.daktilo_backend.payload.request.ArticleDTO;
import com.daktilo.daktilo_backend.payload.request.CategoryDTO;
import com.daktilo.daktilo_backend.repository.ArticleRepository;
import com.daktilo.daktilo_backend.service.ArticleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    EntityManager entityManager;

    @GetMapping
    @Transactional
    public ResponseEntity getAllOrderedByDate(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size
    ){
       try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Article> articles = articleRepository.findAllByOrderByDatePostedDesc(pageRequest);

           if (articles!=null && !articles.isEmpty()){
               return ResponseEntity.ok(articles);
           }else{
               return ResponseEntity.badRequest().body("Haber bulunamadı.");
           }
       }catch(PersistenceException p){
           p.printStackTrace();
           return ResponseEntity.badRequest().body("Haberleri gösterirken bir problem oluştu");
       }catch(Exception e){
           e.printStackTrace();
           return ResponseEntity.badRequest().body("Haberleri gösterirken beklenmedik bir problem oluştu");
       }
    }

    //gündem ayrı bir category olduğu için burası kullanılır ve tarihe göre sıralar
    @GetMapping(path="/category/{category}")
    @Transactional
    public ResponseEntity findByCategory(
            @PathVariable("category") String category,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Article> articles = articleRepository.
                    findByCategories_CategoryNameOrderByDatePostedDesc(category, pageRequest);

            if (articles!=null && !articles.isEmpty()){
                return ResponseEntity.ok(articles);
            }else{
                return ResponseEntity.badRequest().body("Aradığınız kategoride haber bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body(category + " kategorisindeki haberleri gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(category + " kategorisindeki haberleri gösterirken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping(path="/slider")
    @Transactional
    public ResponseEntity getArticlesInSlider(
            @PathVariable(value = "category", required = false) String categoryName,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size
    ){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Article> articlesInSlider = null;

            if(categoryName!=null && !categoryName.isEmpty()) {
                articlesInSlider = articleRepository.
                        findByInSliderTrueAndCategories_CategoryNameOrderByDatePostedDesc(categoryName, pageRequest);
            }else{
                articlesInSlider = articleRepository.findByInSliderTrueOrderByDatePostedDesc(pageRequest);
            }

            if (articlesInSlider!=null && !articlesInSlider.isEmpty()){
                return ResponseEntity.ok(articlesInSlider);
            }else{
                return ResponseEntity.badRequest().body("Aradığınız kategoride haber bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Haberleri gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Haberleri gösterirken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping(path="/searchByTags")
    @Transactional
    public ResponseEntity findByTag(
            @RequestParam(name="tags") List<String> tags,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size){
        try {
            Pageable pageRequest = PageRequest.of(page, size);

            Page<Article> articles = articleRepository.findByTags_TagNameOrderByDatePostedDesc(tags, pageRequest);
            if (articles!=null && !articles.isEmpty()){
                return ResponseEntity.ok(articles);
            }else{
                return ResponseEntity.badRequest().body("Aradığınız etiketle haber bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Haberi kaydederken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Haberi kaydederken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping(path="/read/{id}")
    @Transactional
    public ResponseEntity readArticle(@PathVariable("id") UUID id){
        try{
            Article article = articleRepository.findById(id).orElse(null);
            if(article!=null){
                Long viewCount = article.getViewCount();
                article.setViewCount(++viewCount);
                Article saved = articleRepository.save(article);
                return ResponseEntity.ok(saved);
            }
            else{
                return ResponseEntity.badRequest().body("Aradığınız haber bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Haberi okurken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Haberi okurken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping(path="/search")
    @Transactional
    public ResponseEntity searchArticles(@RequestParam("keyword") String keyword){
        try{
            String searchQuery = "SELECT a FROM Article a WHERE " +
                    "a.articleTitle LIKE :keyword OR " +
                    "a.articleContent LIKE :keyword ";

            List<Article> articles = entityManager.createQuery(searchQuery, Article.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
            return ResponseEntity.ok().body(articles);
        } catch(PersistenceException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Arama sırasında bir hata oluştu.");
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Arama sırasında beklenmedik bir hata oluştu.");
        }
    }

    @GetMapping(path="/{id}")
    @Transactional
    public Article findById(@PathVariable("id") UUID id){
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping(path="/add")
    @Transactional
    public ResponseEntity addArticle(@NonNull @RequestBody ArticleDTO articleDTO){
       ResponseEntity rp = validate(articleDTO);
       if(rp!=null){
          return rp;
       }

       if(articleDTO.getDatePosted() == null)
            articleDTO.setDatePosted(new Timestamp(System.currentTimeMillis()));

       try {
           Article article = articleService.add(articleDTO);

           //TODO Article objesinde tags'e cascadeType.PERSIST eklendi, düzgün
           //bir şekilde yeni tag'ler varsa onları kaydediyor mu bak
           //article referanslarında problem oluşuyor mu bak

           return ResponseEntity.ok(article);
       }catch(PersistenceException p){
           p.printStackTrace();
           return ResponseEntity.badRequest().body("Haberi eklerken bir hata oluştu");
       }catch(Exception e){
           e.printStackTrace();
           return ResponseEntity.badRequest().body("Haberi eklerken beklenmedik bir hata oluştu.");
       }
    }

    @PutMapping(path="/edit/{id}")
    @Transactional
    public ResponseEntity updateArticle(@NonNull @RequestBody ArticleDTO articleDTO,
                               @PathVariable("id") UUID id){
        try {
            ResponseEntity rp = validate(articleDTO);
            if (rp != null) {
                return rp;
            }
            Article article = articleService.update(id, articleDTO);
            return ResponseEntity.ok(article);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Haberi güncellerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Haberi güncellerken beklenmedik bir hata oluştu.");
        }
    }

    @DeleteMapping(path="/delete/{id}")
    @Transactional
    public ResponseEntity deleteArticle(@PathVariable(name="id") UUID id){
        try {
            Article article = articleRepository.findById(id).orElse(null);
            if(article!=null){
                article.getTags().clear();
                article.getCategories().clear();
                articleRepository.deleteById(id);
            }
            else {
                return ResponseEntity.badRequest().body("Silmek istediğiniz haber bulunamadı.");
            }
            return ResponseEntity.ok().body("Silme işlemi başarılı");
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında beklenmedik bir hata oluştu.");
        }
    }


    @PutMapping(path="/activate/{id}/{activeStatus}")
    @Transactional
    public ResponseEntity changeActiveStatus(@PathVariable(name="id") UUID id,
                                      @PathVariable("activeStatus") boolean activeStatus){
        try{
            Article article = articleRepository.findById(id).orElse(null);

            if(article!=null){
                article.setActive(activeStatus);
                Article updatedArticle = articleRepository.save(article);
                return ResponseEntity.ok(updatedArticle);
            }else{
                return ResponseEntity.badRequest().body("Haber bulunamadı");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Haberin durumunu güncellerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Haberin durumunu güncellerken beklenmedik bir hata oluştu.");
        }
    }

    public ResponseEntity validate(ArticleDTO articleDTO){
        Set<CategoryDTO> categories = articleDTO.getCategories();
        if(categories == null || categories.isEmpty()){
            return ResponseEntity.badRequest().body("Lütfen haber kategorilerini boş bırakmayınız");
        }
        String articleTitle = articleDTO.getArticleTitle();
        if(articleTitle == null || articleTitle.isEmpty() ){
            return ResponseEntity.badRequest().body("Lütfen habere başlık ekleyiniz.");
        }
        String articleContent = articleDTO.getArticleContent();
        if(articleContent == null || articleContent.isEmpty()){
            return ResponseEntity.badRequest().body("Lütfen haber içeriğini ekleyiniz.");
        }
        return null;
    }
}
