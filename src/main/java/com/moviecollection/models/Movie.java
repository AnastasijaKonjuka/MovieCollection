package com.moviecollection.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


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
    private Double rating = null;
    private String actors;

    /*
    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Actor> actors;
    */
}
