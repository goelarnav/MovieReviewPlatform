package dev.plum.moviereviewplatform.watchlist;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public Optional<Watchlist> getWatchlist(ObjectId userId){
        return watchlistRepository.findByUserid(userId);
    }

    public Watchlist saveNewWatchList(ObjectId userId){
        List<ObjectId> emptyList = new ArrayList<>();
        Watchlist newWatchlist = new Watchlist(emptyList, userId);
        return watchlistRepository.save(newWatchlist);
    }

    public ResponseEntity<Void> saveMovieToWatchlist(ObjectId movieId, ObjectId userId, UriComponentsBuilder ucb){
        Optional<Watchlist> watchlist = getWatchlist(userId);
        Watchlist savedWatchlist;
        if(watchlist.isPresent()){
            Watchlist updatedWatchlist = watchlist.get();
            updatedWatchlist.getMovieList().add(movieId);
            savedWatchlist = watchlistRepository.save(updatedWatchlist);
        } else{
            Watchlist newWatchlist = saveNewWatchList(userId);
            newWatchlist.getMovieList().add(movieId);
            savedWatchlist = watchlistRepository.save(newWatchlist);
        }
//uri is to set the location of the newly saved watchlist
        URI locationOfNewWatchlist = ucb.path("/api/v1/watchlist/{watchlistId}").buildAndExpand(savedWatchlist.getId()).toUri();

        return ResponseEntity.created(locationOfNewWatchlist).build();
    }
}
