package com.cognitree.sangeet.library.Service;

import com.cognitree.sangeet.library.Enum.Action;
import com.cognitree.sangeet.library.Model.Book;
import com.cognitree.sangeet.library.Repository.BookRepository;

import java.util.List;

public class LibraryService {
    private final BookRepository bookRepository = new BookRepository();

    public Book getBook(long id) { return this.bookRepository.getBook(id); }

    public List<Book> getAllBooks() {
        return this.bookRepository.getAllBooks();
    }

    public List<Book> getAvailableBooks() {
        return this.bookRepository.getAvailableBooks();
    }

    public Boolean checkAvailability(long id) {
        return this.bookRepository.getBook(id).getReserved();
    }

    public Book reserveBook(long id) {
        return this.bookRepository.updateBook(id, Action.RESERVE);
    }

    public Book returnBook(long id) {
        return bookRepository.updateBook(id, Action.RETURN);
    }
}
