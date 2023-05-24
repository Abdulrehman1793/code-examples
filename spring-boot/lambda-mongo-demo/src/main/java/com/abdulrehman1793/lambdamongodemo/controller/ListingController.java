package com.abdulrehman1793.lambdamongodemo.controller;


import com.abdulrehman1793.lambdamongodemo.documents.Listing;
import com.abdulrehman1793.lambdamongodemo.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/listing")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;

//    @GetMapping
    public List<Listing> findAll() {
        return listingService.getAllListings();
    }
}
