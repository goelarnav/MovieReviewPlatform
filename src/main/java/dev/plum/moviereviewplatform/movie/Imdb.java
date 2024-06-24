package dev.plum.moviereviewplatform.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imdb {
    private Double rating;
    private Integer votes;
    @Field("id")
    private Integer uniqueNum;
}
