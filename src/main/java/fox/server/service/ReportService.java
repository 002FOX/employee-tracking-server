package fox.server.service;

import fox.server.model.Leave;
import fox.server.model.Record;
import fox.server.model.User;
import fox.server.repository.LeaveRepository;
import fox.server.repository.RecordRepository;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Record> getRecordsInMonth(int year, int month, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        YearMonth targetMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = targetMonth.atDay(1);
        LocalDate endOfMonth = targetMonth.atEndOfMonth();

        return recordRepository.findRecordsForUserInDateRange(userId, startOfMonth, endOfMonth);
    }

    public List<Leave> getLeavesInMonth(int year, int month, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        return leaveRepository.findLeavesForUserInDateRange(userId, startOfMonth, endOfMonth);
    }

}