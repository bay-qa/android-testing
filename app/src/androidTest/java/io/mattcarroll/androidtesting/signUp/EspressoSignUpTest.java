package io.mattcarroll.androidtesting.signUp;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.signup.SignUpActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.KeyEvent.KEYCODE_MINUS;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by igorkalenichenko on 8/30/17.
 */

public class EspressoSignUpTest extends BaseTest {

    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule =
            new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    private static void scrollToAndTapNext() {
        onView(withId(R.id.button_next)).perform(
                scrollTo(),
                click()
        );
    }

    private void selectAstronomy(String itemName) {
        onView(withText(itemName)).perform(
                click()
        );
    }

    private static void scrollToAstronomy(String itemName) {
        onData(allOf(is(instanceOf(String.class)), is(itemName))).perform(
                scrollTo()
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

    private static void scrollToAndFill(int fieldId, String textToType) {

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
    public void userSignUpVerifyBackWorksonEachPage() {
        //Fill in personal info
        scrollToAndFill(R.id.edittext_first_name, "Igor");
        scrollToAndFill(R.id.edittext_last_name, "Kalenichenko");
        scrollToAndFill(R.id.edittext_address_line_1, "My Address");
        scrollToAndFill(R.id.edittext_address_city, "Seattle");
        scrollToAndFill(R.id.edittext_address_state, "WA");
        scrollToAndFill(R.id.edittext_address_zip, "98104");
        scrollToAndTapNext();

        //select interest
        scrollToAstronomy("Astronomy");
        selectAstronomy("Astronomy");

        //tap next button
        tapNext();

        //first press of back button
        pressBackOnActivity();

        onView(withText("Astronomy"))
                .check(matches(isNotChecked()));
        selectAstronomy("Astronomy");
        tapNext();

        scrollToAndFill(R.id.autocompletetextview_email, "automationforewer@gmail.com");
        scrollToAndFill(R.id.edittext_password, "Test123!");
        tapNext();
    }

    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequired() {

        scrollToAndTapNext();

        checkFieldHasError(R.id.edittext_first_name, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_last_name, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_line_1, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_city, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_state, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_zip, R.string.input_error_required);
    }

    private void checkFieldHasError(int fieldId, int errorId) {
        onView(withId(fieldId))
                .check(matches(hasErrorText(resources.getString(errorId))));
    }
}