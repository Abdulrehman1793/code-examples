package com.abdulrehman1793.lambdamongodemo.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "listingsAndReviews")
@Data
public class Listing {
    @Id
    private String id;
    private String name;
    private String summary;
}
