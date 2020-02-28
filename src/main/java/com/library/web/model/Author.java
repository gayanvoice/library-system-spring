package com.library.web.model;

import com.library.web.repository.AuthorRepository;
import com.library.web.viewmodel.CreateAuthorForm;
import com.library.web.viewmodel.UpdateAuthorForm;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id", nullable = false)
    private long authorId;
    private String name;
    private String description;

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long employeeId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Author [authorId=" + authorId + ", name=" + name +
                ", description=" + description + "]";
    }

    public static Author from(CreateAuthorForm createAuthorForm) {
        Author author = new Author();
        author.setName(createAuthorForm.getName());
        author.setDescription(createAuthorForm.getDescription());
        return author;
    }

    // keeping null is not okay, but any other way to keep this fine?
    public static Author updateFrom(AuthorRepository authorRepository, UpdateAuthorForm updateAuthorForm) {
        return authorRepository.findByAuthorId(updateAuthorForm.getId())
                .map(a -> {
                    Author author = new Author();
                    author.setAuthorId(updateAuthorForm.getId());
                    author.setName(updateAuthorForm.getName());
                    author.setDescription(updateAuthorForm.getDescription());
                    return author;
                })
                .orElse(null);
    }
}
