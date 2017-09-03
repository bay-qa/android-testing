package io.mattcarroll.androidtesting;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspressoSignInTest {

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    @Test
    public void userSignInVerifyNoLinkedAccounts() {
        onView(withId(R.id.edittext_email))
                .perform(typeText("a@a.com"));

        onView(withId(R.id.edittext_password))
                .perform(typeText("password"));

        onView(withId(R.id.button_sign_in))
                .perform(click());

        onView(withId(R.id.textview_no_accounts))
                .check(matches(isDisplayed()));
    }
}