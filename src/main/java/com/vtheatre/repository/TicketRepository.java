package com.vtheatre.repository;

import java.util.Date;
import java.util.Optional;

import com.vtheatre.data.entity.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByConfirmationCode(String confirmationCode);

    @Query(value = "SELECT * from ticket t inner join showtime s on t.showtime_id = s.showtime_id where confirmation_code = ?1 and s.showtime = ?2 and chosen_date = ?3", nativeQuery = true)
    Optional<Ticket> findByConfirmationCodeAndShowtimeAndChosenDate(String confirmationCode, String showtime,
            Date chosenDate);

}
