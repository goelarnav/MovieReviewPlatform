package dev.plum.moviereviewplatform.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private ObjectId id;
    private List<String> genres;
    private Integer runtime;
    private List<String> cast;
    private String poster;
    private String title;
    @Field("fullplot")
    private String fullPlot;
    private List<String> languages;
    private List<String> directors;
    private String rated;
    private Awards awards;
    private String lastupdated;
    private String year;
    private Imdb imdb;
    private List<String> countries;
    private String type;
    @Field("num_mflix_comments")
    private Integer numMflixComments;
    private List<String> writers;

//    public Double getImdbRating(){
//        return this.imdb.getRating();
//    }

}
