package com.moviecollection.services;
import com.moviecollection.repositories.ReviewRepository;
import com.moviecollection.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    public void createReview(Review review) throws Exception {
        this.reviewRepository.save(review);
    }

}