package com.moviecollection.services;

import com.moviecollection.models.Movie;
import com.moviecollection.models.Review;
import com.moviecollection.repositories.MovieRepository;
import com.moviecollection.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, ReviewRepository reviewRepository){
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    public void createMovie(Movie movie) throws Exception{
        this.movieRepository.save(movie);

    }
    public void deleteMovie(Integer movieId) throws Exception{
        this.movieRepository.deleteById(Long.valueOf(movieId));
    }

    public void updateRating(Movie movie) {
        List<Review> reviewList = this.reviewRepository.findAllByMovie(movie);

        Integer ratingSum = 0;
        for (Review review: reviewList) {
            ratingSum += review.getRating();
        }
        Integer ratingCount = reviewList.size();

        Double rating = (double) Math.round((Double.valueOf(ratingSum)/Double.valueOf(ratingCount)) * 10) / 10;
        if (rating > 5.0) rating = 5.0;

        this.movieRepository.findMovieById(movie.getId()).setRating(rating);
    }

    public List<Movie> movieSearch(String title, String genre, String actors, String rating) {

        if (!title.isEmpty() && genre.isEmpty() && actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByTitleContainingIgnoreCase(title);
        }

        if (title.isEmpty() && !genre.isEmpty() && actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByGenre(genre);
        }

        if (title.isEmpty() && genre.isEmpty() && !actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByActorsContainingIgnoreCase(actors);
        }

        if (title.isEmpty() && genre.isEmpty() && actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByRatingIsGreaterThanEqual(Double.parseDouble(rating));
        }

        if (!title.isEmpty() && !genre.isEmpty() && !actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndGenreAndActorsContainingIgnoreCaseAndRatingIsGreaterThanEqual(title, genre, actors, Double.parseDouble(rating));
        }

        if (!title.isEmpty() && !genre.isEmpty() && actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndGenre(title, genre);
        }

        if (!title.isEmpty() && genre.isEmpty() && !actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndActorsContainingIgnoreCase(title, actors);
        }

        if (!title.isEmpty() && genre.isEmpty() && actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndRatingIsGreaterThanEqual(title, Double.parseDouble(rating));
        }

        if (title.isEmpty() && !genre.isEmpty() && !actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByGenreAndActorsContainingIgnoreCase(genre, actors);
        }

        if (title.isEmpty() && !genre.isEmpty() && actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByGenreAndRatingIsGreaterThanEqual(genre, Double.parseDouble(rating));
        }

        if (title.isEmpty() && genre.isEmpty() && !actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByActorsAndRatingIsGreaterThanEqual(actors, Double.parseDouble(rating));
        }

        if (!title.isEmpty() && !genre.isEmpty() && !actors.isEmpty() && rating == null) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndGenreAndActorsContainingIgnoreCase(title, genre, actors);
        }

        if (!title.isEmpty() && !genre.isEmpty() && actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndGenreAndRatingIsGreaterThanEqual(title, genre, Double.parseDouble(rating));
        }

        if (!title.isEmpty() && genre.isEmpty() && !actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByTitleContainingIgnoreCaseAndActorsContainingIgnoreCaseAndRatingIsGreaterThanEqual(title, actors, Double.parseDouble(rating));
        }

        if (title.isEmpty() && !genre.isEmpty() && !actors.isEmpty() && !(rating == null)) {
            return this.movieRepository.findAllByGenreAndActorsContainingIgnoreCaseAndRatingIsGreaterThanEqual(genre, actors, Double.parseDouble(rating));
        }

        return this.movieRepository.findAll();
    }

}
