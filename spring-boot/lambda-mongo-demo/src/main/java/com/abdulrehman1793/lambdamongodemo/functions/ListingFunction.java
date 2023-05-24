package com.abdulrehman1793.lambdamongodemo.functions;

import com.abdulrehman1793.lambdamongodemo.documents.Listing;
import com.abdulrehman1793.lambdamongodemo.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ListingFunction {
    private final ListingService listingService;

    @Bean
    public Supplier<List<Listing>> findAll() {
        return listingService::getAllListings;
    }
}
