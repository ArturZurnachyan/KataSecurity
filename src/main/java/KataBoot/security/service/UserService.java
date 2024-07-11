package KataBoot.security.service;

import KataBoot.security.models.Role;
import KataBoot.security.models.User;
import KataBoot.security.repositories.RoleRepository;
import KataBoot.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(User user,List<Long> rolesIds) {
        User userUpdate = userRepository.findById(user.getId()).orElse(null);
        userUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userUpdate.setName(user.getName());
        userUpdate.setSurname(user.getSurname());
        userUpdate.setEmail(user.getEmail());
        List<Role> roles = roleRepository.findAllById(rolesIds);
        userUpdate.setRoleList(new HashSet<>(roles));
        userRepository.save(userUpdate);
    }

    public void saveUserWithRoles(User user, List<Long> roleIds) {
        User savedUser = userRepository.save(user);
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles = roleRepository.findAllById(roleIds);
        savedUser.setRoleList(new HashSet<>(roles));
        userRepository.save(savedUser);
    }


    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }
}
