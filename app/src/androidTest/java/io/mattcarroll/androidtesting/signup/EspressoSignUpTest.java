package io.mattcarroll.androidtesting.signup;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.KeyEvent.KEYCODE_MINUS;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by abzalbek on 8/29/17.
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

    // scroll and tap next button
    private void scrollToAndTapNext() {
        onView(withId(R.id.button_next)).perform(
                scrollTo(),
                click()
        );
    }

    // click next button
    private void tapNext() {
        onView(withId(R.id.button_next)).perform(
                click()
        );
    }

    // checking error
    private void checkFieldHasError(int filedId, int errorId) {
        onView(withId(filedId))
                .check(matches(hasErrorText(resources.getString(errorId))));
    }

    // press back button
    private static void pressBackOnActivity() {
        //hide keyboard
        closeSoftKeyboard();
        // press back button
        pressBack();
    }

    //scroll and fill, typing with code
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

    // select item with text
    private void selectItemWithText(String itemName) {
        onView(withText(itemName)).perform(
                click()
        );
    }

    // checking item with text
    private void checkItemWithTextDisplayed(String itemName) {
        onView(withText(itemName))
                .check(matches(isDisplayed()));
    }

    //checking item with ID
    private void checkitemWithIdDisplayed(int itemName) {
        onView(withId(itemName))
                .check(matches(isDisplayed()));
    }

    //checking item with text(is not checked)
    private void checkItemWithTextIsChecked(String itemName) {
        onView(withText(itemName))
                .check(matches(isNotChecked()));
    }

    // scrolling list view , finding element with text (need to ask!!!)
    private static void scrollingListView(String itemName) {
        onData(allOf(is(instanceOf(String.class)), is(itemName))).perform(
                scrollTo()
        );
    }

    //checking popup with text
    private void checkPopupWithText(String itemName){
        onView(withText(itemName))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    // catching exception(need to go over again)
    private static void catchingException() {
        // catching exception
        boolean activityFinished = false;
        try {
            pressBackOnActivity();
        } catch (NoActivityResumedException e) {
            activityFinished = true;
        }
        assertTrue(activityFinished);
    }

    @Test
    public void usersSignUpPersonalInfoVerifyRequiredFieldsAreRequired() {

        scrollToAndTapNext();

        checkFieldHasError(R.id.edittext_first_name, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_last_name, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_line_1, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_city, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_state, R.string.input_error_required);
        checkFieldHasError(R.id.edittext_address_zip, R.string.input_error_required);
    }

    @Test
    public void userSignUpVerifyBackWorksOnEachPage() {
        // filling
        scrollToAndFill(R.id.edittext_first_name, "Joe");
        scrollToAndFill(R.id.edittext_last_name, "Taylor");
        scrollToAndFill(R.id.edittext_address_line_1, "Star ave");
        scrollToAndFill(R.id.edittext_address_city, "SanJose");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "90003");

        // scrolling and tap next button
        scrollToAndTapNext();

        // Select chess
        selectItemWithText("Chess");

        // click next button without scrolling
        tapNext();

        // pressing back button
        pressBackOnActivity();

        //checking item
        checkItemWithTextDisplayed("Basketball");

        //second time press back button
        pressBackOnActivity();

        // checking "first name"
        checkitemWithIdDisplayed(R.id.edittext_first_name);

        //catching exception
        catchingException();
    }

    /*HOMEWORK*/
    @Test
    public void usersInfoPressNextBackButtonsCheckAndVerifyPopup() {
        // filling
        scrollToAndFill(R.id.edittext_first_name, "Joe");
        scrollToAndFill(R.id.edittext_last_name, "Taylor");
        scrollToAndFill(R.id.edittext_address_line_1, "Star ave");
        scrollToAndFill(R.id.edittext_address_city, "SanJose");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "90003");

        // scrolling and tap next button
        scrollToAndTapNext();

        //scrolling and clicking checkbox
        scrollingListView("Astronomy");
        selectItemWithText("Astronomy");
        //onView(withText("Astronomy"))

        // click next button without scrolling
        tapNext();

        // pressing back button
        pressBackOnActivity();

        // checkin checkbox
        scrollingListView("Astronomy");
        checkItemWithTextIsChecked("Astronomy");

        //clicking checkbox
        selectItemWithText("Astronomy");

        // click next button without scrolling
        tapNext();

        // providing email and password
        scrollToAndFill(R.id.autocompletetextview_email, "bayQA@email.com");
        scrollToAndFill(R.id.edittext_password, "password2017");

        // click next button without scrolling
        tapNext();

        //checking popup
        //checkPopupWithText("Signup successful!");
        onView(withText("You'll receive a verification email shortly.")).inRoot(isDialog()).check(matches(isDisplayed()));
    }
}
