package com.moviecollection.repositories;
import com.moviecollection.models.Movie;
import com.moviecollection.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    List<Review> findAllByMovie(Movie movie);
}