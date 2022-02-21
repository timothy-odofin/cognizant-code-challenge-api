package cognizant.com.codechallenge.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String name;
    private  AuthTokenInfo tokenInfo;
}
