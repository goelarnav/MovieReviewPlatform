package dev.plum.moviereviewplatform.movie;

import java.util.List;

public interface CustomMovieRepository {
    List<MovieInitial> findFirst10By(String collectionName);
}
