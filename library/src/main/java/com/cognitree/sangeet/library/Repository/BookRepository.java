package com.cognitree.sangeet.library.Repository;

import com.cognitree.sangeet.library.Enum.Action;
import com.cognitree.sangeet.library.Model.Book;

import java.util.*;

public class BookRepository {
    private final DatabaseService databaseService;

    public BookRepository() {
        this.databaseService = new DatabaseService();
    }

    public List<Book> getAllBooks() {
        String query = Queries.GET_ALL_BOOKS_QUERY;
        return this.databaseService.executeGetQuery(query);
    }

    public List<Book> getAvailableBooks() {
        String query = Queries.GET_AVAILABLE_BOOKS_QUERY;
        List<Book> books = this.databaseService.executeGetQuery(query);

        return books;
    }

    public Book getBook(long id) {
        String query = Queries.GET_BOOK_QUERY + id + ";";
        List<Book> book = this.databaseService.executeGetQuery(query);

        if (book.isEmpty()) return null;

        return book.get(0);
    }

    public Book updateBook(long id, Action action) {
        String queryOne = Queries.GET_BOOK_QUERY + id + ";";
        String queryTwo;

        if (action.equals(Action.RESERVE)) {
            queryTwo = Queries.RESERVE_BOOK_QUERY + id + ";";
        }
        else queryTwo = Queries.RETURN_BOOK_QUERY + id + ";";

        List<Book> initialBook = this.databaseService.executeGetQuery(queryOne);

        if (initialBook.isEmpty() ||
                (action == Action.RESERVE && initialBook.get(0).getReserved()) ||
                (action == Action.RETURN && !initialBook.get(0).getReserved())) {
            return null;
        }

        this.databaseService.executeUpdateQuery(queryTwo);

        List<Book> book = this.databaseService.executeGetQuery(queryOne);

        if (book.isEmpty()) return null;

        return book.get(0);
    }
}
