package dev.plum.moviereviewplatform.watchlist;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, ObjectId> {
    public Optional<Watchlist> findByUserid(ObjectId userId);
}
