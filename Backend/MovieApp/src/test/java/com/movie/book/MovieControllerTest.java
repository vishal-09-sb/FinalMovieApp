package com.movie.book;

import com.moviebookingApp.controller.MovieController;
import com.moviebookingApp.exceptions.DuplicateMovieIdExceptions;
import com.moviebookingApp.exceptions.DuplicateMovieNameException;
import com.moviebookingApp.kafka.MovieKafkaProducer;
import com.moviebookingApp.model.Movie;
import com.moviebookingApp.service.MovieService;
import com.moviebookingApp.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @Mock
    private MovieKafkaProducer movieKafkaProducer;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMovieSuccess() throws Exception {
        Movie movie = new Movie();
        movie.setMovieName("TestMovie");

        when(sessionService.checkSessionUserType()).thenReturn("admin");
        when(movieService.addMovie(any(Movie.class))).thenReturn(movie);

        ResponseEntity<?> responseEntity = movieController.addMovie(movie);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(movie, responseEntity.getBody());
       verify(movieKafkaProducer, times(1)).sendMovieMessage(movie.getMovieName());
    }

    @Test
    void testAddMovieFailure() throws Exception {
        Movie movie = new Movie();
        movie.setMovieName("TestMovie");

        when(sessionService.checkSessionUserType()).thenReturn("admin");
        when(movieService.addMovie(any(Movie.class))).thenReturn(null);

        ResponseEntity<?> responseEntity = movieController.addMovie(movie);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals("No Movie", responseEntity.getBody());
        //verify(movieKafkaProducer, never()).sendMovieMessage(any());
    }

    @Test
    void testAddMovieException() {
        Movie movie = new Movie();
        movie.setMovieName("TestMovie");

        assertThrows(Exception.class, () -> {
            movieController.addMovie(movie);
        });
    }
}
