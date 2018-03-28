package com.android.limkokwingapp.ui.login;

import android.content.SharedPreferences;

import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.ui.login.presenter.LoginPresenter;
import com.android.limkokwingapp.ui.login.view.LoginContract;
import com.android.limkokwingapp.utility.ValidationUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gautam on 27/03/18.
 */


@SuppressWarnings("DefaultFileTemplate")
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginContract.LoginView loginView;

    @Mock
    private SharedPreferences sharedPreference;

    private LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        TestScheduleProvider testScheduleProvider = new TestScheduleProvider();
        TestUserDataSource testUserDataSource = new TestUserDataSource();
        UserDataSource dataSource = testUserDataSource.getUserDataSource();
        loginPresenter = new LoginPresenter(loginView, dataSource, testScheduleProvider, sharedPreference);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        when(loginView.getEmail()).thenReturn("");
        loginPresenter.loginUser();
        verify(loginView).onEmailEmpty();
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(loginView.getEmail()).thenReturn("gautam@gmail.com");
        when(loginView.getPassword()).thenReturn("");
        loginPresenter.loginUser();
        verify(loginView).onPasswordEmpty();
    }

    @Test
    public void emailValidator_CorrectEmail_ReturnsTrue() throws Exception {
        assertTrue(ValidationUtils.isValidEmail("gautam@gmail.com"));
    }

    @Test
    public void passwordValidator_CorrectPasswordFormat_ReturnsTrue() throws Exception {
        assertTrue(ValidationUtils.isValidPassword("gautam123!"));
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsInvalid() throws Exception {
        when(loginView.getEmail()).thenReturn("gautam");
        when(loginView.getPassword()).thenReturn("gautam123!");
        loginPresenter.loginUser();
        verify(loginView).onEmailInvalid();
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsInvalid() throws Exception {
        when(loginView.getEmail()).thenReturn("gautam@gmail.com");
        when(loginView.getPassword()).thenReturn("gautam");
        loginPresenter.loginUser();
        verify(loginView).onPasswordInvalid();
    }

}