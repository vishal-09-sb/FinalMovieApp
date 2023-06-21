package com.movie.book;

import com.moviebookingApp.controller.TicketController;
import com.moviebookingApp.model.Movie;
import com.moviebookingApp.model.Ticket;
import com.moviebookingApp.service.MovieService;
import com.moviebookingApp.service.SessionService;
import com.moviebookingApp.service.TicketService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private MovieService movieService;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private TicketController ticketController;

    @Test
    public void testAddTicketSuccess() throws Exception {
        // Arrange
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setSeatsAvailable(10);

        when(movieService.getMovieById(anyInt())).thenReturn(movie);
        when(ticketService.addTicket(any())).thenReturn(true);
        when(movieService.updateMovie(any())).thenReturn(true);
        when(sessionService.checkSessionUserType()).thenReturn("user");

        // Act
        ResponseEntity<?> response = ticketController.addTicket(1, 2, "testUser");

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddTicketFailureDueToInsufficientSeats() throws Exception {
        // Arrange
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setSeatsAvailable(1);

        when(movieService.getMovieById(anyInt())).thenReturn(movie);
        when(sessionService.checkSessionUserType()).thenReturn("user");

        ResponseEntity<?> response = ticketController.addTicket(1, 2, "testUser");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllTicketsSuccess() throws Exception {
        
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

        when(ticketService.getAllTickets()).thenReturn(tickets);
        when(sessionService.checkSessionUserType()).thenReturn("admin");

        ResponseEntity<?> response = ticketController.getAllTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllTicketsFailure() throws Exception {
        
        when(ticketService.getAllTickets()).thenReturn(Arrays.asList());
        when(sessionService.checkSessionUserType()).thenReturn("admin");

        ResponseEntity<?> response = ticketController.getAllTickets();

        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}