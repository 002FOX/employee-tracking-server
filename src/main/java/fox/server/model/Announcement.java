package fox.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcement")
public class Announcement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long announcementId;
        private String content;
        @ManyToOne
        @JoinColumn(name = "created_by")
        private User createdBy;
        private LocalDateTime createdAt;

    }
