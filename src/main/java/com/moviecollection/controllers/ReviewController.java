package com.moviecollection.controllers;
import com.moviecollection.models.Review;
import com.moviecollection.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/add-review")
    public String showCreatedReview(){
        return "add-review";
    }

    @PostMapping("/add-review")
    public String handleReviewCreation(Review review){
        try {
            this.reviewService.createReview(review);
            return "redirect:review?status=REVIEW_SUCCESS";
        } catch (Exception exception){
            exception.printStackTrace();
            return "redirect:review?status=REVIEW_FAILED&message="+exception.getMessage();
        }
    }


}