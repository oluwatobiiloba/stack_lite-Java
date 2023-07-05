package com.stacklite.dev.stacklite_clone.Repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stacklite.dev.stacklite_clone.Model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

}
