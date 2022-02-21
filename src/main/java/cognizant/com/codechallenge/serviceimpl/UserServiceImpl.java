package cognizant.com.codechallenge.serviceimpl;

import cognizant.com.codechallenge.dto.ApiResultSet;
import cognizant.com.codechallenge.dto.Language;
import cognizant.com.codechallenge.dto.user.Login;
import cognizant.com.codechallenge.dto.user.SignUp;
import cognizant.com.codechallenge.mapper.Mapper;
import cognizant.com.codechallenge.model.Languages;
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
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static cognizant.com.codechallenge.utils.ApiMessages.*;
import static cognizant.com.codechallenge.utils.ApiResponseCode.*;
import static cognizant.com.codechallenge.utils.AppConstants.RESOURCE_ID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
   private UsersRepo usersRepo;
    @Autowired
    private OauthClientDetailsRepo oauthClientDetailsRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleUserRepo roleUserRepo;
    private static final int ONE_DAY = 60 * 60 * 24 * 24;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity signUp(SignUp signUpPayload) {
        try {
            String invalidRecord=validate(signUpPayload);
           if(invalidRecord !=null && !invalidRecord.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, BAD_REQUEST,
                    invalidRecord));
            Optional<Users> usersOptional =usersRepo.findByUsername(signUpPayload.getUsername());
           if(usersOptional.isPresent())
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

    private void setUpAccount(Users user){
        OauthClientDetails oauthClientDetails =new OauthClientDetails();
        oauthClientDetails.setClientId(user.getClientId());
        oauthClientDetails.setClientSecret(user.getPassword());
        oauthClientDetails.setAccessTokenValidity(ONE_DAY);
        oauthClientDetails.setAuthorities("USER");
        oauthClientDetails.setResourceIds(RESOURCE_ID);
        RoleUser roleUser= new RoleUser();
        roleUser.setUserId(user);
        roleUser.setRoleId(new Roles(1));
        roleUser.setDateCreated(new Date());
        oauthClientDetailsRepo.save(oauthClientDetails);
        roleUserRepo.save(roleUser);
    }
private String validate(SignUp payload){
        if(payload.getUsername()==null || payload.getUsername().trim().isEmpty())
            return String.format(INVALID_ENTRY,"Username");
        else if(payload.getName()==null || payload.getName().trim().isEmpty())
            return String.format(INVALID_ENTRY,"Name");
        else if(payload.getPassword()==null || payload.getPassword().trim().isEmpty())
            return String.format(INVALID_ENTRY,"Password");
        else
            return null;

}
    @Override
    public ResponseEntity login(Login loginPayload) {
        return null;
    }
}
