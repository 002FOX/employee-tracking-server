package fox.server.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {
    private Long leaveId;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
    private String createdAt;
    private String updatedAt;
}
