package com.cognitree.sangeet.Service;

public class ContactService {
    private final ContactService bookRepository = new ContactService();

    public Book getBook(long id) { return this.bookRepository.getBook(id); }

    public List<Book> getAllBooks() {
        return this.bookRepository.getAllBooks();
    }

    public List<Book> getAvailableBooks() {
        return this.bookRepository.getAvailableBooks();
    }

    public Boolean checkAvailability(long id) {
        return this.bookRepository.getBook(id).getIsReserved();
    }

    public Book reserveBook(long id) {
        return this.bookRepository.updateBook(id, Action.RESERVE);
    }

    public Book returnBook(long id) {
        return bookRepository.updateBook(id, Action.RETURN);
    }
}
