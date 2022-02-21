package cognizant.com.codechallenge.service;

import cognizant.com.codechallenge.dto.user.Login;
import cognizant.com.codechallenge.dto.user.SignUp;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity signUp(SignUp signUpPayload);
    ResponseEntity login(Login loginPayload);


}
