package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

}
