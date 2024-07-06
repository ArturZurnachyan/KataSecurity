package KataBoot.security.dao;

import KataBoot.security.models.MyUser;

import java.util.List;

public interface UserDao {
    List<MyUser> getUsers();
    void saveUser(MyUser user);
    void deleteUser(int id);
    void updateUser(MyUser user, int id);
    MyUser getUser(int id);
}
