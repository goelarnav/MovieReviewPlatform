package dev.plum.moviereviewplatform.searching;

import org.bson.Document;
import java.util.Collection;

public interface MovieAtlasSearchService {

    Collection<Document> moviesByKeywords(String keywords, int limit);

}