package KataBoot.security.service;

import KataBoot.security.models.MyUser;

import java.util.List;

public interface UserService {
    List<MyUser> getAllUsers();
    void saveUser(MyUser user);
    void deleteUser(int id);
    void updateUser(MyUser user, int id);
    MyUser getUser(int id);
}
