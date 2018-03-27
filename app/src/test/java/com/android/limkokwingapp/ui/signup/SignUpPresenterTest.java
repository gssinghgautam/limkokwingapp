package com.android.limkokwingapp.ui.signup;

import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.ui.login.TestScheduleProvider;
import com.android.limkokwingapp.ui.login.TestUserDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gautam on 27/03/18.
 */
public class SignUpPresenterTest {

    @Mock
    SignUpContract.View view;

    private TestScheduleProvider testScheduleProvider;

    private UserDataSource dataSource;

    private SignUpPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduleProvider = new TestScheduleProvider();
        TestUserDataSource testUserDataSource = new TestUserDataSource();
        dataSource = testUserDataSource.getUserDataSource();
        presenter = new SignUpPresenter(view, dataSource, testScheduleProvider);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        when(view.getEmail()).thenReturn("");
        presenter.doSignUp();
        verify(view).onFieldEmpty();
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsInvalid() throws Exception {
        when(view.getEmail()).thenReturn("name");
        when(view.getPassword()).thenReturn("gautam123!@#");
        when(view.getMobile()).thenReturn("813094014");
        when(view.getGender()).thenReturn("Male");
        presenter.doSignUp();
        verify(view).onEmailInvalid();
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(view.getFName()).thenReturn("Gautam");
        when(view.getLName()).thenReturn("Kumar");
        when(view.getEmail()).thenReturn("name@gmail.com");
        when(view.getPassword()).thenReturn("");
        when(view.getMobile()).thenReturn("813094014");
        when(view.getGender()).thenReturn("Male");
        presenter.doSignUp();
        verify(view).onFieldEmpty();
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsInvalid() throws Exception {
        when(view.getFName()).thenReturn("Gautam");
        when(view.getLName()).thenReturn("Kumar");
        when(view.getEmail()).thenReturn("name@gmail.com");
        when(view.getPassword()).thenReturn("gautam12");
        when(view.getMobile()).thenReturn("813094014");
        when(view.getGender()).thenReturn("Male");
        presenter.doSignUp();
        verify(view).onPasswordInvalid();
    }

    @Test
    public void shouldShowErrorMessageWhenMobileIsEmpty() throws Exception {
        when(view.getFName()).thenReturn("Gautam");
        when(view.getLName()).thenReturn("Kumar");
        when(view.getEmail()).thenReturn("name@gmail.com");
        when(view.getPassword()).thenReturn("gautam123!@#");
        when(view.getMobile()).thenReturn("");
        when(view.getGender()).thenReturn("Male");
        presenter.doSignUp();
        verify(view).onFieldEmpty();
    }

    @Test
    public void shouldShowErrorMessageWhenMobileIsInvalid() throws Exception {
        when(view.getFName()).thenReturn("Gautam");
        when(view.getLName()).thenReturn("Kumar");
        when(view.getEmail()).thenReturn("name@gmail.com");
        when(view.getPassword()).thenReturn("gautam123!@#");
        when(view.getMobile()).thenReturn("8130940");
        when(view.getGender()).thenReturn("Male");
        presenter.doSignUp();
        verify(view).onMobileInvalid();
    }

    @Test
    public void shouldShowErrorMessageWhenGenderIsEmpty() throws Exception {
        when(view.getFName()).thenReturn("Gautam");
        when(view.getLName()).thenReturn("Kumar");
        when(view.getEmail()).thenReturn("name@gmail.com");
        when(view.getPassword()).thenReturn("gautam123!@#");
        when(view.getMobile()).thenReturn("813094014");
        when(view.getGender()).thenReturn("");
        presenter.doSignUp();
        verify(view).onFieldEmpty();
    }

    @Test
    public void shouldShowErrorMessageWhenGenderIsInvalid() throws Exception {
        when(view.getFName()).thenReturn("Gautam");
        when(view.getLName()).thenReturn("Kumar");
        when(view.getEmail()).thenReturn("name@gmail.com");
        when(view.getPassword()).thenReturn("gautam123!@#");
        when(view.getMobile()).thenReturn("813094014");
        when(view.getGender()).thenReturn("Select Gender");
        presenter.doSignUp();
        verify(view).onGenderInvalid();
    }


}