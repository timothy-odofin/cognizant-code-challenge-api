package cognizant.com.codechallenge.security;

import cognizant.com.codechallenge.model.auth.RoleUser;
import cognizant.com.codechallenge.model.auth.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String password;
    private final String username;

    public CustomUserDetails(Users user, List<RoleUser> list) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = translate(list);
    }

    private Collection<? extends GrantedAuthority> translate(List<RoleUser> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleUser role : roles) {
            String name = role.getRoleId().getName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        }
        return grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
