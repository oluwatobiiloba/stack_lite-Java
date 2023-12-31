package com.stacklite.dev.stacklite_clone.Services;

import com.stacklite.dev.stacklite_clone.Model.Movie;
import com.stacklite.dev.stacklite_clone.Repositories.MovieRepositoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepositoryRepo movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovie(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }
}
