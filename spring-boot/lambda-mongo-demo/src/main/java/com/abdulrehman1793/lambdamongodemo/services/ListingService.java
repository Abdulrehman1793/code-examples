package com.abdulrehman1793.lambdamongodemo.services;

import com.abdulrehman1793.lambdamongodemo.documents.Listing;
import com.abdulrehman1793.lambdamongodemo.repositories.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;

    public List<Listing> getAllListings() {
        return listingRepository.findAll(PageRequest.of(0, 50))
                .stream().toList();
    }
}
