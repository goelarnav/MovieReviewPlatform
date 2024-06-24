package dev.plum.moviereviewplatform.movie;

import dev.plum.moviereviewplatform.searching.MovieAtlasSearchServiceImpl;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

//    @GetMapping("/")
//    public ResponseEntity<List<Movie>> getAllMovies(){
//        LOGGER.info("Movies sent!");
//
//        return new ResponseEntity<>(movieService.getTenMovies(), HttpStatus.OK);
//
//        //return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
//    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getMoviesByWins(@PathVariable Integer imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.findMoviesByImdbId(imdbId), HttpStatus.OK);
    }

    @GetMapping("/initial")
    public ResponseEntity<List<MovieInitial>> getFirst10MoviesFromInitialCollection() {
        List<MovieInitial> movies = movieService.load10MoviesFromInitial();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/genre/{genres}")
    public ResponseEntity<List<Movie>> getMoviesByGenres(@PathVariable String genres){
        List<Movie> movies = movieService.findMoviesByGenre(genres);
        if(movies.isEmpty()) {
            LOGGER.info("No movies found for genre: {}", genres);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Movies found for genre: {}", genres);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
