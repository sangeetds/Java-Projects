package com.cognitree.sangeet.library.Repository;

public final class Queries {
    public static final String GET_ALL_BOOKS_QUERY = "SELECT * FROM book";
    public static final String GET_AVAILABLE_BOOKS_QUERY = "SELECT FROM book WHERE reserved != 1;";
    public static final String GET_BOOK_QUERY = "SELECT * FROM BOOK WHERE ID = ";
    public static final String RESERVE_BOOK_QUERY = "UPDATE BOOK SET RESERVED = 1 WHERE ID = ";
    public static final String RETURN_BOOK_QUERY = "UPDATE BOOK SET RESERVED = 0 WHERE ID = ";
}


