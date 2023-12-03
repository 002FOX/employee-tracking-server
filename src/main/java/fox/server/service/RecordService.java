package fox.server.service;

import fox.server.model.Record;
import fox.server.model.User;
import fox.server.repository.LeaveRepository;
import fox.server.repository.RecordRepository;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Record> getAllRecordsToday(Long userId, LocalDate date){
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return recordRepository.getRecordsForUserWithTodayClockIn(u.getId(), date);
    }

}