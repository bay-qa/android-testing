package io.mattcarroll.androidtesting.signup;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

/**
 * Created by borisgurtovyy on 8/28/17.
 */

public class EspressoSignUpTest {

    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule =
            new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;

    @Before
    public void setup() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }


    private static void scrollToAndTapNext(){
        onView(withId(R.id.button_next)).perform(scrollTo(),click());
    }

    private static void tapNext(){
        onView(withId(R.id.button_next)).perform(click());
    }

    private static void pressBackOnActivity(){
        closeSoftKeyboard();
        pressBack();
    }

    private static void scrollToAndFill(int fieldId, String textToType){
        onView(withId(fieldId))
                .perform(scrollTo(), typeText(textToType));
    }


    @Test
    public void userSignUpVerifyBackWorksOnEachPage(){
        scrollToAndFill(R.id.edittext_first_name, "Sergio");
        scrollToAndFill(R.id.edittext_last_name, "Odin");
        scrollToAndFill(R.id.edittext_address_line_1, "1600 Amphitheatre Pkw");
        scrollToAndFill(R.id.edittext_address_city, "MTV");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "94043");

        scrollToAndTapNext();

        onView(withText("Astronomy"))
                .perform(scrollTo(),click());

        tapNext();

        pressBackOnActivity();
        onView(withText("Astronomy"))
                .perform(scrollTo())
                .check(matches(isNotChecked()));

        onView(withText("Astronomy"))
                .perform(scrollTo(),click());

        onView(withId(R.id.autocompletetextview_email))
                .perform(scrollTo(),clearText(),typeText("myMail@email.com"));
        onView(withId(R.id.edittext_password))
                .perform(scrollTo(),clearText(),typeText("myPassCode"));

        tapNext();

        onView(withId(R.id.alertTitle))
                .check(matches(isDisplayed()));

//        boolean activityFinished = false;
//        try {
//            pressBackOnActivity();
//        } catch (NoActivityResumedException e){
//            activityFinished = true;
//        }
//
//        assertTrue(activityFinished);
    }

    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequired() {
        scrollToAndTapNext();
        checkFieldIdsError(R.id.edittext_first_name, R.string.input_error_required);
        checkFieldIdsError(R.id.edittext_last_name, R.string.input_error_required);
        checkFieldIdsError(R.id.edittext_address_line_1, R.string.input_error_required);
        checkFieldIdsError(R.id.edittext_address_city, R.string.input_error_required);
        checkFieldIdsError(R.id.edittext_address_state, R.string.input_error_required);
        checkFieldIdsError(R.id.edittext_address_zip, R.string.input_error_required);
    }

    private void checkFieldIdsError(int fieldId, int errorId){
        onView(withId(fieldId))
                .check(matches(hasErrorText(resources.getString(errorId))));

    }
}
