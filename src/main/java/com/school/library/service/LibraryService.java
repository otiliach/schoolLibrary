package com.school.library.service;

import com.school.library.model.Author;
import com.school.library.model.Book;
import com.school.library.model.Domain;
import com.school.library.repository.AuthorRepository;
import com.school.library.repository.BookRepository;
import com.school.library.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final DomainRepository domainRepository;

    @Autowired
    public LibraryService(final AuthorRepository authorRepository, final BookRepository bookRepository, final DomainRepository domainRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.domainRepository = domainRepository;
    }

    //Author
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    public Author updateAuthor(Author author, Long authorId) {
        Author authorDB = authorRepository.findById(authorId).orElse(null);
        System.out.println(authorId);
        System.out.println(authorDB);

        if (authorDB != null) {
            if (author.getFullName() != null)
                authorDB.setFullName(author.getFullName());

            return authorRepository.save(authorDB);
        }
        return null;
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    //Book
    @Transactional
    public void saveBook(Book book) {
        if (domainRepository.existsById(book.getDomain().getId())) {

            Domain domain = domainRepository.findById(book.getDomain().getId()).orElse(null);

            Iterable<Author> authors = authorRepository.findAllById(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));

            List<Author> authorList = new ArrayList<>();

            for (Author author : authors) {
                if (author != null) {
                    authorList.add(author);
                }
            }
            book.setAuthors(new ArrayList<>(authorList));
            book.setDomain(domain);

            System.out.println("Domain ID: " + book.getDomain().getId());
            System.out.println("Domain Name: " + book.getDomain().getName());

            bookRepository.save(book);
        }
    }

    public Book updateBook(Book book, Long bookId) {
        Book bookDB = bookRepository.findById(bookId).orElse(null);
        if (bookDB != null) {
            if (book.getTitle() != null)
                bookDB.setTitle(book.getTitle());
            if (book.getDescription() != null)
                bookDB.setDescription(book.getDescription());
            if (book.getIsbn() != null)
                bookDB.setIsbn(book.getIsbn());
            if (book.getDomain() != null)
                bookDB.setDomain(book.getDomain());
            if (book.getAuthors() != null) {
                List<Author> authors = book.getAuthors();

                List<Author> authorList = new ArrayList<>(authors);
                bookDB.setAuthors(authorList);
            }
            return bookRepository.save(bookDB);
        }
        return null;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Page<Book> searchBooks(String author, String title, List<Domain> categories, Pageable pageable) {

        ArrayList<Book> books = new ArrayList<>();
        this.bookRepository.findAll().forEach(books::add);

        List<Book> booksByAuthor = new ArrayList<>();
        if(author!=null && !author.isEmpty()) {
            booksByAuthor = books.stream()
                    .filter(book -> book.getAuthors().stream()
                            .anyMatch(author1 -> author1.getFullName().contains(author))).toList();
        } else {
            booksByAuthor = books;
        }

        List<Book> booksByAuthorAndTitle = new ArrayList<>();
        if(title!=null && !title.isEmpty()) {
            booksByAuthorAndTitle = booksByAuthor.stream().filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
        } else {
            booksByAuthorAndTitle = booksByAuthor;
        }

        List<Book> booksByAuthorTitleAndDomains = new ArrayList<>();
        if(categories!=null && !categories.isEmpty()) {
            booksByAuthorTitleAndDomains = booksByAuthorAndTitle.stream().filter(book -> categories.contains(book.getDomain())).toList();
        } else {
            booksByAuthorTitleAndDomains = booksByAuthorAndTitle;
        }

        return new PageImpl<>(booksByAuthorTitleAndDomains);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    //Domain
    public Domain saveDomain(Domain domain) {
        return domainRepository.save(domain);
    }

    public Domain updateDomain(Domain domain, Long domainId) {
        Domain domainDB = domainRepository.findById(domainId).orElse(null);

        if (domainDB != null) {
            domainDB.setName(domain.getName());

            return domainRepository.save(domainDB);
        }
        return null;
    }

    public Domain getDomainById(Long domainId) {
        return domainRepository.findById(domainId).orElse(null);
    }

    public List<Domain> getAllDomains() {
        return (List<Domain>) domainRepository.findAll();
    }

    public void deleteDomain(Long domainId) {
        domainRepository.deleteById(domainId);
    }
}
