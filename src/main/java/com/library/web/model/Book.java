package com.library.web.model;

import com.library.web.repository.AuthorRepository;
import com.library.web.repository.BookRepository;
import com.library.web.viewmodel.CreateBookForm;
import com.library.web.viewmodel.UpdateAuthorForm;
import com.library.web.viewmodel.UpdateBookForm;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", nullable = false)
    private long bookId;
    private String title;
    private String description;
    private String thumbnail;
    private long isbn10;
    private long isbn13;
    private long shelfId;
    private long authorId;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

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

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", title=" + title +
                ", description=" + description + "]";
    }

    public static Book from(CreateBookForm createBookForm) {
        Book book = new Book();
        book.setTitle(createBookForm.getTitle());
        book.setDescription(createBookForm.getDescription());
        book.setThumbnail(createBookForm.getThumbnail());
        book.setIsbn10(createBookForm.getIsbn10());
        book.setIsbn13(createBookForm.getIsbn13());
        book.setShelfId(createBookForm.getShelfId());
        book.setAuthorId(createBookForm.getAuthorId());
        return book;
    }

    // keeping null is not okay, but any other way to keep this fine?
    public static Book updateFrom(BookRepository bookRepository, UpdateBookForm updateBookForm) {
        return bookRepository.findByBookId(updateBookForm.getId())
                .map(a -> {
                    Book book = new Book();
                    book.setBookId(updateBookForm.getId());
                    book.setTitle(updateBookForm.getTitle());
                    book.setDescription(updateBookForm.getDescription());
                    book.setThumbnail(updateBookForm.getThumbnail());
                    book.setIsbn10(updateBookForm.getIsbn10());
                    book.setIsbn13(updateBookForm.getIsbn13());
                    book.setShelfId(updateBookForm.getShelfId());
                    book.setAuthorId(updateBookForm.getAuthorId());
                    return book;
                })
                .orElse(null);
    }


}
