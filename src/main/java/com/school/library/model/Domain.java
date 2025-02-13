package com.school.library.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> books;

    public Domain(String name) {
        this.name = name;
    }

    public Domain() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
