package fox.server.controller;

import fox.server.config.RoleRequired;
import fox.server.model.Record;
import fox.server.model.Role;
import fox.server.payload.RecordResponse;
import fox.server.repository.RecordRepository;
import fox.server.service.ClockService;
import fox.server.service.RecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> GetRecords(@PathVariable Long userId) {
        try {
            List<Record> records = recordService.getAllRecordsToday(userId, LocalDate.now());
            List<RecordResponse> recordResponses = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for(Record r : records){
                recordResponses.add(new RecordResponse(r.getRecordId(), r.getClockInTime().format(formatter), r.getClockOutTime().format(formatter)));
            }
            return new ResponseEntity<>(recordResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}