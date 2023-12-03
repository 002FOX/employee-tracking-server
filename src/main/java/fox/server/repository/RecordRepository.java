package fox.server.repository;

import fox.server.model.Record;
import fox.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT r FROM Record r WHERE r.user.id = :userId ORDER BY r.date DESC, r.clockInTime DESC")
    Record findLatestRecordByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Record r WHERE r.user.id = :userId AND r.date >= :startDate AND r.date <= :endDate")
    List<Record> findRecordsForUserInDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Record> getRecordsByUser(User user);

    // New method to get all records for a user where the clock-in time is today
    @Query("SELECT r FROM Record r WHERE r.user.id = :userId AND r.date = :todayDate")
    List<Record> getRecordsForUserWithTodayClockIn(
            @Param("userId") Long userId,
            @Param("todayDate") LocalDate todayDate
    );

}