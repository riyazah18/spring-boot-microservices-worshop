package io.javabrains.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;

@Service
public class MovieInfoServuce {
	
    @Autowired
    private RestTemplate restTemplate;
	
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
    
    /**
	 * fallback method execute in case of service down or failure.
	 * @param userId
	 * @return
	 */
    public CatalogItem getFallbackCatalogItem(Rating rating) {
		
		return new CatalogItem("No Movie", "", rating.getRating());
	}

}
