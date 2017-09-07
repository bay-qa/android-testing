package io.mattcarroll.androidtesting.signup;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.usersession.UserSession;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;

/**
 * Test suite that demonstrates testing of the Sign-Up flow using a Page Object pattern.
 * In this example, each Page is implemented as a static inner class of this test suite to avoid
 * confusing these Page Objects with other tests.
 * In practice, Page Objects should be defined as standalone Classes
 * so that other tests can make use of them.
 */
@RunWith(AndroidJUnit4.class)
public class PageObjectSignUpTest {
    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule =
            new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;

    @Before
    public void setup() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    @After
    public void teardown() {
        UserSession.getInstance().logout();
    }

    @Test
    public void userSignUpVerifyBackWorksOnEachPage() {
        // We create PersonalInfoPage but it next() in the last line returns CredentialsPage
        CredentialsPage credentialsPage = new PersonalInfoPage()
                .firstName("Matt")
                .lastName("Carroll")
                .addressLine1("123 Fake Street")
                .city("Palo Alto")
                .state("CA")
                .zip("94024")
                .submitAndExpectInterestsPage() // we are on the interests page after calling this method
                    // explicitly write expected page name to make PO less dependent on expected behavior
                .selectInterests("Football")
                .submitAndExpectCredentialsPage(); // this next() and returns CredentialsPage

        // We're on the final page.  Now go back to each previous page.
        InterestsPage interestsPage = credentialsPage.back();

        // We're on the interests page.  Go back to the Personal Info page.
        PersonalInfoPage personalInfoPage = interestsPage.back();

        // Go back again. We expect the Sign Up Activity to disappear.
        boolean didActivityFinish = false;
        try {
            personalInfoPage.back();
        } catch (NoActivityResumedException e) {
            didActivityFinish = true;
        }
        assertTrue(didActivityFinish);
    }

    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequired() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        // Verify required fields show errors and non-required fields do not.
        PersonalInfoPage page = new PersonalInfoPage();

        page.submit(); // trigger form errors and do not expect next page

        // one method per each assertion type, this way tests will slowly leak into Page Objects
        page.assertHasFirstNameError(REQUIRED_FIELD_ERROR)
                .assertHasLastNameError(REQUIRED_FIELD_ERROR)
                .assertHasAddressLine1Error(REQUIRED_FIELD_ERROR)

                // with Espresso and Hamcrest we have better option:
                .assertCity(hasErrorText(REQUIRED_FIELD_ERROR))
                .assertState(hasErrorText(REQUIRED_FIELD_ERROR))
                .assertZip(hasErrorText(REQUIRED_FIELD_ERROR));
                // This way we have to add methods only per verified View, not per assertion type
    }

    @Test
    public void userSignUpHappyPath() {
        new PersonalInfoPage()
                .firstName("Matt")
                .lastName("Carroll")
                .addressLine1("123 Fake Street")
                .city("Palo Alto")
                .state("CA")
                .zip("94024")
                .submitAndExpectInterestsPage()
                .selectInterests("Football")
                .submitAndExpectCredentialsPage()
                .username("myuser")
                .password("123456")
                .submitAndExpectCredentialsPage();
    }

    @Test
    public void userSignUpInterestsVerifySelectionRequiredToContinue() {
        new PersonalInfoPage()
                .firstName("Matt")
                .lastName("Carroll")
                .addressLine1("123 Fake Street")
                .city("Palo Alto")
                .state("CA")
                .zip("94024")
                .submitAndExpectInterestsPage()
                .submit();  // we don't expect any page here, so this method returns void

        // Verify that a dialog is displayed with an error message.
        onView(withText(R.string.dialog_select_interests_body)).check(matches(isDisplayed()));
    }

    @Test
    public void userSignUpCredentialsVerifyUsernameAndPasswordAreRequired() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        CredentialsPage credentialsPage = new PersonalInfoPage()
                .firstName("Matt")
                .lastName("Carroll")
                .addressLine1("123 Fake Street")
                .city("Palo Alto")
                .state("CA")
                .zip("94024")
                .submitAndExpectInterestsPage()
                .selectInterests("Football")
                .submitAndExpectCredentialsPage();

        credentialsPage.submitAndExpectCredentialsPage(); // trigger form errors.

        // Verify that credentials are required for sign up.
        credentialsPage.assertHasUsernameError(REQUIRED_FIELD_ERROR)
                .assertHasPasswordError(REQUIRED_FIELD_ERROR);
    }

    private static class PersonalInfoPage {
        public PersonalInfoPage() {
            /*
             * Another common implementation is to have separate assertDisplayed method
             * but it is easy to forget calling it
             */
            onView(withId(R.id.edittext_first_name)).check(matches(isDisplayed()));
        }

        public PersonalInfoPage assertHasFirstNameError(@NonNull String error) {
            onView(withId(R.id.edittext_first_name))
                    .check(matches(hasErrorText(error)));
            return this;
        }

        public PersonalInfoPage firstName(@NonNull String firstName) {
            onView(withId(R.id.edittext_first_name)).perform(
                    scrollTo(),
                    typeText(firstName));
            return this;
        }

        public PersonalInfoPage assertHasLastNameError(@NonNull String error) {
            onView(withId(R.id.edittext_last_name))
                    .check(matches(hasErrorText(error)));
            return this;
        }

        public PersonalInfoPage lastName(@NonNull String lastName) {
            onView(withId(R.id.edittext_last_name)).perform(
                    scrollTo(),
                    typeText(lastName));
            return this;
        }

        public PersonalInfoPage assertHasAddressLine1Error(@NonNull String error) {
            onView(withId(R.id.edittext_address_line_1))
                    .check(matches(hasErrorText(error)));
            return this;
        }

        public PersonalInfoPage addressLine1(@NonNull String addressLine1) {
            onView(withId(R.id.edittext_address_line_1)).perform(
                    scrollTo(),
                    typeText(addressLine1));
            return this;
        }

        public PersonalInfoPage assertCity(Matcher<View> matcher) {
            onView(withId(R.id.edittext_address_city))
                    .check(matches(matcher));
            return this;
        }

        public PersonalInfoPage city(@NonNull String city) {
            onView(withId(R.id.edittext_address_city)).perform(
                    scrollTo(),
                    typeText(city));
            return this;
        }

        public PersonalInfoPage assertState(Matcher<View> matcher) {
            onView(withId(R.id.edittext_address_state))
                    .check(matches(matcher));
            return this;
        }

        public PersonalInfoPage state(@NonNull String state) {
            onView(withId(R.id.edittext_address_state)).perform(
                    scrollTo(),
                    typeText(state));
            return this;
        }

        public PersonalInfoPage assertZip(Matcher<View> matcher) {
            onView(withId(R.id.edittext_address_zip))
                    .check(matches(matcher));
            return this;
        }

        public PersonalInfoPage zip(@NonNull String zip) {
            onView(withId(R.id.edittext_address_zip)).perform(
                    scrollTo(),
                    typeText(zip));
            return this;
        }

        public void back() {
            pressBack();
        }

        public InterestsPage submitAndExpectInterestsPage() {
            submit();
            return new InterestsPage();
        }

        public void submit() {
            onView(withId(R.id.button_next)).perform(
                    scrollTo(),
                    click());
        }

    }

    private static class InterestsPage {
        public InterestsPage() {
            /*
             * Another common implementation is to have separate assertDisplayed method
             * but it is easy to forget calling it
             */
            onView(withId(R.id.listview_interests)).check(matches(isDisplayed()));
        }

        public InterestsPage selectInterests(@NonNull String ... interests) {
            for (String interest : interests) {
                onData(is(interest)).perform(click());
            }
            return this;
        }

        public PersonalInfoPage back() {
            pressBack();
            return new PersonalInfoPage();
        }

        public CredentialsPage submitAndExpectCredentialsPage() {
            submit();
            return new CredentialsPage();
        }

        public void submit() {
            onView(withId(R.id.button_next)).perform(click());
        }

    }

    private static class CredentialsPage {

        public CredentialsPage() {
            /*
             * Another common implementation is to have separate assertDisplayed method
             * but it is easy to forget calling it
             */
            onView(withId(R.id.autocompletetextview_email)).check(matches(isDisplayed()));
        }

        public CredentialsPage assertHasUsernameError(@NonNull String error) {
            onView(withId(R.id.autocompletetextview_email)).check(matches(hasErrorText(error)));
            return this;
        }

        public CredentialsPage username(@NonNull String username) {
            onView(withId(R.id.autocompletetextview_email)).perform(
                    scrollTo(),
                    typeText(username));
            return this;
        }

        public CredentialsPage assertHasPasswordError(@NonNull String error) {
            onView(withId(R.id.edittext_password)).check(matches(hasErrorText(error)));
            return this;
        }

        public CredentialsPage password(@NonNull String password) {
            onView(withId(R.id.edittext_password)).perform(
                    scrollTo(),
                    typeText(password));
            return this;
        }

        public InterestsPage back() {
            pressBack();
            return new InterestsPage();
        }

        public void submitAndExpectCredentialsPage() {
            onView(withId(R.id.button_next)).perform(
                    scrollTo(),
                    click());
        }
    }
}
