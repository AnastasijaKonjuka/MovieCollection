package com.moviecollection.controllers;

import com.moviecollection.models.Movie;
import com.moviecollection.models.MovieRequest;
import com.moviecollection.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
class MovieController {


    private MovieController movieRepository;
    private MovieRequest movieRequest;
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/register")
    public String displayMoviePage() {
        return "/register";
    }


    @PostMapping("/add-movie")
    public String addMovie(Movie movie) throws Exception {
        try {
            this.movieService.createMovie(new Movie());
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


//    @PostMapping("/add-movie") //create new movie
//    public String addMovie(MovieRequest movieRequest) throws Exception {
//        this.movieService.createMovie(new Movie(
//                null,
//                movieRequest.getTitle(),
//                movieRequest.getYear(),
//                movieRequest.getGenre(),
//                null,
//                movieRequest.getRuntime(),
//                movieRequest.getTrailerUrl(),
//                movieRequest.getPosterUrl(),
//                null,
//                movieRequest.getActors()
//        ));
//        return "redirect:/?message=MOVIE_CREATED";
//    }
//    @GetMapping("/") //redirect to be able to see all movies
//    public String displayAllMovies(@RequestParam(required = false) String message, Model model){
//        ArrayList<Movie> movie = movieRepository.allMovie();
//        model.addAttribute("movie", movie);
//        model.addAttribute("message", message);
//        return "all-movie";
//    }
//
//    @GetMapping("/update-movie/{movieId}")
//    public String update(@PathVariable  MovieRequest movieRequest, @PathVariable String movieId) {
//        Movie movie = new Movie(
//                        null,
//                        movieRequest.getTitle(),
//                        movieRequest.getYear(),
//                        movieRequest.getGenre(),
//                        null,
//                        movieRequest.getRuntime(),
//                        movieRequest.getTrailerUrl(),
//                        movieRequest.getPosterUrl(),
//                        null,
//                        movieRequest.getActors());
//
//        this.movieRepository.update(movie);
//        return "update-movie";
//    }
//





