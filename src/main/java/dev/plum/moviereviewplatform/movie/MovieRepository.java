package dev.plum.moviereviewplatform.movie;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId>, CustomMovieRepository {
    //Optional<Movie> findByImdb_Id(Integer imdbId);

    Optional<Movie> findByImdbUniqueNum(Integer uniqueNum);

    //List<Movie> findFirst10By();

    List<Movie> findByGenresContaining(String genre);
}
