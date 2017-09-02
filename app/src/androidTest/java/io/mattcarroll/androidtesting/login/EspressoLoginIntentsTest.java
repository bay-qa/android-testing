package io.mattcarroll.androidtesting.login;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.signup.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspressoLoginIntentsTest {
    // Results that will be used in tests
    private static final Instrumentation.ActivityResult SUCCESS =
            new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
    private static final Instrumentation.ActivityResult SIGN_UP_FAILED =
            new Instrumentation.ActivityResult(LoginActivity.RESULT_SIGN_UP_FAILED, null);
    private static final Instrumentation.ActivityResult CANCELLED =
            new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null);

    // IntentsTestRule should be used instead of ActivityTestRule
    // to enable validation and stubbing of intents sent out by an app
    // This rule takes the same arguments as ActivityTestRule
    @Rule
    public final IntentsTestRule<LoginActivity> intentsRule =
            new IntentsTestRule<>(LoginActivity.class, false, true);

    @Test
    public void loginActivityShouldLaunchSignUpActivityWhenSignUpPressed() {
        clickSignUp();

        // Verify SignUpActivity intent is fired
        intended(
                // this form can be used to ease refactoring
                //hasComponent(SignUpActivity.class.getName())

                // and this form is more readable
                hasComponent(hasShortClassName(".signup.SignUpActivity"))
        );
    }

    @Test
    public void loginActivityShouldShowSuccessDialogWhenSignUpSucceeds() {
        intending(hasComponent(SignUpActivity.class.getName()))
                .respondWith(SUCCESS);

        clickSignUp();

        // Verify the success dialog is shown
        onView(withText(R.string.dialog_title_signup_successful))
                .check(matches(isDisplayed()));
        onView(withText(R.string.dialog_message_signup_successful))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginActivityShouldShowFailureDialogWhenSignUpFails() {
        intending(hasComponent(SignUpActivity.class.getName()))
                .respondWith(SIGN_UP_FAILED);

        clickSignUp();

        // Verify the failure dialog is shown
        onView(withText(R.string.dialog_title_signup_failed))
                .check(matches(isDisplayed()));
        onView(withText(R.string.dialog_message_signup_failed))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loginActivityShouldShowLoginScreenWhenSignUpCancelled() {
        intending(hasComponent(SignUpActivity.class.getName()))
                .respondWith(CANCELLED);

        clickSignUp();

        // Verify the login screen is now visible
        onView(withId(R.id.email_login_form))
                .check(matches(isDisplayed()));
    }

    private void clickSignUp() {
        onView(withId(R.id.button_sign_up))
                .perform(scrollTo(), click());
    }
}
