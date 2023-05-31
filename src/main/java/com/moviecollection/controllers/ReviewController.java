package com.moviecollection.controllers;
import com.moviecollection.models.Review;
import com.moviecollection.models.ReviewRequest;
import com.moviecollection.repositories.MovieRepository;
import com.moviecollection.repositories.ReviewRepository;
import com.moviecollection.repositories.UserRepository;
import com.moviecollection.services.MovieService;
import com.moviecollection.services.ReviewService;
import com.moviecollection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieService movieService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, MovieRepository movieRepository, UserRepository userRepository, MovieService movieService, ReviewRepository reviewRepository){
        this.reviewService = reviewService;
        this.userService = userService;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.movieService = movieService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/search/{id}/add-review")
    public String showCreatedReview(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, @PathVariable Integer id, Model model){
        try {
            if (userId.isEmpty() || userService.verifyAdmin(Integer.valueOf(userId)))
                throw new RuntimeException("User session expired. Please login to try again");
            if (this.reviewRepository.findReviewByUserAndMovie(this.userRepository.findUserById(Integer.valueOf(userId)), this.movieRepository.findMovieById(id)) != null) {
                return "redirect:/search/{id}?status=REVIEW_EXISTS";
            }
            model.addAttribute("movie", this.movieRepository.findMovieById(id));
            return "add-review";
        }  catch (Exception exception) {
            return "redirect:login?status=REVIEW_ERROR&message=" + exception.getMessage();
        }
    }

    @PostMapping("/search/{id}/add-review")
    public String handleReviewCreation(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, @PathVariable Integer id, ReviewRequest reviewRequest){
        try {
            if (userId.isEmpty() || userService.verifyAdmin(Integer.valueOf(userId)))
                throw new RuntimeException("User session expired. Please login to try again");

            Review review = new Review(
                    reviewRequest.getDescription(),
                    reviewRequest.getRating(),
                    this.userRepository.findUserById(Integer.valueOf(userId)),
                    this.movieRepository.findMovieById(id)
            );
            this.reviewService.createReview(review);
            this.movieService.updateRating(this.movieRepository.findMovieById(id));
            this.movieRepository.save(this.movieRepository.findMovieById(id));

            return "redirect:/search/{id}?status=REVIEW_SUCCESS";
        } catch (Exception exception){
            exception.printStackTrace();
            return "redirect:/search/{id}?status=REVIEW_FAILED&message="+exception.getMessage();
        }
    }


}