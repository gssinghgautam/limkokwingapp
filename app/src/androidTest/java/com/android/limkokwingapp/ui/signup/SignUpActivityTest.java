package com.android.limkokwingapp.ui.signup;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.ui.main.MainActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by gautam on 27/03/18.
 */
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public final ActivityTestRule<SignUpActivity> mActivityTestRule =
            new ActivityTestRule<>(SignUpActivity.class, true, false);

    @Test
    public void checkIfViewsAreDisplayed() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.edit_last_name)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.edit_email_layout)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.edit_password_layout)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.edit_mobile_layout)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.sp_gender)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfErrorMessageIsDisplayedForEmptyFields() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        Espresso.onView(withText(R.string.empty_form_error_msg)).
                inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfErrorMessageIsDisplayedForInvalidEmail() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).perform(typeText("Gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_last_name)).perform(typeText("Kumar"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam12!@"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_mobile_layout)).perform(typeText("813094014"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sp_gender)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.sp_gender)).check(matches(withSpinnerText(containsString("Male"))));
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        Espresso.onView(withText(R.string.invalid_email_error_msg)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfErrorMessageIsDisplayedForInvalidPassword() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).perform(typeText("Gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_last_name)).perform(typeText("Kumar"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_mobile_layout)).perform(typeText("813094014"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sp_gender)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.sp_gender)).check(matches(withSpinnerText(containsString("Male"))));
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        Espresso.onView(withText(R.string.invalid_password_error_msg)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfErrorMessageIsDisplayedForInvalidMobile() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).perform(typeText("Gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_last_name)).perform(typeText("Kumar"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam123!@#"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_mobile_layout)).perform(typeText("81309"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sp_gender)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.sp_gender)).check(matches(withSpinnerText(containsString("Male"))));
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        Espresso.onView(withText(R.string.invalid_mobile_error_msg)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfErrorMessageIsDisplayedForInvalidGender() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).perform(typeText("Gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_last_name)).perform(typeText("Kumar"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam123!@#"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_mobile_layout)).perform(typeText("813094014"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sp_gender)).perform(click());
        Espresso.onData(anything()).atPosition(0).perform(click());
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        Espresso.onView(withText(R.string.invalid_gender)).
                inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfErrorMessageIsDisplayedIfEmailExists() {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).perform(typeText("Gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_last_name)).perform(typeText("Kumar"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam123!@#"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_mobile_layout)).perform(typeText("813094014"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sp_gender)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        Espresso.onView(withText(R.string.email_exists_error_msg)).
                inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfNewActivityIsOpenedOnSuccessfulSignUp() {
        Intents.init();
        mActivityTestRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.edit_first_name)).perform(typeText("Gautam"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_last_name)).perform(typeText("Kumar"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_email_layout)).perform(typeText("gautam1@gmail.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_password_layout)).perform(typeText("gautam123!@#"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.edit_mobile_layout)).perform(typeText("813094014"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sp_gender)).perform(click());
        Espresso.onData(anything()).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.btn_sign_up)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

}