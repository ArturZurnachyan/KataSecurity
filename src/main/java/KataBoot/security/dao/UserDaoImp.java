package KataBoot.security.dao;

import KataBoot.security.models.MyUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<MyUser> getUsers() {
        return entityManager.createQuery("from MyUser", MyUser.class).getResultList();
    }

    @Override
    public void saveUser(MyUser user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(entityManager.find(MyUser.class, id));
    }


    @Override
    public void updateUser(MyUser user, int id) {
        MyUser newUser = entityManager.find(MyUser.class, id);
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setEmail(user.getEmail());
        entityManager.merge(newUser);
    }
    @Override
    public MyUser getUser(int id) {
        return entityManager.find(MyUser.class, id);
    }
}
