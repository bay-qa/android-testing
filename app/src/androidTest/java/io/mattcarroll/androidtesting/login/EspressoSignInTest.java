package io.mattcarroll.androidtesting.login;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspressoSignInTest extends BaseTest{

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule=
            new ActivityTestRule<>(SplashActivity.class, false, true);
    private Resources resources;

       @Before
    public void setup() {
           // getTargetContext() operates on the application under test
           // getContext() operates on the test APK context
           resources = InstrumentationRegistry.getTargetContext().getResources();
       }

    private static void fillFields(int fieldId, String textToType){
        onView(withId(fieldId)).perform(
                typeText(textToType)
        );
    }

    private void checkFieldHasError(int fieldId, int errorID){
        onView(withId(fieldId))
                .check(matches(hasErrorText(resources.getString(errorID))));
    }

    private static void scrollToButton(int fieldId){
        onView(withId(fieldId)).perform(scrollTo());

    }

    private static void clickButton(int fieldId){
        onView(withId(fieldId)).perform(click());
    }


    //@Before
    public void SignIn(){
        //fill email
        fillFields(R.id.edittext_email, getProperties().getProperty("email"));

        //fill password
        fillFields(R.id.edittext_password, getProperties().getProperty("passwordForLogin"));

        //scroll to sign in button
        scrollToButton(R.id.button_sign_in);

        //click on sign in button
        clickButton(R.id.button_sign_in);
    }

    @Test
    public void logInAndBankAccount(){

        SignIn();
        //click on + button
        clickButton(R.id.fab_manage_accounts);

        //click on link button
        clickButton(R.id.button_link_account);

        //fill bank name
        fillFields(R.id.edittext_bank_name, getProperties().getProperty("bankName"));

        //fill bank account number
        fillFields(R.id.edittext_account_number, getProperties().getProperty("accountNumber"));

        //fill password for account
        fillFields(R.id.edittext_password, getProperties().getProperty("passwordForBankAccount"));

        //click link button
        clickButton(R.id.button_link_account);

    }
    @Test
    public void userSignInPersonalInfoRequiredFieldsAreRequired(){

        scrollToButton(R.id.button_sign_in);
        clickButton(R.id.button_sign_in);
        checkFieldHasError(R.id.edittext_email, R.string.error_field_required);


    }
}
