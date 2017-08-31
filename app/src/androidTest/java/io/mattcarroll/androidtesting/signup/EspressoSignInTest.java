package io.mattcarroll.androidtesting.signup;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 8/31/17.
 */

public class EspressoSignInTest {

    @Rule

    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false,true);

    @Test

    public void usersSignInPersonalInfoRequiredFieldAreFilled(){

        onView(withId(R.id.edittext_email))
                .perform(typeText("bayQATraining@email.com"));

        onView(withId(R.id.edittext_password))
                .perform(typeText("Password2017"));

        onView(withId(R.id.button_sign_in)).perform(click());

        onView(withId(R.id.textview_no_accounts)).check(matches("No linked accounts"));


    }
}
