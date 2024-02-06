package entity;

import com.daktilo.daktilo_backend.DaktiloBackendApplication;
import com.daktilo.daktilo_backend.entity.*;
import com.daktilo.daktilo_backend.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;
import java.util.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = DaktiloBackendApplication.class)
public class ArticleTest {

    Article article0 = new Article();

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TagRepository tagRepository;

    @BeforeEach
    void setup(){
        Category category1 = new Category("Gündem","Gündemin Sesi");
        Category category2 = new Category("Spor", "Spor");
        Category category3 = new Category("Siyaset","Siyaset");

        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();

        author1.setAuthorName("kedi");
        author1.setAuthorEmail("kedi@gmail.com");
        author1.setAuthorAccountStatus(true);
        author1.setUsername("kedi");
        author1.setPassword("kedii");

        author2.setAuthorName("kedi2");
        author2.setAuthorEmail("kedi2@gmail.com");
        author2.setAuthorAccountStatus(true);
        author2.setUsername("kedi2");
        author2.setPassword("kedii2");

        author3.setAuthorName("kedi3");
        author3.setAuthorEmail("kedi3@gmail.com");
        author3.setAuthorAccountStatus(true);
        author3.setUsername("kedi3");
        author3.setPassword("kedii3");


        Tag tag1 = new Tag();
        tag1.setTagName("hükümet");
        Tag tag2 = new Tag();
        tag2.setTagName("Galatasaray");
        Tag tag3 = new Tag();
        tag3.setTagName("marmaray");

        Article article1 = new Article();
        article1.setCategories(new HashSet<>(Arrays.asList(category1,category2)));
        article1.setTags(new HashSet<Tag>(Arrays.asList(tag1,tag2)));
        article1.setAuthor(author1);
        article1.setDatePosted(new Timestamp(System.currentTimeMillis()));
        article1.setArticleTitle("ARTICLE1");
        article1.setArticleContent("ARTICLE1 ARTICLE1 ARTICLE1");
        article1.setActive(true);

        Article article2 = new Article();
        article1.setCategories(new HashSet<>(Arrays.asList(category1,category3)));
        article1.setTags(new HashSet<Tag>(Arrays.asList(tag2,tag3)));
        article1.setAuthor(author2);
        article1.setDatePosted(new Timestamp(System.currentTimeMillis()+1000));
        article1.setArticleTitle("ARTICLE2");
        article1.setArticleContent("ARTICLE2 ARTICLE2 ARTICLE2");
        article1.setActive(true);

        Article article3 = new Article();

        article1.setCategories(new HashSet<>(Arrays.asList(category3)));
        article1.setTags(new HashSet<Tag>(Arrays.asList(tag1,tag3)));
        article1.setAuthor(author3);
        article1.setDatePosted(new Timestamp(System.currentTimeMillis()+10000000));
        article1.setArticleTitle("ARTICLE3");
        article1.setArticleContent("ARTICLE3 ARTICLE3 ARTICLE3");
        article1.setActive(true);

        List<Category> categories = new ArrayList<>(Arrays.asList(category1,category2,category3));
        List<Author> authors = new ArrayList<>(Arrays.asList(author1,author2,author3));
        List<Tag>   tags = new ArrayList<>(Arrays.asList(tag1,tag2,tag3));
        List<Article> articles = new ArrayList<>(Arrays.asList(article1,article2,article3));

        categoryRepository.saveAll(categories);
        authorRepository.saveAll(authors);
        tagRepository.saveAll(tags);
        articleRepository.saveAll(articles);
    }

    @AfterEach
    void cleanup(){
        articleRepository.deleteAll();
        tagRepository.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void findAllOrderedByDate(){
        List<Article> articlesFindAll = articleRepository.findAllByOrderByDatePostedDesc();
        List<Article> articlesAllDateDesc = new ArrayList<>(articlesFindAll);
        Collections.copy(articlesAllDateDesc, articlesFindAll);
        articlesAllDateDesc.sort(Comparator.comparing(Article::getDatePosted));

        assertEquals(articlesFindAll, articlesAllDateDesc);
    }

    @Test
    void findAllByCategory(){

    }

    @Test
    void findAllByTag(){

    }

    @Test
    void findOneById(){

    }

    @Test
    void findAllByAuthor(){

    }

    @Test
    void searchArticles(){

    }

    @Test
    void addArticle(){

    }

    @Test
    void addArticlesWithNewTags(){

    }

    @Test
    void updateArticleContent(){

    }

    @Test
    void changeArticleCategory(){

    }

    @Test
    void changeArticleTags(){

    }

    @Test
    void removeArticle(){

    }


    @Test
    void changeActiveStatusOfAnArticle(){

    }

}
