package com.daktilo.daktilo_backend.service;

import com.daktilo.daktilo_backend.entity.Author;
import com.daktilo.daktilo_backend.payload.DTOMapper;
import com.daktilo.daktilo_backend.payload.request.AuthorDTO;
import com.daktilo.daktilo_backend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    DTOMapper dtoMapper;

    public Author add(AuthorDTO authorDTO) {
        Author author = dtoMapper.convertToAuthorEntity(authorDTO);

        return authorRepository.save(author);
    }


    public Author update(UUID id, AuthorDTO authorDTO) {
        Author author = dtoMapper.convertToAuthorEntity(authorDTO);

        author.setId(id);

        return authorRepository.save(author);
    }
}
