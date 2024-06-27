package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.model.Hotel;
import com.hotel.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@PostMapping("/save")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
		Hotel savedHotel = hotelService.create(hotel);
		return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
	}

	// get single
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId) {
		Hotel getHotelById = hotelService.get(hotelId);
		return new ResponseEntity<>(getHotelById, HttpStatus.OK);
	}

	// get all
	@GetMapping("/getAllHotels")
	public ResponseEntity<List<Hotel>> getAll() {
		List<Hotel> allHotels = hotelService.getAll();
		return new ResponseEntity<>(allHotels, HttpStatus.OK);
	}

}
