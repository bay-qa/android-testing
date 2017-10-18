package io.mattcarroll.androidtesting.login;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.signup.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by borisgurtovyy on 8/30/17.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoLoginTest {

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    @Test
    public void userLogsInSuccessfully() {
        onView(withId(R.id.edittext_email))
                .perform(typeText("borisbayqa@gmail.com"));
        onView(withId(R.id.edittext_password))
                .perform(typeText("password1235"));
        onView(withId(R.id.button_sign_in))
                .perform(scrollTo())
                .perform(click());
        onView(withId(R.id.fab_manage_accounts))
                .check(matches(isDisplayed()));
    }
}
