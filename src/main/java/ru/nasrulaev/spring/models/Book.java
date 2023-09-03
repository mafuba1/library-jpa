package ru.nasrulaev.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;


@Entity
@Table(name = "book")
public class Book {

    private static final long TEN_DAYS = 864000000L;

    @Column(name = "book_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "author_name")
    @Size(min = 2, max = 100, message = "Author name length must be between 2 and 100 characters")
    @NotBlank
    private String authorName;

    @Column(name = "title")
    @NotBlank
    @Size(max = 200, message = "Book title must not be longer than 200 characters")
    private String title;

    @Column(name = "publication_year")
    @NotNull
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "holder_id", referencedColumnName = "user_id")
    private Person holder;

    @Column(name = "taking_time")
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date takingTime;

    @Transient
    private boolean isOverdue;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Person getHolder() {
        return holder;
    }

    public void setHolder(Person holder) {
        this.holder = holder;
    }

    public Date getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(Date takingTime) {
        this.takingTime = takingTime;
    }

    public boolean isOverdue() {
        return new Date().getTime() - takingTime.getTime() >= TEN_DAYS;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

}
