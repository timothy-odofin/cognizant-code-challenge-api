package cognizant.com.codechallenge.dto;

import lombok.Data;

@Data
public class TaskReportResponse {
    private String username;
    private Long successCount;
    private String tasks;
}
