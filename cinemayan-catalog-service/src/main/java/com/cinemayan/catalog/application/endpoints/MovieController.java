package com.cinemayan.catalog.application.endpoints;

import com.cinemayan.catalog.application.config.MovieApiURLs;
import com.cinemayan.catalog.domain.movie.entity.Movie;
import com.cinemayan.core.domain.pagination.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag (name = "Movies controller")
@RestController
class MovieController {

    @Operation (summary = "Get all movies")
    @GetMapping (MovieApiURLs.GET_MOVIES_URL)
    @ResponseStatus (HttpStatus.OK)
    public PaginatedResponse<Movie> getAllMovies (
        @RequestParam (name = "search", required = false) String searchQuery,
        @RequestParam (name = "password", required = false) String password) {
        return null;
    }
}
