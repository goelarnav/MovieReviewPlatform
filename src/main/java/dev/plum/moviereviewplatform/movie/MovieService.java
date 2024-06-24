package dev.plum.moviereviewplatform.movie;

import dev.plum.moviereviewplatform.exception.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public ObjectId findObjectIdByImdbId(Integer imdbId){
        Optional<Movie> requested = movieRepository.findByImdbUniqueNum(imdbId);
        return requested.map(Movie::getId).orElseThrow(() -> new NoSuchElementException("Movie with IMDb ID " + imdbId + " not found"));
    }

    public Optional<Movie> findMoviesByImdbId(Integer imdbId){
        return movieRepository.findByImdbUniqueNum(imdbId);
    }

    public Movie updateCommentCount(ObjectId movieId){
        Movie existingMovie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        existingMovie.setNumMflixComments(existingMovie.getNumMflixComments()+1);
        return movieRepository.save(existingMovie);
    }

    public Boolean movieExist(ObjectId movieId){
        Optional<Movie> foundMovie = movieRepository.findById(movieId);
        if(foundMovie.isEmpty()){
            return false;
        } else{
            return true;
        }
    }

//    public List<Movie> getTenMovies(){
//        return movieRepository.findFirst10By();
//    }

//    public List<Movie> getTenMoviesFromInitialCollection() {
//        return movieRepository.findFirst10ByOrderByTitleAscAndCollectionName("movies-initial");
//    }

    public List<MovieInitial> load10MoviesFromInitial(){
        return movieRepository.findFirst10By("initial-movies");
    }

    public Movie findMovieById(ObjectId movieId){
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("Movie not found by Object Id"));
    }

    public List<Movie> findMoviesByGenre(String genre) {
        return movieRepository.findByGenresContaining(genre);
    }
}
