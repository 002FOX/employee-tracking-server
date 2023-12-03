package fox.server.service;

import fox.server.exceptions.InvalidLeaveStateException;
import fox.server.model.Leave;
import fox.server.model.LeaveStatus;
import fox.server.model.User;
import fox.server.repository.LeaveRepository;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Leave> getLeaves(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

         return leaveRepository.getLeavesByUser(user);
    }
    public Leave requestTimeOff(Long userId, LocalDate startDate, LocalDate endDate, String reason) {
        // Check if input makes sense/ available

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Leave leaveRequest = Leave.builder()
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .reason(reason)
                .status(LeaveStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return leaveRepository.save(leaveRequest);
    }

    public Leave approveLeaveRequest(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found"));

        if (leave.getStatus() == LeaveStatus.PENDING) {
            leave.setStatus(LeaveStatus.APPROVED);
            leave.setUpdatedAt(LocalDateTime.now());
            return leaveRepository.save(leave);
        } else {
            throw new InvalidLeaveStateException("Leave request cannot be approved.");
        }
    }

    public Leave rejectLeaveRequest(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found"));

        if (leave.getStatus() == LeaveStatus.PENDING) {
            leave.setStatus(LeaveStatus.REJECTED);
            leave.setUpdatedAt(LocalDateTime.now());
            return leaveRepository.save(leave);
        } else {
            throw new InvalidLeaveStateException("Leave request cannot be rejected.");
        }
    }
}