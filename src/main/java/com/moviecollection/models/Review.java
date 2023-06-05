package com.moviecollection.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movieId")
    private Movie movie;

    Timestamp createdAt;
    @PrePersist
    public void beforeSave(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Review(String description, Integer rating, User user, Movie movie) {
        this.description = description;
        this.rating = rating;
        this.user = user;
        this.movie = movie;
    }
}
