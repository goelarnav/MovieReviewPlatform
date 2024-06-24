package dev.plum.moviereviewplatform.comment;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Optional<List<Comment>> getCommentsByMovie(ObjectId movieId){
        return commentRepository.findByMovieid(movieId);
    }

    public ResponseEntity<Void> addComment(Comment newComment, UriComponentsBuilder ucb){
        Comment addedComment = commentRepository.save(newComment);
        URI locationOfNewComment = ucb.path("api/v1/movies/comments/{id}").buildAndExpand(addedComment.getObjectId()).toUri();
        return ResponseEntity.created(locationOfNewComment).build();
    }
}