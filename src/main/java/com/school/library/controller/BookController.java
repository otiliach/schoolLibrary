package com.school.library.controller;

import com.school.library.model.Book;
import com.school.library.model.Domain;
import com.school.library.service.LibraryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/library")
public class BookController {
    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/book/save")
    public @ResponseBody ResponseEntity<String> saveBook(@RequestBody Book book) {
        try {
            libraryService.saveBook(book);
            return ResponseEntity.ok("Cartea a fost salvată cu succes!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Eroare la salvarea cărții: " + e.getMessage());
        }
    }

    @PutMapping("/book/save/{id}")
    public @ResponseBody Book updateBook (@RequestBody Book book, @PathVariable(value = "id") Long  bookId) {
        return libraryService.updateBook(book, bookId);
    }
    @GetMapping("/book/{id}")
    public @ResponseBody Book getBookById(@PathVariable(value = "id") String  bookId) {
        return libraryService.getBookById(Long.valueOf(bookId));
    }

    @GetMapping("/all-books")
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        Map<String, Object> response = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = authentication != null &&
                                  SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                                  !(authentication instanceof AnonymousAuthenticationToken);

        response.put("isAuthenticated", isAuthenticated);
        response.put("books", libraryService.getAllBooks());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/books-filtered")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size);

        List<Domain> domains;

        if(categories == null || categories.isEmpty()) {
            domains = libraryService.getAllDomains();
        } else {
            domains = categories.stream().map(libraryService::getDomainById).collect(Collectors.toList());
        }

        Page<Book> books = libraryService.searchBooks(author, title, domains, pageable);

        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/book/delete/{bookId}")
    public @ResponseBody void deleteBook(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
    }
}
