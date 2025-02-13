package com.school.library.controller;

import com.school.library.model.Author;
import com.school.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/biblioteca")
public class AuthorController {
    private final LibraryService libraryService;

    public AuthorController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    @PostMapping("/autor/save")
    public @ResponseBody void saveAuthor(@RequestBody Author author) {
        libraryService.saveAuthor(author);
    }

    @PutMapping("/author/save/{id}")
    public @ResponseBody Author updateAuthor (@RequestBody Author author, @PathVariable(value = "id") Long  authorId) {
        return libraryService.updateAuthor(author, authorId);
    }
    @GetMapping("/author/{id}")
    public @ResponseBody Author getAuthorById(@PathVariable(value = "id") String  authorId) {
        return libraryService.getAuthorById(Long.valueOf(authorId));
    }

    @GetMapping("/all-authors")
    public @ResponseBody List<Author> getAllSkills() {
        return libraryService.getAllAuthors();
    }
}
