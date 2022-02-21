package cognizant.com.codechallenge.serviceimpl;

import cognizant.com.codechallenge.dto.user.Login;
import cognizant.com.codechallenge.dto.user.SignUp;
import cognizant.com.codechallenge.repo.auth.OauthClientDetailsRepo;
import cognizant.com.codechallenge.repo.auth.UsersRepo;
import cognizant.com.codechallenge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
   private UsersRepo usersRepo;
    @Autowired
    private OauthClientDetailsRepo oauthClientDetailsRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity signUp(SignUp signUpPayload) {
        return null;
    }

    @Override
    public ResponseEntity login(Login loginPayload) {
        return null;
    }
}
