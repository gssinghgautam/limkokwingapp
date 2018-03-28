package com.android.limkokwingapp.data.repository.local;

import com.android.limkokwingapp.data.dao.UserDao;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.ui.login.model.LoginModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by gautam on 25/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class UserDataSource implements UserDataRepository {

    private final UserDao userDao;

    @Inject
    public UserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Maybe<User> loginUser(LoginModel loginModel) {
        return userDao.loggedInUser(loginModel.getEmail(), loginModel.getPassword());
    }

    @Override
    public Maybe<User> checkIfUserExists(String mEmail) {
        return userDao.checkIfUserExist(mEmail);
    }

    @Override
    public Flowable<List<User>> findAll() {
        return userDao.getAllUser();
    }

    @Override
    public void saveUser(User mUser) {
        userDao.saveUser(mUser);
    }

    @Override
    public void updateUser(User mUser) {
        userDao.updateUser(mUser);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
}
