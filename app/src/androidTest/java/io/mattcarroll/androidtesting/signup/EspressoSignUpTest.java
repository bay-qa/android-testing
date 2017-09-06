package io.mattcarroll.androidtesting.signup;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.androidtesting.SignUpActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.KeyEvent.KEYCODE_MINUS;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by oxana on 8/31/2017.
 */

public class EspressoSignUpTest {
    @Rule
    public final ActivityTestRule<SignUpActivity>activityRule =
            new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;

    @Before
    public void  setup() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    private static void scrollToAndTapNext(){
        onView(withId(R.id.button_next)).perform(
                scrollTo(),
                click()
        );
    }

    private static void pressBackOnActivity(){
        //hide keyboard
        closeSoftKeyboard();
        //press back button
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

    //scroll list
    private static void scrollToForInterest(String itemName) {
        onData(allOf(is(instanceOf(String.class)), is(itemName)))
                .perform(scrollTo());
    }

    //check checkbox
    private static void interestIsNotSelected(String itemName) {
        onView(withText(itemName))
                .check(matches(isNotChecked()));

    }
    //put checkbox
    private static void selectInterest(String itemName){
        onView(withText(itemName))
                .perform(click());
    }


    private static void tapNext(){
        onView(withId(R.id.button_next)).perform(
                click()
        );
    }


    // lesson3_class
    @Test
    public void userSignUpVerifyBackWorksOnEachPage(){
        scrollToAndFill(R.id.edittext_first_name, "Oxana");
        scrollToAndFill(R.id.edittext_last_name, "Ermolenko");
        scrollToAndFill(R.id.edittext_address_line_1, "address");
        scrollToAndFill(R.id.edittext_address_city, "Mountain view");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "94043");

        //tap next button
        scrollToAndTapNext();

        //select interest
        onView(withText("Chess"))
                .perform(click());

        //tap next button
        tapNext();

        //first press of back button
        pressBackOnActivity();
        onView(withText("Basketball"))
                .check(matches(isDisplayed()));

        //second press of back button
        pressBackOnActivity();
        onView(withId(R.id.edittext_first_name))
                .check(matches(isDisplayed()));

        boolean activityFinished = false;
        try{
            pressBackOnActivity();
        }catch (NoActivityResumedException e){
            activityFinished = true;
        }
        assertTrue(activityFinished);


    }
    //homework lesson3
    @Test
    public void userSignUpPersonalInfoVerifyPopupAboutVerificationEmail(){

        scrollToAndFill(R.id.edittext_first_name, "Oxana");
        scrollToAndFill(R.id.edittext_last_name, "Ermolenko");
        scrollToAndFill(R.id.edittext_address_line_1, "address");
        scrollToAndFill(R.id.edittext_address_city, "Mountain view");
        scrollToAndFill(R.id.edittext_address_state, "CA");
        scrollToAndFill(R.id.edittext_address_zip, "94043");

        //tap next button
        scrollToAndTapNext();

        //select interest
        scrollToForInterest("Astronomy");
        selectInterest("Astronomy");

        //tap next button
        tapNext();

        //press back button
        pressBackOnActivity();

        //verify that Astronomy is not selected and select again
        scrollToForInterest("Astronomy");
        interestIsNotSelected("Astronomy");
        selectInterest("Astronomy");


        //tap next button
        tapNext();

        //provide email password
        scrollToAndFill(R.id.autocompletetextview_email, "bayqa@yahoo.com");
        scrollToAndFill(R.id.edittext_password, "password");

        //tap next button
        tapNext();




    }

    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequired(){

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
}
