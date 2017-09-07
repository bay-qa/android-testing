package io.mattcarroll.androidtesting.signup;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.KeyEvent.KEYCODE_MINUS;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class EspressoSignUpTest {
    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule =
            new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;

    @Before
    public void setup() {
        // getTargetContext() operates on the application under test
        // getContext() operates on the test APK context
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    private static void scrollToAndTapNext() {
        onView(withId(R.id.button_next)).perform(
                scrollTo(),
                click()
        );
    }

    private static void tapNext() {
        onView(withId(R.id.button_next)).perform(
                click()
        );
    }

    private static void pressBackOnActivity() {
        // hide keyboard
        closeSoftKeyboard();
        // press back button
        pressBack();
    }

    private static void scrollToAndFill(int fieldId, String textToType){

        EspressoKey underscore = new EspressoKey.Builder()
                .withShiftPressed(true)
                .withKeyCode(KEYCODE_MINUS)
                .build();

        onView(withId(fieldId)).perform(
                scrollTo(),
                pressKey(underscore),
                typeText(textToType)
        );
    }

    @Test
    public void userSignUpVerifyBackWorksOnEachPage() {
        // Fill in personal info
        scrollToAndFill(R.id.edittext_first_name, "Boris");
        scrollToAndFill(R.id.edittext_last_name, "Gurtovoy");
        scrollToAndFill(R.id.edittext_address_line_1, "my adress 666");
        scrollToAndFill(R.id.edittext_address_city, "Los Angeles");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "90007");

        scrollToAndTapNext();

        // Select interest
        onView(withText("Chess"))
                .perform(click());

        // tap next button
        tapNext();

        // first press of back button
        pressBackOnActivity();
        onView(withText("Basketball"))
                .check(matches(isDisplayed()));

        //second press of back button
        pressBackOnActivity();
        onView(withId(R.id.edittext_first_name))
                .check(matches(isDisplayed()));

        boolean activityFinished = false;
        try {
            pressBackOnActivity();
        } catch (NoActivityResumedException e){
            activityFinished = true;
        }
        assertTrue(activityFinished);
    }

    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequired() {
        // Verify required fields show errors and non-required fields do not.
        scrollToAndTapNext();

        checkFieldHasError(R.id.edittext_first_name, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_last_name, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_line_1, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_city, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_state, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_zip, R.string.input_error_required);
    }

    private void checkFieldHasError(int fieldId, int errorId){
        onView(withId(fieldId))
                .check(matches(hasErrorText(resources.getString(errorId))));
    }


    /*HOMEWORK*/



    private void verifyViewIsChecked(String viewName) {
        onView(withText(viewName))
                .check(matches(isChecked()));
    }


    private void verifyViewIsNotChecked(String viewName) {
        onView(withText(viewName))
                .check(matches(isNotChecked()));
    }


    private static void clickViewOnTheListView(String viewName) {
        onData(hasToString(viewName))
                .perform(click());
    }

    private static void scrollToViewOnTheListView(String viewName) {
        onData(hasToString(viewName))
                .perform(scrollTo());
    }


    @Test
    public void usersInfoPressNextBackButtonsCheckAndVerifyPopup() {
        // filling
        scrollToAndFill(R.id.edittext_first_name, "Boris");
        scrollToAndFill(R.id.edittext_last_name, "Gurtovoy");
        scrollToAndFill(R.id.edittext_address_line_1, "Some adress");
        scrollToAndFill(R.id.edittext_address_city, "Los Angeles");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "90007");

        // scroll and press next
        scrollToAndTapNext();

        //click Astronomy
        clickViewOnTheListView("Astronomy");

        // check it is checked
        verifyViewIsChecked("Astronomy");

        // click next button without scrolling
        tapNext();

        // pressing back button
        pressBackOnActivity();

        scrollToViewOnTheListView("Astronomy");

        // check it is NOT checked
        verifyViewIsNotChecked("Astronomy");

        //click Astronomy
        clickViewOnTheListView("Astronomy");

        // click next button without scrolling
        tapNext();

        //fill email
        scrollToAndFill(R.id.autocompletetextview_email, "borisbayqa@gmail.com");

        //fill pass
        scrollToAndFill(R.id.edittext_password, "pass12345");

        //click sign up button
        tapNext();

    }

}
