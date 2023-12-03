package fox.server.service;

import fox.server.model.Record;
import fox.server.model.User;
import fox.server.repository.RecordRepository;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ClockService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;

    public Record clockIn(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        LocalDateTime currentTime = LocalDateTime.now();


        Record record = new Record();
        record.setUser(user);
        record.setClockInTime(currentTime);
        record.setClockOutTime(currentTime);
        record.setDate(currentTime.toLocalDate());
        recordRepository.save(record);
        return record;
    }

    public Record clockOut(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        LocalDateTime currentTime = LocalDateTime.now();
        Record record = recordRepository.findLatestRecordByUserId(userId);

        if (record == null || !record.getClockOutTime().equals(record.getClockInTime())) {
            throw new IllegalStateException("Cannot clock out. No clock-in found or already clocked out.");
        }

        record.setClockOutTime(currentTime);

        return recordRepository.save(record);
    }
}