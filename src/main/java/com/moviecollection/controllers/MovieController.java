package com.moviecollection.controllers;

import com.moviecollection.models.Movie;
import com.moviecollection.models.MovieRequest;
import com.moviecollection.models.Review;
import com.moviecollection.models.SearchRequest;
import com.moviecollection.repositories.MovieRepository;
import com.moviecollection.repositories.ReviewRepository;
import com.moviecollection.services.MovieService;
import com.moviecollection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Controller
class MovieController {

    private final UserService userService;
    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;


    @Autowired
    public MovieController(UserService userService, MovieService movieService, MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
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
            return "redirect:add-movie?status=ADDED_SUCCESS?message=MOVIE_CREATED";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:add-movie?status=FAILED_TO_ADD&message=" + exception.getMessage();
        }
    }

    @GetMapping("/movie-list")
    public String displayAllMovies(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, @RequestParam(required = false) String message, Model model) {
        if (userId.isEmpty() || !userService.verifyAdmin(Integer.valueOf(userId))) throw new RuntimeException("Admin session expired. Please login to try again");

        List<Movie> movieList = movieRepository.findAll();
      model.addAttribute("movieList", movieList);
      model.addAttribute("message", message);
      return "movie-list";
    }

    @GetMapping("/update-movie/{movieId}")
    public String showUpdateMoviePage(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, @PathVariable Integer movieId, Model model) {
        try {
            if (userId.isEmpty() || !userService.verifyAdmin(Integer.valueOf(userId))) throw new RuntimeException("Admin session expired. Please login to try again");
            Movie movie = movieRepository.findMovieById(movieId);
            model.addAttribute("movie", movie);
            return "update-movie";
        } catch (Exception exception) {
            return "redirect:login?status=ADMIN_ERROR&message=" + exception.getMessage();
        }
    }

    @PostMapping("/update-movie/{movieId}")
    public String updateMovie(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, @PathVariable Integer movieId, MovieRequest movieRequest) throws Exception {
        try {
            if (userId.isEmpty() || !userService.verifyAdmin(Integer.valueOf(userId))) throw new RuntimeException("Admin session expired. Please login to try again");
            if (movieId == null) throw new RuntimeException("Movie not found");

            Movie movie = new Movie(
                    movieId,
                    movieRequest.getTitle(),
                    movieRequest.getYear(),
                    movieRequest.getGenre(),
                    movieRequest.getDescription(),
                    movieRequest.getRuntime(),
                    movieRequest.getTrailerUrl(),
                    movieRequest.getPosterUrl(),
                    movieRepository.findMovieById(movieId).getRating(),
                    movieRequest.getActors()
            );
            this.movieRepository.save(movie);
            return "redirect:/movie-list?message=MOVIE_UPDATED";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:/movie-list?status=FAILED_TO_UPDATE&message=" + exception.getMessage();
        }

    }

    @GetMapping("/delete-movie/{movieId}")
    public String deleteMovie(@CookieValue(value="loggedInUserId", defaultValue = "") String userId, @PathVariable Integer movieId) throws Exception {
       try{
           if (userId.isEmpty() || !userService.verifyAdmin(Integer.valueOf(userId))) throw new RuntimeException("Admin session expired. Please login to try again");
           List<Review> reviewToDelete = this.reviewRepository.findAllByMovie(this.movieRepository.findMovieById(movieId));
           for (Review review : reviewToDelete) {
               this.reviewRepository.delete(review);
           }
           this.movieService.deleteMovie(movieId);
           return "redirect:/movie-list?message=MOVIE_DELETED";
    } catch (Exception exception) {
        exception.printStackTrace();
        return "redirect:/movie-list?status=FAILED_TO_DELETE&message=" + exception.getMessage();
    }
    }

    @GetMapping("/search")
    public String showSearchPage(Model model) {
        List<Movie> movies = this.movieRepository.findAll();
        List<String> allGenres = movies.stream()
                .map(Movie::getGenre)
                .collect(Collectors.toList());
        List<String> genres = new ArrayList<>(new HashSet<>(allGenres));
        model.addAttribute("genreList", genres);
        return "search";
    }


    @PostMapping("/search")
    public String handleSearch(SearchRequest searchRequest, Model model) {
        String title = searchRequest.getTitle();
        String genre = searchRequest.getGenre();
        String actors = searchRequest.getActors();
        String rating = searchRequest.getRating();

        model.addAttribute("movieList", this.movieService.movieSearch(title, genre, actors, rating));

        return "search-results";
    }

    @GetMapping("/search/{id}")
    public String showMoviePage(@PathVariable Integer id, Model model, @RequestParam(name = "status", required = false) String status,
                                @RequestParam(name = "message", required = false) String message){
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("movie", this.movieRepository.findMovieById(id));
        model.addAttribute("reviewList", this.reviewRepository.findAllByMovie(this.movieRepository.findMovieById(id)));
        return "movie";
    }

    @GetMapping("/")
    public String showMovieTop(Model model) {
        model.addAttribute("topMoviesList",this.movieRepository.findTop3ByOrderByRatingDesc());
        return "index";
    }

}

