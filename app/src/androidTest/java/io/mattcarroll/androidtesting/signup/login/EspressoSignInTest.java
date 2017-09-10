package io.mattcarroll.androidtesting.signup.login;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.signup.BaseTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 8/31/17.
 */

public class EspressoSignInTest extends BaseTest {

    @Rule

    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    @Test
    public void usersSignInPersonalInfoRequiredFieldAreFilled(){
        onView(withId(R.id.edittext_email))
                .perform(typeText("bayQATraining@email.com"));
        onView(withId(R.id.edittext_password))
                .perform(typeText("Password2017"));
        onView(withId(R.id.button_sign_in)).perform(click());
        onView(withId(R.id.textview_no_accounts)).check(matches(isDisplayed()));

    }

    /*----------------HOMEWORK----------------*/

    private static void typeTextOnfield(int fieldID, String text){
        onView(withId(fieldID)).perform(
                typeText(text)
        );
    }

    private static void scrollAndclickOn(int fieldID){
        onView(withId(fieldID)).perform(
                scrollTo(),
                click()
        );
    }

    private static void clickOn(int fieldID){
        onView(withId(fieldID)).perform(
                click()
        );
    }

    private static void checkTextOnView(int text){
        onView(withId(text)).check(matches(isDisplayed()));
    }

    @Before
    public void logIn(){
        // filling email
        typeTextOnfield(R.id.edittext_email,getProperties().getProperty("email"));

        //filliing password
        typeTextOnfield(R.id.edittext_password, getProperties().getProperty("passwordForLogin"));

        //clicking
        scrollAndclickOn(R.id.button_sign_in);

    }
    @Test

    public void addingBankAccount(){

        // click on +
        clickOn(R.id.fab_manage_accounts);

        //click "link"
        clickOn(R.id.button_link_account);

        // filling bank name
        typeTextOnfield(R.id.edittext_bank_name,getProperties().getProperty("bankName"));

        //filling bank account number
        typeTextOnfield(R.id.edittext_account_number,getProperties().getProperty("accountNumber"));

        // filling passwrod for bank account
        typeTextOnfield(R.id.edittext_password,getProperties().getProperty("passwordForBankAccount"));

        //click linkAccount
        clickOn(R.id.button_link_account);

    }


}
