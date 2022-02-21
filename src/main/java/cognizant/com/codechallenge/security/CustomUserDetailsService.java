package cognizant.com.codechallenge.security;

import cognizant.com.codechallenge.model.auth.Users;
import cognizant.com.codechallenge.repo.auth.RoleUserRepo;
import cognizant.com.codechallenge.repo.auth.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
   private RoleUserRepo roleuserRepo;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Users> user = usersRepo.findByUsername(userName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        Users rs = user.get();
        return new CustomUserDetails(rs, roleuserRepo.findByAccount(rs.getId()));
    }

}
