package com.moviecollection.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieRequest {
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



}
