package com.user.external.services;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ratings-microservice")
public interface RatingService {

}
