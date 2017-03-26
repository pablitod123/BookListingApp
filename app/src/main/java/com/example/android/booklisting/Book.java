package com.example.android.booklisting;

/**
 * Created by paulstyslinger on 3/21/17.
 */

public class Book {

    //Title of the book
    private String mTitle;

    //Author of the book
    private String mAuthor;

    //Number of pages in the book
    private int mPages;

    /**
     * @param title is the title of the book
     * @param author is the first listed author of the book
     * @param pages is the number of pages in the book
     */
    public Book(String title, String author, int pages) {
        mTitle = title;
        mAuthor = author;
        mPages = pages;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public int getPages(){
        return mPages;
    }


}
