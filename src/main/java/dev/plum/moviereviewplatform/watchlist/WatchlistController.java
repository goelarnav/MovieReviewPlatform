package dev.plum.moviereviewplatform.watchlist;

import dev.plum.moviereviewplatform.movie.Movie;
import dev.plum.moviereviewplatform.movie.MovieService;
import dev.plum.moviereviewplatform.security.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {

    @Autowired
    private UserService userService;
    @Autowired
    private WatchlistService watchlistService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/{login}")
    public ResponseEntity<List<Movie>> findWatchlist(@PathVariable String login){
        ObjectId userId = new ObjectId(userService.findUserId(login));
        Optional<Watchlist> foundWatchlist = watchlistService.getWatchlist(userId);
        if(foundWatchlist.isPresent()){
            List<Movie> watchlistMovies = new ArrayList<>();
            for(ObjectId movieId : foundWatchlist.get().getMovieList()){
                watchlistMovies.add(movieService.findMovieById(movieId));
            }
            return new ResponseEntity<List<Movie>>(watchlistMovies, HttpStatus.OK);
        } else{
            Watchlist savedWatchlist = watchlistService.saveNewWatchList(userId);
            List<Movie> emptyList = new ArrayList<Movie>();
            return new ResponseEntity<List<Movie>>(emptyList, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> addMovieToWatchList(@RequestBody AddMovieToWatchlistRequest addMovieToWatchlistRequest, UriComponentsBuilder ucb){
        Integer imdbId = addMovieToWatchlistRequest.getImdbid();
        ObjectId movieId = movieService.findObjectIdByImdbId(imdbId);
        ObjectId userId = new ObjectId(userService.findUserId(addMovieToWatchlistRequest.getLogin()));

        if(!movieService.movieExist(movieId)){
            return ResponseEntity.badRequest().body("Movie not found with imdb id: " + imdbId);
        }

        return watchlistService.saveMovieToWatchlist(movieId, userId, ucb);
    }

}
