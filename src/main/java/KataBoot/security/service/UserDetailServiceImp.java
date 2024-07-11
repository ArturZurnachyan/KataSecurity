package KataBoot.security.service;


import KataBoot.security.models.User;
import KataBoot.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailServiceImp implements UserDetailsService {


    private final UserRepository Repository;

    @Autowired
    public UserDetailServiceImp(UserRepository Repository) {
        this.Repository = Repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Repository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(email);
        }


    }
}
