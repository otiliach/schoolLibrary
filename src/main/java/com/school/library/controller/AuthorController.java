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

    @GetMapping("/all-authors")
    public @ResponseBody List<Author> getAllSkills() {
        return libraryService.getAllAuthors();
    }
}
