package fox.server.controller;
import fox.server.config.RoleRequired;
import fox.server.model.Leave;
import fox.server.model.LeaveRequest;
import fox.server.model.Record;
import fox.server.model.Role;
import fox.server.payload.LeaveResponse;
import fox.server.payload.RecordResponse;
import fox.server.repository.LeaveRepository;
import fox.server.repository.RecordRepository;
import fox.server.service.ClockService;
import fox.server.service.LeaveService;
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
@RequestMapping("/leaves")
public class LeavesController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllLeaves(@PathVariable Long userId) {
        try {
            List<Leave> leaves = leaveService.getLeaves(userId);
            List<LeaveResponse> leaveResponses = new ArrayList<>();
            for(Leave l : leaves){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                leaveResponses.add(new LeaveResponse(l.getLeaveId(), l.getStartDate().format(timeFormatter), l.getEndDate().format(timeFormatter), l.getReason(), l.getStatus().toString(), l.getCreatedAt().format(formatter), l.getUpdatedAt().format(formatter)));
            }
            return new ResponseEntity<>(leaveResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> PostLeave(@PathVariable Long userId, @RequestBody LeaveRequest leaveRequest) {
        try {
            Leave leave = leaveService.requestTimeOff(userId, leaveRequest.getStartDate(), leaveRequest.getEndDate(), leaveRequest.getReason());
            LeaveResponse l = new LeaveResponse(leave.getLeaveId(), leave.getStartDate().toString(), leave.getEndDate().toString(), leave.getReason(), leave.getStatus().toString(), leave.getCreatedAt().toString(), leave.getUpdatedAt().toString());
            return new ResponseEntity<>(l, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}