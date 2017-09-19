package io.mattcarroll.androidtesting.signup;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.PageObjects.CredentialsPage;
import io.mattcarroll.androidtesting.PageObjects.InterestsPage;
import io.mattcarroll.androidtesting.PageObjects.PersonalInfoPage;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.usersession.UserSession;

/**
 * Created by borisgurtovyy on 9/14/17.
 */

public class PageObjectSignUpTest extends BaseTest{

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
    public void userSignUpHappyPathPO() {
                new PersonalInfoPage()
                .firstName(getProperties().getProperty("name"))
                .lastname(getProperties().getProperty("last_name"))
                .address1(getProperties().getProperty("address1"))
                .city(getProperties().getProperty("city"))
                .state(getProperties().getProperty("state"))
                .zipcode(getProperties().getProperty("zip"))
                .submitAndExpectInteresrsPage();
    }

    @Test
    public void userSignUpPersonalInfoVerifyRequiredFieldsAreRequiredPO() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        PersonalInfoPage personalInfoPage = new PersonalInfoPage();

        personalInfoPage.submit();

        personalInfoPage.assertHasFirstNameError(REQUIRED_FIELD_ERROR)
                        .assertHasLastNameError(REQUIRED_FIELD_ERROR);

    }

    @Test
    public void userSignUpCredentialsVerifyRequiredFieldsAreRequiredPO() {
        final String REQUIRED_FIELD_ERROR = resources.getString(R.string.input_error_required);

        CredentialsPage credentialsPage = new PersonalInfoPage()
                .firstName(getProperties().getProperty("name"))
                .lastname(getProperties().getProperty("last_name"))
                .address1(getProperties().getProperty("address1"))
                .city(getProperties().getProperty("city"))
                .state(getProperties().getProperty("state"))
                .zipcode(getProperties().getProperty("zip"))
                .submitAndExpectInteresrsPage()
                .selctInterests("Football", "Alcohol")
                .submit();

        credentialsPage.submit();
        credentialsPage.assertHasUsernameError(REQUIRED_FIELD_ERROR);



    }

    @Test
    public void checkAstronomyPO() {
        PersonalInfoPage personalInfoPage = new PersonalInfoPage()
                    .firstName(getProperties().getProperty("name"))
                    .lastname(getProperties().getProperty("last_name"))
                    .address1(getProperties().getProperty("address1"))
                    .city(getProperties().getProperty("city"))
                    .state(getProperties().getProperty("state"))
                    .zipcode(getProperties().getProperty("zip"));

        InterestsPage interestsPage = personalInfoPage.submitAndExpectInteresrsPage();

        interestsPage.selctInterests("Astronomy");
        interestsPage.verifyViewIsChecked("Astronomy");

        CredentialsPage credentialsPage = interestsPage.submit();

        InterestsPage interestsPage1 = credentialsPage.pressBackButton();

        interestsPage1.scrollToViewOnTheListView("Astronomy");
        interestsPage1.verifyViewIsNotChecked("Astronomy");

    }




}
