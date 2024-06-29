package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.model.User;
import com.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User saveUser = userService.saveUser(user);
		return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
	}

	// fallback method for circuit breaker
	public ResponseEntity<User> ratingsHotelFallback(String id, Exception ex) {
		logger.info("Fallback method is executed because connecting service is down : ", ex.getMessage());
		User user = new User();
		user.setUserId("141234");
		user.setName("Dummy");
		user.setEmail("dummy@gmail.com");
		user.setAbout("This user is created dummy because some service is down");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	int retryCount = 1;

	@GetMapping("/{id}")
//	@CircuitBreaker(name = "ratingsHotelBreaker", fallbackMethod = "ratingsHotelFallback")
	@Retry(name = "ratingsHotelService", fallbackMethod = "ratingsHotelFallback")
//	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingsHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String id) {
		logger.info("Get single user handler method : User Controller ");
		logger.info("Retry Count : {} ", retryCount);
		retryCount++;
		User userById = userService.getUser(id);
		return new ResponseEntity<>(userById, HttpStatus.OK);
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getAll() {
		List<User> allUsers = userService.getAllUser();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
}