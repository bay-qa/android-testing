package io.mattcarroll.androidtesting.signup.signUp;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.signup.BaseTest;
import io.mattcarroll.androidtesting.signup.PageObjects.CredentialsPage;
import io.mattcarroll.androidtesting.signup.PageObjects.InterestsPage;
import io.mattcarroll.androidtesting.signup.PageObjects.PersonalInfoPage;
import io.mattcarroll.androidtesting.signup.SignUpActivity;
import io.mattcarroll.androidtesting.usersession.UserSession;

/**
 * Created by abzalbek on 9/18/17.
 */

public class PageObjectSignUpTest extends BaseTest {

    @Rule
    public final ActivityTestRule<SignUpActivity> activityRule
            = new ActivityTestRule<>(SignUpActivity.class, false, true);

    private Resources resources;


    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    @After
    public void tearDown() {
        UserSession.getInstance().logout();
    }

    //positive test
    @Test
    public void userSignUpHappyPathPO() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        // filling of field by using pageObjects
        new PersonalInfoPage()
                .firstName(getProperties().getProperty("name"))
                .lastName(getProperties().getProperty("lastName"))
                .address1(getProperties().getProperty("street1"))
                .city(getProperties().getProperty("city"))
                .state(getProperties().getProperty("state"))
                .zipcode(getProperties().getProperty("zip"))
                .submitAndExpectInterestPage();

        // next page
        new InterestsPage()
                .clickOnView("Snowboarding")
                .clickOnView("Chess")
                .clickOnView("Astronomy")
                .submitAndExpectCredendialPage();

        // next page
        CredentialsPage credentialsPage = new CredentialsPage();

        credentialsPage.clickOnSignUPButton();
        credentialsPage
                .assertHasEmailError(REQUIRED_FIELD_ERROR)
                .assertHasPasswordError(REQUIRED_FIELD_ERROR);

    }

    //negative test
    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequiredPO() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        // creating constructor
        PersonalInfoPage personalInfoPage = new PersonalInfoPage();
        // pressing "next" button
        personalInfoPage.submit();
        // assertation of requred field(error)
        personalInfoPage
                .assertHasFirstNameError(REQUIRED_FIELD_ERROR)
                .assertHasLastNameError(REQUIRED_FIELD_ERROR)
                .assertHasAddressError(REQUIRED_FIELD_ERROR)
                .assertHasCityError(REQUIRED_FIELD_ERROR)
                .assertHasStateError(REQUIRED_FIELD_ERROR)
                .assertHasZipCodeError(REQUIRED_FIELD_ERROR);

    }

    // way to get all steps from beginning (positive test)
    @Test
    public void userSignUpHappyPath2PO() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        // filling of field by using pageObjects
        new PersonalInfoPage()
                .firstName(getProperties().getProperty("name"))
                .lastName(getProperties().getProperty("lastName"))
                .address1(getProperties().getProperty("street1"))
                .city(getProperties().getProperty("city"))
                .state(getProperties().getProperty("state"))
                .zipcode(getProperties().getProperty("zip"))
                .submitAndExpectInterestPage();

        new InterestsPage()
                .clickOnView("Snowboarding", "Astronomy")
                .submitAndExpectCredendialPage();

        new CredentialsPage()
                .typeTextToEmailField(getProperties().getProperty("email"))
                .typeTextToPasswordField(getProperties().getProperty("passwordForLogin"))
                .submitAndSuccess();
    }

}
