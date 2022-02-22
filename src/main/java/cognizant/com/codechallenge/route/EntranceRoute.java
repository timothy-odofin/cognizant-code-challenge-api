package cognizant.com.codechallenge.route;

import cognizant.com.codechallenge.dto.user.Login;
import cognizant.com.codechallenge.dto.user.SignUp;
import cognizant.com.codechallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/entrance")
public class EntranceRoute {
    @Autowired
    private UserService userService;
    @PostMapping("/signin")
    public ResponseEntity login(@Valid @RequestBody Login payload) {
        return userService.login(payload);
    }
    @PostMapping("/create-account")
    public ResponseEntity createAccount(@Valid @RequestBody SignUp payload) {
        return userService.signUp(payload);
    }


}
