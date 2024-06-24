package dev.plum.moviereviewplatform.comment;

import dev.plum.moviereviewplatform.movie.Movie;
import dev.plum.moviereviewplatform.movie.MovieService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<List<Comment>>> getCommentsByMovie(@PathVariable Integer imdbId){
        ObjectId movieId = movieService.findObjectIdByImdbId(imdbId);
        return new ResponseEntity<Optional<List<Comment>>>(commentService.getCommentsByMovie(movieId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody @Validated Comment newComment, BindingResult bindingResult, UriComponentsBuilder ucb){

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Validation Errors: " + bindingResult.getAllErrors());
        }

        if(newComment.getMovieid() == null){
            return ResponseEntity.badRequest().body("movieid must not be null. email is " + newComment.getEmail());
        }

        Movie updatedMovie = movieService.updateCommentCount(newComment.getMovieid());
        LOGGER.info("UPDATED MOVIE HAS COMMENTS: " + updatedMovie.getNumMflixComments());

        return commentService.addComment(newComment,ucb);
    }
}