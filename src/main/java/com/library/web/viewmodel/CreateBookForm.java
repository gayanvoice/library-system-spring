package com.library.web.viewmodel;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class CreateBookForm {

    @NotNull
    @Size(min = 6, max = 50)
    private String title;

    @NotNull
    @Size(min = 6, max = 2000)
    private String description;

    @Size(min = 6, max = 200)
    private String thumbnail;

    @NotNull
    @NumberFormat
    private long isbn10;

    @NotNull
    @NumberFormat
    private long isbn13;

    @NotNull
    @NumberFormat
    private long shelfId;

    @NotNull
    @NumberFormat
    private long authorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(long isbn10) {
        this.isbn10 = isbn10;
    }

    public long getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(long isbn13) {
        this.isbn13 = isbn13;
    }

    public long getShelfId() {
        return shelfId;
    }

    public void setShelfId(long shelfId) {
        this.shelfId = shelfId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}