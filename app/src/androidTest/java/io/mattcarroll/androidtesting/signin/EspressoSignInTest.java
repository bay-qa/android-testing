package io.mattcarroll.androidtesting.signin;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.login.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


/**
 * Created by sodintsov on 8/30/17.
 */

public class EspressoSignInTest {
    @Rule
    public final ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class, false, true);

    @Test
    public void userSignInVerifyRequiredFieldsAreRequired(){
        onView(ViewMatchers.withId(R.id.button_sign_in))
                .perform(click());
        onView(withId(R.id.edittext_email))
                .check(matches(hasErrorText("This field is required")));
        }

    @Test
    public void signInUserWithValidLoginAndPassword(){
        onView(ViewMatchers.withId(R.id.edittext_email))
                .perform(scrollTo(),clearText(),typeText("email@gmail.com"));
        onView(ViewMatchers.withId(R.id.edittext_password))
                .perform(scrollTo(),clearText(),typeText("password123"));
        onView(ViewMatchers.withId(R.id.button_sign_in))
                .perform(click());
        onView(ViewMatchers.withId(R.id.textview_no_accounts))
                .check(matches(isDisplayed()));
    }
}
