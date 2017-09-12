package io.mattcarroll.androidtesting.login;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by max on 9/4/2017.
 */

public class EspressoSignInTest extends BaseTest{

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false,true);

    @Test
    public void userSignInPersonalInfoRequiredFieldsAreRequired(){

        onView(withId(R.id.edittext_email))
                .perform(typeText("BayQA@yahoo.com"));
        onView(withId(R.id.edittext_password))
                .perform(typeText("password"));
        onView(withId(R.id.button_sign_in))
                .perform(scrollTo())
                .perform(click());
        onView(withId(R.id.textview_no_accounts))
                .check(matches(isDisplayed()));
    }

    //homework lesson 4

    private static void fillFields(int fieldId, String textToType){
        onView(withId(fieldId)).perform(
                typeText(textToType)
        );
    }

    private static void scrollToButton(int fieldId){
        onView(withId(fieldId)).perform(
                scrollTo()
        );
    }

    private static void clickButton(int fieldId){
        onView(withId(fieldId)).perform(
                click()
        );
    }

    private static void checkTextIsDisplayed(String textToCheck) {
        onView(withText(textToCheck)).check(matches(isDisplayed())
        );
    }

    @Before
    public void SignIn(){
        //fill email
        fillFields(R.id.edittext_email, getProperties().getProperty("email"));

        //fill password
        fillFields(R.id.edittext_password, getProperties().getProperty("password"));

        //scroll to sign in button
        scrollToButton(R.id.button_sign_in);

        //click on sign in button
        clickButton(R.id.button_sign_in);
    }


    @Test
    public void logInAndAddBankAccount(){


        //click on + button
        clickButton(R.id.fab_manage_accounts);

        //click on link button
        clickButton(R.id.button_link_account);

        //fill bank name
        fillFields(R.id.edittext_bank_name, getProperties().getProperty("bankName"));

        //fill bank account number
        fillFields(R.id.edittext_account_number, getProperties().getProperty("accountNumber"));

        //fill password for account
        fillFields(R.id.edittext_password, getProperties().getProperty("passwordForAccount"));

        // click link button
        clickButton(R.id.button_link_account);

    }


}
