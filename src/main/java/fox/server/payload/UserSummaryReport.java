package fox.server.payload;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

public class UserSummaryReport {
    private String name;
    private Map<LocalDate, Duration> workingHoursPerDay;
    private Integer totalLeavesTaken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<LocalDate, Duration> getWorkingHoursPerDay() {
        return workingHoursPerDay;
    }

    public void setWorkingHoursPerDay(Map<LocalDate, Duration> workingHoursperDay) {
        this.workingHoursPerDay = workingHoursperDay;
    }

    public Integer getTotalLeavesTaken() {
        return totalLeavesTaken;
    }

    public void setTotalLeavesTaken(Integer totalLeavesTaken) {
        this.totalLeavesTaken = totalLeavesTaken;
    }
}
