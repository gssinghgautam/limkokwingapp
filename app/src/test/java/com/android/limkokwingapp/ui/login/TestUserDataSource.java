package com.android.limkokwingapp.ui.login;

import com.android.limkokwingapp.data.dao.UserDao;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.data.repository.local.UserDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Created by gautam on 27/03/18.
 */

public class TestUserDataSource {

    @Mock
    UserDao userDao;

    private UserDataSource userDataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userDataSource = new UserDataSource(userDao);
    }

    @Test
    public void loadAllUser_ShouldReturnFromDatabase() {
        List<User> userList = Arrays.asList(new User(), new User());
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        given(userDao.getAllUser()).willReturn(Flowable.just(userList));

        //When
        userDataSource.findAll().subscribe(subscriber);

        then(userDao).should().getAllUser();
    }

    @Test
    public void checkIfUserExists_ShouldReturnFromDatabase() {
        User user = new User("gautam@gmail.com", "hello");
        TestSubscriber<User> subscriber = new TestSubscriber<>();
        given(userDao.checkIfUserExist("gautam@gmail.com")).willReturn(Maybe.just(user));
        //When
        userDataSource.checkIfUserExists("gautam@gmail.com").subscribe(new TestObserver<>());

        then(userDao).should().checkIfUserExist("gautam@gmail.com");
    }

    @Test
    public void addQuestion_ShouldInsertToDatabase() {
        User user = new User();
        userDataSource.saveUser(user);
        then(userDao).should().saveUser(user);
    }

    public UserDataSource getUserDataSource() {
        return userDataSource;
    }
}
