package com.school.library.controller;

import com.school.library.model.Book;
import com.school.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/book/save")
    public @ResponseBody void saveBook(@RequestBody Book book) {
        libraryService.saveBook(book);
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
    public @ResponseBody List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @DeleteMapping("/book/delete")
    public @ResponseBody void deleteBook(@RequestBody Book book) {
        libraryService.deleteBook(book);
    }
}
