package com.abdulrehman1793.lambdamongodemo.repositories;

import com.abdulrehman1793.lambdamongodemo.documents.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String> {
}

