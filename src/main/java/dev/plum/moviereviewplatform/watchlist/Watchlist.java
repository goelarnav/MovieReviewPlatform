package dev.plum.moviereviewplatform.watchlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "watchlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist {
    private ObjectId id;
    private List<ObjectId> movieList;
    private ObjectId userid;

    public Watchlist(List<ObjectId> movieList, ObjectId userid){
        this.movieList = movieList;
        this.userid = userid;
    }
}
