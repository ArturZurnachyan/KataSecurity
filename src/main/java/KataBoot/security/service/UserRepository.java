package KataBoot.security.service;

import KataBoot.security.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findUserByEmail(String Email);

}
