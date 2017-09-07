package io.mattcarroll.androidtesting;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.KeyEvent.KEYCODE_MINUS;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

public class EspressoSignUpTest {

    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule =
            new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    private static void scrollToAndTapNext() {
        onView(withId(R.id.button_next)).perform(scrollTo(), click());
    }

    private static void tapNext() {
        onView(withId(R.id.button_next)).perform(click());
    }

    private void checkFieldHasError(int fieldId, int errorId) {
        onView(withId(fieldId))
                .check(matches(hasErrorText(resources.getString(errorId))));
    }

    public static void pressBackOnActivity() {
        // hide key board
        closeSoftKeyboard();
        // press back button
        pressBack();
    }

    private void selectItemByText(String item) {
        onView(withText(item)).perform(click());
    }

    private static void scrollToItem(String item) {
        onData(allOf(is(instanceOf(String.class)), is(item))).perform(scrollTo());
    }

    private void fillInPersonalInfo(
            String name, String lastName, String address, String city, String state, String zipCode
                                        ) {
        scrollToAndFill(R.id.edittext_first_name, name);
        scrollToAndFill(R.id.edittext_last_name, lastName);
        scrollToAndFill(R.id.edittext_address_line_1, address);
        scrollToAndFill(R.id.edittext_address_city, city);
        scrollToAndFill(R.id.edittext_address_state, state);
        scrollToAndFill(R.id.edittext_address_zip, zipCode);
    }

    public void checkIfItemSelected(String item) {
        onView(withText(item)).check(matches(isNotChecked()));
    }

    public void fillInCredentials(String email, String password) {
        scrollToAndFill(R.id.autocompletetextview_email, email);
        scrollToAndFill(R.id.edittext_password, password);
    }

    public static void clickSignUp() {
        onView(withId(R.id.button_next)).perform(click());
    }

    public void checkSuccessfulPopUp() {
        onView(withId(R.id.alertTitle)).check(matches(allOf(withText("Signup successful!"), isDisplayed())));
    }

    private static void scrollToAndFill(int fieldId, String textToType) {
        EspressoKey underscore = new EspressoKey.Builder()
                .withShiftPressed(true)
                .withKeyCode(KEYCODE_MINUS)
                .build();

        onView(withId(fieldId))
                .perform(scrollTo(),
//                        pressKey(underscore),
                        typeText(textToType));
    }

    @Test
    public void userSignUpVerifyBackButtonWorksOnEachPage() {
        // fill in personal info
        fillInPersonalInfo(
                "TestName", "TestLastName", "TestAddress", "TestCity", "TestState", "12345"
        );

        scrollToAndTapNext();
        // select interest
        onView(withText("Chess")).perform(click());

        // tap next button
        tapNext();

        // first press back button
        pressBackOnActivity();
        onView(withText("Basketball")).check(matches(isDisplayed()));

        //second press of back button
        pressBackOnActivity();
        onView(withId(R.id.edittext_first_name)).check(matches(isDisplayed()));

        boolean activityFinish = false;
        try {
            pressBackOnActivity();
        } catch (NoActivityResumedException e) {
            activityFinish = true;
        }
        assertTrue(activityFinish);
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

    @Test
    public void userSignUpPositiveTest() {
        fillInPersonalInfo(
                "Vitaly", "W", "460E 5th Avenue", "New York", "NY", "10012"
        );
        scrollToAndTapNext();
        scrollToItem("Astronomy");
        selectItemByText("Astronomy");
        tapNext();
        pressBackOnActivity();
        scrollToItem("Astronomy");
        checkIfItemSelected("Astronomy");
        selectItemByText("Astronomy");
        tapNext();
        //fill in credentials
        fillInCredentials("a@a.com", "password");
        //click sign up
        clickSignUp();
        // verify the pop up
//        checkSuccessfulPopUp();
    }
}
