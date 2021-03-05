package io.javabrains.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.MovieInfoServuce;
import io.javabrains.moviecatalogservice.services.RatingInfoService;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

  
    @Autowired
    private MovieInfoServuce movieInfoService;
    private RatingInfoService ratingInfoService;

    @Autowired
    WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")   
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = ratingInfoService.getUserRatings(userId);
        return userRating.getRatings().stream().map(
        		rating -> movieInfoService.getCatalogItem(rating)
        		).collect(Collectors.toList());

    }    
   
}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/