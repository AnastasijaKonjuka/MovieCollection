package com.moviecollection.services;

import com.moviecollection.models.Movie;
import com.moviecollection.repositories.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public void createMovie(Movie movie) throws Exception{
        this.movieRepository.save(movie);
        
    }
    public void deleteMovie(Integer movieId) throws Exception{
        this.movieRepository.deleteById(Long.valueOf(movieId));
    }

    public void updateMovie(Integer movieId) {
    }

}
