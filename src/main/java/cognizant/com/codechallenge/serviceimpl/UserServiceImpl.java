package cognizant.com.codechallenge.serviceimpl;

import cognizant.com.codechallenge.dto.ApiResultSet;
import cognizant.com.codechallenge.dto.user.AuthTokenInfo;
import cognizant.com.codechallenge.dto.user.Login;
import cognizant.com.codechallenge.dto.user.SignUp;
import cognizant.com.codechallenge.dto.user.UserResponse;
import cognizant.com.codechallenge.mapper.Mapper;
import cognizant.com.codechallenge.model.auth.OauthClientDetails;
import cognizant.com.codechallenge.model.auth.RoleUser;
import cognizant.com.codechallenge.model.auth.Roles;
import cognizant.com.codechallenge.model.auth.Users;
import cognizant.com.codechallenge.repo.auth.OauthClientDetailsRepo;
import cognizant.com.codechallenge.repo.auth.RoleUserRepo;
import cognizant.com.codechallenge.repo.auth.UsersRepo;
import cognizant.com.codechallenge.service.UserService;
import cognizant.com.codechallenge.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

import static cognizant.com.codechallenge.utils.ApiMessages.*;
import static cognizant.com.codechallenge.utils.ApiResponseCode.*;
import static cognizant.com.codechallenge.utils.AppConstants.RESOURCE_ID;
import static cognizant.com.codechallenge.utils.Utils.getGson;
import static cognizant.com.codechallenge.utils.Utils.getHeadersWithClientCredentials;
import static cognizant.com.codechallenge.validation.AppValidation.validateSignUp;
import static cognizant.com.codechallenge.validation.AppValidation.validateLogin;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private OauthClientDetailsRepo oauthClientDetailsRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleUserRepo roleUserRepo;
    @Autowired
    private RestTemplate restTemplate;
    private static final int ONE_DAY = 60 * 60 * 24 * 24;
    @Value("${API.TOKEN_URL}")
    private String TOKEN_URL;
    @Value("${API.CLIENT_URL}")
    private String CLIENT_URL;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity signUp(SignUp signUpPayload) {
        try {
            String invalidRecord = validateSignUp(signUpPayload);
            if (invalidRecord != null && !invalidRecord.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, BAD_REQUEST,
                        invalidRecord));
            Optional<Users> usersOptional = usersRepo.findByUsername(signUpPayload.getUsername());
            if (usersOptional.isPresent())
                return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, TASK_DONE,
                        RECORD_EXISTS));
            Users user = Mapper.convertObject(signUpPayload, Users.class);
            user.setClientId(Utils.getClientId());
            user.setPassword(passwordEncoder.encode(signUpPayload.getPassword()));
            setUpAccount(usersRepo.save(user));
            return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, OKAY,
                    ACCOUNT_CREATED));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, INTERNAL_SERVER_ERROR, ERROR));
        }
    }

    private void setUpAccount(Users user) {
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(user.getClientId());
        oauthClientDetails.setClientSecret(user.getPassword());
        oauthClientDetails.setAccessTokenValidity(ONE_DAY);
        oauthClientDetails.setAuthorities("USER");
        oauthClientDetails.setResourceIds(RESOURCE_ID);
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user);
        roleUser.setRoleId(new Roles(1));
        roleUser.setDateCreated(new Date());
        oauthClientDetailsRepo.save(oauthClientDetails);
        roleUserRepo.save(roleUser);
    }

    @Override
    public ResponseEntity login(Login loginPayload) {
        try {
            String invalidRecord = validateLogin(loginPayload);
            if (invalidRecord != null && !invalidRecord.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, BAD_REQUEST,
                        invalidRecord));
            Optional<Users> usersOptional = usersRepo.findByUsername(loginPayload.getUsername());
            if (usersOptional.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, NOT_FOUND,
                        USER_NOT_FOUND));
            Users loginUser = usersOptional.get();
            if (!passwordEncoder.matches(loginPayload.getPassword(), loginUser.getPassword()))
                return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, NOT_FOUND,
                        INVALID_USER));
            UserResponse userResponse = Mapper.convertObject(loginUser, UserResponse.class);
            userResponse.setTokenInfo(obtainAccessToken(loginPayload));
            return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, OKAY,
                    userResponse));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, INTERNAL_SERVER_ERROR, ERROR));
        }
    }


    private AuthTokenInfo obtainAccessToken(Login login) {
        String url = CLIENT_URL + TOKEN_URL + "?grant_type=password&username=" + login.getUsername() + "&password=" + login.getPassword() + "";
        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials(login.getUsername(), login.getPassword()));

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        AuthTokenInfo token = getGson().fromJson(response.getBody(), AuthTokenInfo.class);
        return token;
    }


}
