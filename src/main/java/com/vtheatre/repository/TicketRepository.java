package com.vtheatre.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.MyTicketsResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT * from ticket t inner join showtime s on t.showtime_id = s.showtime_id where username = ?1 and s.showtime = ?2 and chosen_date = ?3", nativeQuery = true)
    Optional<Ticket> findByUsernameAndShowtimeAndChosenDate(String username, String showtime, Date chosenDate);

    @Query(value = "SELECT new com.vtheatre.data.model.MyTicketsResponse(m.title, s.showtime, t.chosenDate) from Ticket t inner join Showtime s on t.showtimeId = s.showtimeId inner join Movie m on t.movieId = m.movieId where t.username = ?1 order by t.chosenDate, str_to_date(s.showtime,'%l:%i %p')")
    List<MyTicketsResponse> findByUsername(String username);

}
