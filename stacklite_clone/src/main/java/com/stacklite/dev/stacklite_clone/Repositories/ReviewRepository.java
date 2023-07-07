package com.stacklite.dev.stacklite_clone.Repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.stacklite.dev.stacklite_clone.Model.Review;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

}
