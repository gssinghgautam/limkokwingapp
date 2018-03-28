package com.android.limkokwingapp.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.limkokwingapp.data.entity.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by gautam on 25/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE email =:emailId")
    Maybe<User> checkIfUserExist(String emailId);

    @Query("SELECT * FROM user")
    Flowable<List<User>> getAllUser();

    @Query("SELECT * FROM user")
    Single<User> getLoggedInUser();

    @Query("SELECT * FROM user WHERE email =:mEmailId AND password =:mPassword")
    Maybe<User> loggedInUser(String mEmailId, String mPassword);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUser(User mUserData);

    @Update
    void updateUser(User mUserData);

    @Delete
    void deleteUser(User mUserData);

}
