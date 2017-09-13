package io.mattcarroll.androidtesting.login;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspressoSignInTest extends BaseTest {

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    @Test
    public void userSignInVerifyNoLinkedAccounts() {
        signIn();
        onView(withId(R.id.textview_no_accounts))
                .check(matches(isDisplayed()));
    }

    @Test
    public void linkBankAccount() {
        signIn();
        //user click + sign
        clickAddButton();
        //user click Link Account
        linkButtonClik();
        //user types in Bank name, Account Number, and Password
        linkNewBankAccount();
        //user clicks Link
        linkButtonClik();
    }

    private void clickAddButton() {
        onView(withId(R.id.fab_manage_accounts))
                .perform(click());
    }

    private void linkButtonClik() {
        onView(withId(R.id.button_link_account))
                .perform(click());
    }

    private void linkNewBankAccount() {
        onView(withId(R.id.edittext_bank_name))
                .perform(typeText(getProperties().getProperty("bankName")));
        onView(withId(R.id.edittext_account_number))
                .perform(typeText(getProperties().getProperty("accountNumber")));
        onView(withId(R.id.edittext_password))
                .perform(typeText(getProperties().getProperty("password")));
    }

    private void signIn() {
        onView(ViewMatchers.withId(R.id.edittext_email))
                .perform(typeText(getProperties().getProperty("email")));
        onView(withId(R.id.edittext_password))
                .perform(typeText(getProperties().getProperty("password")));
        onView(withId(R.id.button_sign_in))
                .perform(click());
    }
}