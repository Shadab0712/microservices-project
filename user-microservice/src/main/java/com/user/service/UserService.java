package com.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.exceptions.ResourceNotFoundException;
import com.user.external.services.HotelService;
import com.user.model.Hotel;
import com.user.model.Rating;
import com.user.model.User;
import com.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public User saveUser(User user) {
		String randomuserId = UUID.randomUUID().toString();
		user.setUserId(randomuserId);
		return userRepository.save(user);
	}

	public User getUserById(String userId) {
		// get user from database with the help of user repository
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
		// fetch rating of the above user from RATING SERVICE
		// http://localhost:8083/ratings/users/47e38dac-c7d0-4c40-8582-11d15f185fad

		Rating[] ratingsOfUser = restTemplate.getForObject("http://ratings-microservice/ratings/users/" + user.getUserId(),
				Rating[].class);
		logger.info("{} ", ratingsOfUser);
		List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());
		List<Rating> ratingList = ratings.stream().map(rating -> {
			// api call to hotel service to get the hotel

			// http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
			// ResponseEntity<Hotel> forEntity =
			// restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),
			// Hotel.class);

			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			// logger.info("response status code: {} ",forEntity.getStatusCode());
			// set the hotel to rating
			rating.setHotel(hotel);
			// return the rating

			return rating;
		}).collect(Collectors.toList());

		user.setRatings(ratingList);

		return user;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public boolean deleteUser(String userId) {
		userRepository.deleteById(userId);
		return true;
	}

}
