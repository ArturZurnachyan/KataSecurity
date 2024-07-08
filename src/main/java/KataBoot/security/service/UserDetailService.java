package KataBoot.security.service;


import KataBoot.security.models.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository Repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MyUser> user = Repository.findUserByEmail(email);

        if (user.isPresent()) {
            var userObj = user.get();

            System.out.println("User found: " + userObj.getEmail());
            System.out.println("Encoded password: " + userObj.getPassword());

            return User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(getRole(userObj).toUpperCase())
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }


    private String getRole(MyUser user) {
        if (user.getRole() == null) {
            return "USER";
        }
        return user.getRole();
    }
}
