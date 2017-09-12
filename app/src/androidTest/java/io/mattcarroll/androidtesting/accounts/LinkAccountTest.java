package io.mattcarroll.androidtesting.accounts;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by borisgurtovyy on 9/11/17.
 */

public class LinkAccountTest extends BaseTest {


    @Rule

    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    private static void clickOnItem(int fieldID){
        onView(withId(fieldID))
                .perform(click());
    }

    private static void typeTextIntoTextField(int fieldID, String text){
        onView(withId(fieldID))
                .perform(typeText(text));
    }

    private static void scrollAndclickOnItem(int fieldID){
        onView(withId(fieldID)).perform(
                scrollTo(),
                click()
        );
    }


    public void login() {
        typeTextIntoTextField(R.id.edittext_email, getProperties().getProperty("email_login") );
        typeTextIntoTextField(R.id.edittext_password, getProperties().getProperty("password_login") );
        scrollAndclickOnItem(R.id.button_sign_in);
    }

    private  void goBackToOverViewScreen() {
        pressBack();
    }

    public void verifyLastFourDigits(){
        String accountNumber = getProperties().getProperty("account_number");
        String accountNumberLastFourDigits = accountNumber.substring(accountNumber.length() - 4,accountNumber.length() );

        onView(withId(R.id.recyclerview_accounts))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(accountNumberLastFourDigits))))
                .check(matches(allOf(
                        hasDescendant(allOf(
                                withId(R.id.textview_account_last_digits),
                                withText(accountNumberLastFourDigits))))));
    }

    @Test
    public void linkingBankAccount() {
        // login
        login();

        // click "+"
        clickOnItem(R.id.fab_manage_accounts);

        // click "link account"
        clickOnItem(R.id.button_link_account);

        // type bank name
        typeTextIntoTextField(R.id.edittext_bank_name, getProperties().getProperty("bank_name"));

        // type account number
        typeTextIntoTextField(R.id.edittext_account_number, getProperties().getProperty("account_number"));

        // type password
        typeTextIntoTextField(R.id.edittext_password, getProperties().getProperty("password_bank"));

        // click "link account"
        clickOnItem(R.id.button_link_account);

        // press back button
        goBackToOverViewScreen();

        verifyLastFourDigits();
    }



}
