package io.mattcarroll.androidtesting.signup.accounts;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.signup.BaseTest;
import io.mattcarroll.androidtesting.signup.PageObjects.BankAccountPage;
import io.mattcarroll.androidtesting.signup.PageObjects.LinkAccountPage;
import io.mattcarroll.androidtesting.signup.PageObjects.ListLinkAccountPage;
import io.mattcarroll.androidtesting.signup.PageObjects.LoginPage;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
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
 * Created by abzalbek on 9/12/17.
 */

public class LinkAccountTest extends BaseTest {

    @Rule

    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<SplashActivity>(SplashActivity.class, false, true);

    public static void clickOnItem(int fieldID) {
        onView(withId(fieldID)).perform(
                click()
        );
    }

    public static void typeTextIntoTextField(int fieldID, String text) {
        onView(withId(fieldID))
                .perform(typeText(text));
    }

    public static void scrollAndClickOnItem(int filedID) {
        onView(withId(filedID)).perform(
                scrollTo(),
                click()
        );
    }

    public static void goToOverViewScreen() {
        closeSoftKeyboard();
        pressBack();
    }

    // verification on recycler view, very important!!!
    public void verifyLastFourDigits() {
        String accountNumber = getProperties().getProperty("accountNumber");
        // cutting string
        String acountNumberLastFourDigits = accountNumber.substring(accountNumber.length() - 4, accountNumber.length());

        // finding recycleView !!!  neeed to go over!!!
        onView(withId(R.id.recyclerview_accounts))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(acountNumberLastFourDigits))))
                .check(matches(allOf(
                        hasDescendant(allOf(
                                withId(R.id.textview_account_last_digits),
                                withText(acountNumberLastFourDigits))))));
    }

    public void login() {
        typeTextIntoTextField(R.id.edittext_email, getProperties().getProperty("email"));
        typeTextIntoTextField(R.id.edittext_password, getProperties().getProperty("passwordForLogin"));
        scrollAndClickOnItem(R.id.button_sign_in);
    }

    @Test
    public void linkingBankAccount() {

        login();

        //click "+"
        clickOnItem(R.id.fab_manage_accounts);

        //click "link account"
        clickOnItem(R.id.button_link_account);

        //type bank name
        typeTextIntoTextField(R.id.edittext_bank_name, getProperties().getProperty("bankName"));

        //type account number
        typeTextIntoTextField(R.id.edittext_account_number, getProperties().getProperty("accountNumber"));

        // type password
        typeTextIntoTextField(R.id.edittext_password, getProperties().getProperty("passwordForBankAccount"));

        //click "link account"
        clickOnItem(R.id.button_link_account);

        //pressBack
        goToOverViewScreen();
        //sdghrhh

        // verification last 4 digits of account number
        verifyLastFourDigits();
    }

    @Test
    public void linkAccountTextPO() {

        new LoginPage()
                .fillEmail(getProperties().getProperty("email"))
                .fillPassword(getProperties().getProperty("passwordForLogin"))
                .submit();

        new LinkAccountPage()
                .addButton();

        new ListLinkAccountPage()
                .linkAccountButton();

        BankAccountPage bankAccountPage = new BankAccountPage()
                .fillBankName(getProperties().getProperty("bankName"))
                .fillAccountNumber(getProperties().getProperty("accountNumber"))
                .fillPassword(getProperties().getProperty("passwordForBankAccount"));

        bankAccountPage.pressLinkAccountButton();


    }

}
