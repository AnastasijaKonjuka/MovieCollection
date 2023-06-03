package com.moviecollection.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private Integer year;
    private String genre;
    private String description;
    private Integer runtime;
    private String trailerUrl;
    private String posterUrl;
    private Double rating;
    private String actors;

    public Movie(Integer id, String title, Integer year, String genre, String description, Integer runtime, String trailerUrl, String posterUrl, String actors) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.runtime = runtime;
        this.trailerUrl = trailerUrl;
        this.posterUrl = posterUrl;
        this.actors = actors;
    }
}
