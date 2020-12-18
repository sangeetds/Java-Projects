package com.cognitree.sangeet.library.Model;


import java.util.Objects;
import java.util.UUID;

public class Book {
    private Long id;
    private String title;
    private String author;
    private Boolean reserved;

    public Book() {

    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.reserved = false;
    }

    public Book(Long id, String title, String author, Boolean reserved) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.reserved = reserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && title.equals(book.title) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", reserved=" + reserved +
                '}';
    }
}
