package com.moviecollection.repositories;

import com.moviecollection.models.Movie;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class MovieRepository {
    DatabaseRepository databaseRepository;



    public  void create(Movie movie) throws RuntimeException {
        String query = "INSERT INTO movie (id, title, year, genre, description, runtime, trailerUrl, posterUrl, rating, actor) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = this.databaseRepository.getConnection().prepareStatement(query);
            statement.setInt(1, movie.getId());
            statement.setString(2, movie.getTitle());
            statement.setInt(3, movie.getYear());
            statement.setString(4, movie.getGenre());
            statement.setString(5, movie.getDescription());
            statement.setInt(6, movie.getRuntime());
            statement.setString(7, movie.getTrailerUrl().toString());
            statement.setString(8, movie.getPosterUrl().toString());
            statement.setDouble(9, movie.getRating());
            statement.setString(10,movie.getActors());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public  void delete(Integer movieId) {
        String query = "DELETE FROM movie  WHERE id=?";
        try {
            PreparedStatement statement = this.databaseRepository.getConnection().prepareStatement(query);
            statement.setInt(1, Integer. parseInt(movieId.toString()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Movie movie) throws RuntimeException {
        try {
            String query;
            PreparedStatement statement ;
            if (movie.getId() != null) {
                query = "UPDATE movie SET title, year, genre, description = ? WHERE id= ?";
                statement = this.databaseRepository.getConnection().prepareStatement(query);
                statement.setString(1, movie.getTitle());
                statement.setInt(2, movie.getYear());
                statement.setString(3, movie.getGenre());
                statement.setString(4, movie.getDescription());
                statement.setInt(8, Integer.parseInt(movie.getId().toString()));
            }else{
                query = "UPDATE movie SET runtime, trailerUrl, PosterUrl, rating, actors = ? WHERE id= ?";
                statement = this.databaseRepository.getConnection().prepareStatement(query);
                statement.setInt(5, movie.getRuntime());
                statement.setString(6, movie.getTrailerUrl());
                statement.setString(7, movie.getPosterUrl());
                statement.setDouble(8, movie.getRating());
                statement.setString(9, movie.getActors());
                statement.setInt(8, Integer.parseInt(movie.getId().toString()));

            }
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public ArrayList<Movie> allMovie(){
        ArrayList<Movie> movie = new ArrayList<>();
        String query = "SELECT * FROM movie";
        try{
            PreparedStatement statement = this.databaseRepository.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                movie.add(new Movie(
                        Integer.valueOf(result.getInt("id")
                ),
                        result.getString("title"),
                        Integer.valueOf(result.getInt("year")),
                        result.getString("genre"),
                        result.getString("description"),
                        Integer.valueOf(result.getInt("runtime")),
                        result.getString("trailerUrl"),
                        result.getString("posterUrl"),
                        Double.valueOf(result.getDouble("rating")),
                        result.getString("actors")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return movie;
    }


}
