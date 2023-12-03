package fox.server.service;

import fox.server.model.Leave;
import fox.server.model.Record;
import fox.server.model.User;
import fox.server.payload.UserSummaryReport;
import fox.server.repository.LeaveRepository;
import fox.server.repository.RecordRepository;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;
    public UserSummaryReport generateUserSummaryReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Record> records = recordRepository.getRecordsByUser(user);
        List<Leave> leaves = leaveRepository.getLeavesByUser(user);

        Map<LocalDate, Duration> workingHoursPerDay = calculateWorkingHours(records);

        int totalLeavesTaken = leaves.size();

        UserSummaryReport userSummary = new UserSummaryReport();
        userSummary.setName(user.getFirstName() + user.getLastName());
        userSummary.setWorkingHoursPerDay(workingHoursPerDay);
        userSummary.setTotalLeavesTaken(totalLeavesTaken);

        return userSummary;
    }

    private Map<LocalDate, Duration> calculateWorkingHours(List<Record> records) {
        Map<LocalDate, Duration> workingHoursMap = new HashMap<>();

        for (Record record : records) {
            LocalDate date = record.getDate();
            LocalDateTime clockInTime = record.getClockInTime();
            LocalDateTime clockOutTime = record.getClockOutTime();

            Duration workingHours = Duration.between(clockInTime, clockOutTime);

            workingHoursMap.merge(date, workingHours, Duration::plus);

        }

        return workingHoursMap;
    }


}