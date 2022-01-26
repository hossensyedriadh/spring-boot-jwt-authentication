package io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.service;

import io.github.hossensyedriadh.springbootjwtauthentication.entity.UserData;
import io.github.hossensyedriadh.springbootjwtauthentication.repository.UserDataRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final ObjectFactory<UserDataRepository> userDataRepositoryObjectFactory;

    @Autowired
    public JwtUserDetailsService(ObjectFactory<UserDataRepository> userDataRepositoryObjectFactory) {
        this.userDataRepositoryObjectFactory = userDataRepositoryObjectFactory;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may be case-sensitive, or case-insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDataRepositoryObjectFactory.getObject().findById(username).isPresent()) {
            UserData user = userDataRepositoryObjectFactory.getObject().getById(username);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

            UserDetails userDetails = new UserDetails() {
                @Serial
                private static final long serialVersionUID = 5225330999567893840L;

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return Collections.singletonList(grantedAuthority);
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getUsername();
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
                    return user.isEnabled();
                }
            };

            return User.withUserDetails(userDetails).build();
        }

        throw new UsernameNotFoundException("Incorrect username: " + username);
    }
}
