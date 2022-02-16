package cognizant.com.codechallenge.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CompileUiPayload {
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username should not be empty")
    private String username;
    @NotNull(message = "Script is required")
    @NotBlank(message = "Script should not be empty")
    private String script;
    @NotNull(message = "Language is required")
    @NotBlank(message = "Language should not be empty")
    private String language;
    @NotNull(message = "Task Id is required")
    private Long taskId;
}
