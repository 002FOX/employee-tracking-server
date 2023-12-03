package fox.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;
    private String title;
    private String description;
    private double salary;
    private LocalTime startingTime;
    private LocalTime endingTime;
    @OneToMany(mappedBy = "position")
    private List<User> users;

    public Position(Long positionId, String title, String description, double salary, LocalTime startingTime, LocalTime endingTime) {
        this.positionId = positionId;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }
}