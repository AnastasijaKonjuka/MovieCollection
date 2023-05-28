package com.moviecollection.repositories;

import com.moviecollection.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findMovieById(Integer id);

    @Override
    List<Movie> findAll();

    List<Movie> findAllByTitleContainingIgnoreCase(String title);
    List<Movie> findAllByGenre(String genre);
    List<Movie> findAllByActorsContainingIgnoreCase(String actors);
    List<Movie> findAllByRatingIsNullAndRatingIsGreaterThanEqual(Double rating);

    List<Movie> findAllByTitleContainingIgnoreCaseAndGenreAndActorsContainingIgnoreCaseAndRatingIsNullAndRatingIsGreaterThanEqual(String title, String genre, String actors, Double rating);

    List<Movie> findAllByTitleContainingIgnoreCaseAndGenre(String title, String genre);
    List<Movie> findAllByTitleContainingIgnoreCaseAndActorsContainingIgnoreCase(String title, String actors);
    List<Movie> findAllByTitleContainingIgnoreCaseAndRatingIsNullAndRatingIsGreaterThanEqual(String title, Double rating);

    List<Movie> findAllByGenreAndActorsContainingIgnoreCase(String genre, String actors);
    List<Movie> findAllByGenreAndRatingIsNullAndRatingIsGreaterThanEqual(String genre, Double rating);

    List<Movie> findAllByActorsAndRatingIsNullAndRatingIsGreaterThanEqual(String actors, Double rating);

    List<Movie> findAllByTitleContainingIgnoreCaseAndGenreAndActorsContainingIgnoreCase(String title, String genre, String actors);
    List<Movie> findAllByTitleContainingIgnoreCaseAndGenreAndRatingIsNullAndRatingIsGreaterThanEqual(String title, String genre, Double rating);
    List<Movie> findAllByTitleContainingIgnoreCaseAndActorsContainingIgnoreCaseAndRatingIsNullAndRatingIsGreaterThanEqual(String title, String actors, Double rating);
    List<Movie> findAllByGenreAndActorsContainingIgnoreCaseAndRatingIsNullAndRatingIsGreaterThanEqual(String genre, String actors, Double rating);

}
