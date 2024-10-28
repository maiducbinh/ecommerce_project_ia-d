package com.example.subproject.service;

import com.example.subproject.entity.Rating;
import com.example.subproject.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getRatingsByItemId(Long itemId) {
        return ratingRepository.findByItemId(itemId);
    }

    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Additional methods if needed
}
