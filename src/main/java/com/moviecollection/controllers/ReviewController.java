package com.moviecollection.controllers;
import com.moviecollection.models.Review;
import com.moviecollection.services.ReviewService;
import com.moviecollection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService){
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/add-review")
    public String showCreatedReview(@CookieValue(value="loggedInUserId", defaultValue = "") String userId){
        try {
            if (userId.isEmpty() || userService.verifyAdmin(Integer.valueOf(userId)))
                throw new RuntimeException("User session expired. Please login to try again");
            return "add-review";
        }  catch (Exception exception) {
            return "redirect:login?status=REVIEW_ERROR&message=" + exception.getMessage();
        }
    }

    @PostMapping("/add-review")
    public String handleReviewCreation(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, Review review){
        try {
            if (userId.isEmpty() || userService.verifyAdmin(Integer.valueOf(userId)))
                throw new RuntimeException("User session expired. Please login to try again");
            this.reviewService.createReview(review);
            return "redirect:add-review?status=REVIEW_SUCCESS";
        } catch (Exception exception){
            exception.printStackTrace();
            return "redirect:add-review?status=REVIEW_FAILED&message="+exception.getMessage();
        }
    }


}