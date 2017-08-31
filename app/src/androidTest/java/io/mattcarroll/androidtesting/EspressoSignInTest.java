package io.mattcarroll.androidtesting;

import android.support.test.rule.ActivityTestRule;

import io.mattcarroll.androidtesting.login.LoginActivity;
import io.mattcarroll.androidtesting.signup.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


/**
 * Created by sodintsov on 8/30/17.
 */

public class EspressoSignInTest {
    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule = new ActivityTestRule<>(SignUpActivity.class, false, true);


    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequired(){
        onView(withId(R.id.button_next))
                .perform(scrollTo())
                .perform(click());
        onView(withId(R.id.edittext_first_name))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_last_name))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_address_line_1))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_address_line_2))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_account_number))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_address_city))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_address_state))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_address_zip))
                .check(matches(hasErrorText("Required.")));
        onView(withId(R.id.edittext_bank_name))
                .check(matches(hasErrorText("Required.")));
    }
}
