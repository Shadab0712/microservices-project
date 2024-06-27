package com.ratings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ratings.model.Rating;
import com.ratings.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository repository;

	public Rating create(Rating rating) {
		return repository.save(rating);
	}

	public List<Rating> getRatings() {
		return repository.findAll();
	}

	public List<Rating> getRatingByUserId(String userId) {
		return repository.findByUserId(userId);
	}

	public List<Rating> getRatingByHotelId(String hotelId) {
		return repository.findByHotelId(hotelId);
	}

}
