package com.daktilo.daktilo_backend.payload;

import com.daktilo.daktilo_backend.entity.*;
import com.daktilo.daktilo_backend.payload.request.*;
import com.daktilo.daktilo_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DTOMapper {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Category convertToCategoryEntity(CategoryDTO categoryDTO){
        return convertToCategoryEntity(null,categoryDTO);
    }

    public Category convertToCategoryEntity(UUID id,CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(id).orElse(new Category());

        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDesc(categoryDTO.getCategoryDesc());
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
        article.setAuthor(userRepository.findById(articleDTO.getAuthorId()).orElse(null));
        article.setDatePosted(articleDTO.getDatePosted());
        article.setArticleContent(articleDTO.getArticleContent());
        article.setArticleTitle(articleDTO.getArticleTitle());
        article.setCommentStatus(articleDTO.isCommentStatus());
        article.setActive(articleDTO.isActive());
        article.setInSlider(articleDTO.isInSlider());
        article.setVideoUrl(articleDTO.getVideoUrl());
        article.setPictureUrl(articleDTO.getPictureUrl());

        return article;
    }

    public Tag convertToTagEntity(TagDTO tagDTO){
        Tag tag = tagRepository.findByTagName(tagDTO.getTagName()).orElse(null);

        if(tag==null){
            tag = new Tag();
            tag.setTagName(tagDTO.getTagName());
        }

        Article newArticle = articleRepository.findById(tagDTO.getArticleId()).orElse(null);
        if(newArticle!=null){
            tag.getArticles().add(newArticle);
        }

        return tag;
    }

    public UserDTO convertToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(user.getEmail());
        user.setPhoneNumber(user.getPhoneNumber());
        Long dateJoined = userDTO.getDateJoined();
        if(dateJoined == null){
            dateJoined = ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toInstant().toEpochMilli();
        }
        user.setDateJoined(dateJoined);
        user.setAccountNonExpired(userDTO.isAccountNonExpired());
        user.setCredentialsNonExpired(userDTO.isCredentialsNonExpired());
        user.setEnabled(userDTO.isEnabled());
        user.setAccountNonLocked(userDTO.isAccountNonLocked());
        return userDTO;
    }

    public User convertToUserEntity(UserDTO userDTO, UUID id){
        User user;
        if(id!=null)
            user = userRepository.findById(id).orElse(null);
        else
            user = userRepository.findByUsername(userDTO.getUsername()).orElse(null);

        if(user!=null) {
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            user.setUsername(userDTO.getUsername());
            user.setEmail(user.getEmail());
            user.setPhoneNumber(user.getPhoneNumber());
            Long dateJoined = userDTO.getDateJoined();
            if(dateJoined == null){
                dateJoined = ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toInstant().toEpochMilli();
            }
            user.setDateJoined(dateJoined);
            user.setAccountNonExpired(userDTO.isAccountNonExpired());
            user.setCredentialsNonExpired(userDTO.isCredentialsNonExpired());
            user.setEnabled(userDTO.isEnabled());
            user.setAccountNonLocked(userDTO.isAccountNonLocked());
        }

        return user;
    }


}
