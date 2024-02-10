package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.entity.Tag;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.TagDTO;
import com.daktilo.daktilo_backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    DTOMapper dtoMapper;

    public Tag add(TagDTO tagDTO){
        Tag tag = dtoMapper.convertToTagEntity(tagDTO);

        return tagRepository.save(tag);
    }


    public Tag update(UUID id, TagDTO tagDTO){
        Tag tag = dtoMapper.convertToTagEntity(tagDTO);

        return tagRepository.save(tag);
    }


    /*public void batchSaveTags(Set<Tag> tags, UUID articleId){
        if(articleId != null){
            tags.stream().map(tag->tag::set)
        }
    }*/

}
