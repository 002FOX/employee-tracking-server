package fox.server.controller;

import fox.server.model.Announcement;
import fox.server.model.User;
import fox.server.payload.AnnouncementRequest;
import fox.server.payload.AnnouncementResponse;
import fox.server.repository.AnnouncementRepository;
import fox.server.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAnnouncements() {
        try {
            List<Announcement> announcements = announcementRepository.findAll();
            List<AnnouncementResponse> announcementResponses = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for(Announcement a : announcements){
                announcementResponses.add(new AnnouncementResponse(a.getAnnouncementId(), a.getContent(), a.getCreatedBy().getFirstName(), a.getCreatedBy().getLastName(), a.getCreatedBy().getPosition().getTitle(), a.getCreatedAt().format(formatter)));
            }
            return new ResponseEntity<>(announcementResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementRequest request) {
        try {

            User createdBy = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            Announcement announcement = Announcement.builder()
                    .content(request.getContent())
                    .createdBy(createdBy)
                    .createdAt(LocalDateTime.now())
                    .build();

            announcementRepository.save(announcement);

            return new ResponseEntity<>("Announcement created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}