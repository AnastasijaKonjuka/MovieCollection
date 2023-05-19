package com.moviecollection.controllers;

import com.moviecollection.models.Admin;
import com.moviecollection.models.Movie;
import com.moviecollection.models.MovieRequest;
import com.moviecollection.repositories.MovieRepository;
import com.moviecollection.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller
public class AdminController {

    private AdminService adminService;
    private MovieRepository movieRepository;
    private MovieRequest movieRequest;

    AdminController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @GetMapping("/login")
    public String handleAdminRegistration(Admin admin) {
        try {
            this.adminService.verifyAdmin(admin.getPassword());
            return "redirect:login?status=REGISTER_SUCCESS";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:register?status=REGISTER_FAILED&message=" + exception.getMessage();
        }

    }

    @GetMapping("/add-movie")
    public String displayMoviePage() {
        return "add-movie";
    }

    @PostMapping("/add-movie") //create new movie
    public String addMovie(MovieRequest movieRequest) {
        this.movieRepository.create(new Movie(
                null,
                movieRequest.getTitle(),
                movieRequest.getYear(),
                movieRequest.getGenre(),
                null,
                movieRequest.getRuntime(),
                movieRequest.getTrailerUrl(),
                movieRequest.getPosterUrl(),
                null,
                movieRequest.getActors()
        ));
        return "redirect:/?message=MOVIE_CREATED";
    }
    @GetMapping("/") //redirect to be able to see all movies
    public String displayAllMovies(@RequestParam(required = false) String message, Model model){
        ArrayList<Movie> movie = movieRepository.allMovie();
        model.addAttribute("movie", movie);
        model.addAttribute("message", message);
        return "all-movie";
    }

    @GetMapping("/update-movie/{movieId}")
    public String update(@PathVariable  MovieRequest movieRequest, @PathVariable String movieId) {
        Movie movie = new Movie(
                        null,
                        movieRequest.getTitle(),
                        movieRequest.getYear(),
                        movieRequest.getGenre(),
                        null,
                        movieRequest.getRuntime(),
                        movieRequest.getTrailerUrl(),
                        movieRequest.getPosterUrl(),
                        null,
                        movieRequest.getActors());

        this.movieRepository.update(movie);
        return "update-movie";
    }

    @GetMapping("/delete-movie/{movieId}")
    public String deleteMovie(@PathVariable Integer movieId) {
        this.movieRepository.delete(movieId);
        return "redirect:/?message=TODO DELETED";
    }


}
