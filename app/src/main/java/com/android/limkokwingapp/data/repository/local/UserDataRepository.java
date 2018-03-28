package com.android.limkokwingapp.data.repository.local;

import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.ui.login.model.LoginModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by gautam on 25/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface UserDataRepository {

    Maybe<User> loginUser(LoginModel loginModel);

    Maybe<User> checkIfUserExists(String mEmail);

    Flowable<List<User>> findAll();

    void saveUser(User mUser);

    void updateUser(User mUser);

    void deleteUser(User user);

}
