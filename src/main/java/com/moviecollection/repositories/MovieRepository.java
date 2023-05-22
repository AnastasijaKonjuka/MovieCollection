package com.moviecollection.repositories;

import com.moviecollection.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findMovieByActors(String actors);
    Movie findMovieById(Integer id);
    Movie findMovieByGenre (String genre);


}
