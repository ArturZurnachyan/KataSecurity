package KataBoot.security.service;

import KataBoot.security.dao.UserDao;
import KataBoot.security.models.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void saveUser(MyUser user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(MyUser user, int id) {
        userDao.updateUser(user,id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MyUser getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyUser> getAllUsers() {
        return userDao.getUsers();
    }
}
