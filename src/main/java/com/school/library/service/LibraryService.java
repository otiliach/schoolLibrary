package com.school.library.service;

import com.school.library.model.Author;
import com.school.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private AuthorRepository authorRepository;
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }
}
