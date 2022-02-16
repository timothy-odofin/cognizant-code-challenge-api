package cognizant.com.codechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionOutcome {
    private String expectedOutput;
    private String  userOutput;
    private String remark;
}
