package com.school.library.service;

import com.school.library.model.Author;
import com.school.library.model.Book;
import com.school.library.model.Domain;
import com.school.library.repository.AuthorRepository;
import com.school.library.repository.BookRepository;
import com.school.library.repository.DomainRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final DomainRepository domainRepository;

    public LibraryService(final AuthorRepository authorRepository,final BookRepository bookRepository,final DomainRepository domainRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.domainRepository = domainRepository;
    }

    //Author
    public void saveAuthor(Author author) {
        authorRepository.save(author);
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

    public void deleteBook(Book book) {
        bookRepository.deleteById(book.getId());
    }

    //Domain
    public void saveDomain(Domain domain) {
        domainRepository.save(domain);
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
