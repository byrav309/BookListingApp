package com.exmample.android.booklistingapp;


public class BookDetails {

    private String title;
    private String author;
    private String bookThumbNail;

    public BookDetails(String title, String author, String bookThumbNail) {
        this.title = title;
        this.author = author;
        this.bookThumbNail = bookThumbNail;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookThumbNail() {
        return bookThumbNail;
    }

}
