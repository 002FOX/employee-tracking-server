package fox.server.controller;

import fox.server.config.RoleRequired;
import fox.server.model.Record;
import fox.server.model.Role;
import fox.server.service.ClockService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clock")
public class ClockController {
    @Autowired
    private ClockService clockService;

    @PostMapping("/{userId}/clockin")
    public ResponseEntity<?> clockIn(@PathVariable Long userId) {
        try {
            Record record = clockService.clockIn(userId);
            return new ResponseEntity<>(record, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{userId}/clockout")
    public ResponseEntity<?> clockOut(@PathVariable Long userId) {
        try {
            Record record = clockService.clockOut(userId);
            return new ResponseEntity<>(record, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}