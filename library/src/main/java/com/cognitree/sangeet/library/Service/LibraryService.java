package com.cognitree.sangeet.library.Service;

import com.cognitree.sangeet.library.Enum.Action;
import com.cognitree.sangeet.library.Model.Book;
import com.cognitree.sangeet.library.Repository.BookRepository;

import java.util.List;

public class LibraryService {
    private final BookRepository bookRepository = new BookRepository();

    public Book getBook(long id) { return bookRepository.getBook(id); }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.getAvailableBooks();
    }

    public Boolean checkAvailability(long id) {
        return bookRepository.getBook(id).getReserved();
    }

    public Book reserveBook(long id) {
        Book book = bookRepository.updateBook(id, Action.RESERVE);
        if (book == null) {
            return null;
        }

        return book;
    }

    public Book returnBook(long id) {
        return bookRepository.updateBook(id, Action.RETURN);
    }
}
