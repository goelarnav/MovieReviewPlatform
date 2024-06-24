package dev.plum.moviereviewplatform.security.repositories;

import dev.plum.moviereviewplatform.security.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByLogin(String login);
}