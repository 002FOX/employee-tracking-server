package fox.server.payload;

import fox.server.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class AnnouncementResponse {


    private Long announcementId;
    private String content;
    private String firstName;
    private String lastName;
    private String positionTitle;
    private String createdAt;

    public AnnouncementResponse(Long announcementId, String content, String firstName, String lastName, String positionTitle, String createdAt) {
        this.announcementId = announcementId;
        this.content = content;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionTitle = positionTitle;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

}
