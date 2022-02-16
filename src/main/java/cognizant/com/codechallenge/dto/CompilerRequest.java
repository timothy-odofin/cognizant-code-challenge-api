package cognizant.com.codechallenge.dto;

import lombok.Data;

@Data
public class CompilerRequest {
    private String clientId;
    private String clientSecret;
    private String script;
    private String stdin;
    private String language;
    private int versionIndex;

}
