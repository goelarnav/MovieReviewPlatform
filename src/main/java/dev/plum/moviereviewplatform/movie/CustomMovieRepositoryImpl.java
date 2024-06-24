package dev.plum.moviereviewplatform.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomMovieRepositoryImpl implements CustomMovieRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<MovieInitial> findFirst10By(String collectionName) {
        return mongoTemplate.find(new Query().limit(10), MovieInitial.class, "movies-initial");
    }
}
