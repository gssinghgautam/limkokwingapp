package com.android.limkokwingapp.ui.login;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.ui.main.MainActivity;
import com.android.limkokwingapp.ui.signup.SignUpActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;


/**
 * Created by gautam on 27/03/18.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityTestRule<LoginActivity> mActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class, true, false);

    @Test
    public void checkEmailEditTextIsDisplayed() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_email_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkPasswordEditTextIsDisplayed() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_password_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkErrorMessageIsDisplayedForEmptyData() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.btn_login)).perform(click());
        Espresso.onView(withText(R.string.empty_fields_error_msg)).
                inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkErrorMessageIsDisplayedForInvalidEmail() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.btn_login)).perform(click());
        Espresso.onView(withText(R.string.invalid_email_error_msg)).check(matches(isDisplayed()));
    }

    @Test
    public void checkErrorMessageIsDisplayedForInvalidPassword() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.btn_login)).perform(click());
        Espresso.onView(withText(R.string.invalid_password_error_msg)).check(matches(isDisplayed()));
    }

    @Test
    public void checkErrorMessageIsDisplayedIfUserNotExists() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam123!@#"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.btn_login)).perform(click());
        Espresso.onView(withText(R.string.user_not_exists)).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfMainActivityIsOpenedOnSuccessfulLogin(){
        Intents.init();
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam1@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam123!@#"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.btn_login)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }


}