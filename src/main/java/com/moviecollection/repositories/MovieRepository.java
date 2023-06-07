package com.moviecollection.repositories;

import com.moviecollection.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findMovieById(Integer id);

    @Override
    List<Movie> findAll();

    List<Movie> findTop3ByOrderByRatingDesc();

    List<Movie> findAllByTitleContainingIgnoreCase(String title);
    List<Movie> findAllByGenre(String genre);
    List<Movie> findAllByActorsContainingIgnoreCase(String actors);
    List<Movie> findAllByRatingIsGreaterThanEqual(Double rating);

    List<Movie> findAllByTitleContainingIgnoreCaseAndGenreAndActorsContainingIgnoreCaseAndRatingIsGreaterThanEqual(String title, String genre, String actors, Double rating);

    List<Movie> findAllByTitleContainingIgnoreCaseAndGenre(String title, String genre);
    List<Movie> findAllByTitleContainingIgnoreCaseAndActorsContainingIgnoreCase(String title, String actors);
    List<Movie> findAllByTitleContainingIgnoreCaseAndRatingIsGreaterThanEqual(String title, Double rating);

    List<Movie> findAllByGenreAndActorsContainingIgnoreCase(String genre, String actors);
    List<Movie> findAllByGenreAndRatingIsGreaterThanEqual(String genre, Double rating);

    List<Movie> findAllByActorsAndRatingIsGreaterThanEqual(String actors, Double rating);

    List<Movie> findAllByTitleContainingIgnoreCaseAndGenreAndActorsContainingIgnoreCase(String title, String genre, String actors);
    List<Movie> findAllByTitleContainingIgnoreCaseAndGenreAndRatingIsGreaterThanEqual(String title, String genre, Double rating);
    List<Movie> findAllByTitleContainingIgnoreCaseAndActorsContainingIgnoreCaseAndRatingIsGreaterThanEqual(String title, String actors, Double rating);
    List<Movie> findAllByGenreAndActorsContainingIgnoreCaseAndRatingIsGreaterThanEqual(String genre, String actors, Double rating);

}
