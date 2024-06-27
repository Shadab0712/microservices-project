package com.ratings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ratings.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByUserId(String userId);

	List<Rating> findByHotelId(String hotelId);
}
