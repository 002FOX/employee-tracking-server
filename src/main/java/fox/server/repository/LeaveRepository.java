package fox.server.repository;

import fox.server.model.Leave;
import fox.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    @Query("SELECT l FROM Leave l WHERE l.user.id = :userId AND l.startDate >= :startDate AND l.endDate <= :endDate")
    List<Leave> findLeavesForUserInDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Leave> getLeavesByUser(User user);
}