package dev.plum.moviereviewplatform.watchlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieToWatchlistRequest {
    private String login;
    private Integer imdbid;
}
