package ru.nasrulaev.spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Person")
public class Person {

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotBlank
    @Size(min = 2, message = "Full name should contain at least 2 characters")
    private String name;

    @Column(name = "birth_year")
    @Min(value = 1900, message = "Year of birth must be at least 1900")
    private int birthYear;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.PERSIST)
    private List<Book> bookList;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }


}
