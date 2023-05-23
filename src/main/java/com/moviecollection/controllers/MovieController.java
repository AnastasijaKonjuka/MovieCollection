package com.moviecollection.controllers;

import com.moviecollection.models.Movie;
import com.moviecollection.services.MovieService;
import com.moviecollection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
class MovieController {

    private final UserService userService;
    private final MovieService movieService;


    @Autowired
    public MovieController(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping("/add-movie")
    public String showAddMoviePage(@CookieValue(value="loggedInUserId", defaultValue = "") String userId) {
        try {
            if (userId.isEmpty() || !userService.verifyAdmin(Integer.valueOf(userId))) throw new RuntimeException("Admin session expired. Please login to try again");
            return "add-movie";
        } catch (Exception exception) {
            return "redirect:login?status=ADMIN_ERROR&message=" + exception.getMessage();
        }
    }

    @PostMapping("/add-movie")
    public String addMovie(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, Movie movie) throws Exception {
        try {
            if (userId.isEmpty() || !userService.verifyAdmin(Integer.valueOf(userId))) throw new RuntimeException("Admin session expired. Please login to try again");
            this.movieService.createMovie(movie);
            return "redirect:add-movie?status=ADDED_SUCCESS";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:add-movie?status=FAILED_TO_ADD&message=" + exception.getMessage();
        }
    }

    @GetMapping("/delete-movie/{movieId}")
    public String deleteMovie(@PathVariable Integer movieId) throws Exception {
        this.movieService.deleteMovie(movieId);
        return "redirect:/?message=MOVIE DELETED";
    }


    @PutMapping("/update-movie/{movieId}")
    public String updateMovie(@PathVariable Integer movieId) throws Exception {
        try {
            this.movieService.updateMovie(movieId);
            return "redirect:update-movie?status=UPDATED_SUCCESS";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:update-movie?status=FAILED_TO_UPDATE&message=" + exception.getMessage();
        }

    }
}

