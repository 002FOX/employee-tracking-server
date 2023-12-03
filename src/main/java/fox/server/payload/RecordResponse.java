package fox.server.payload;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fox.server.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {

    private Long recordId;
    private String clockInTime;
    private String clockOutTime;

}
