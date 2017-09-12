package io.mattcarroll.androidtesting.login;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.signup.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by borisgurtovyy on 8/30/17.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoLoginTest extends BaseTest {

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    @Test
    public void userLogsInsuccessfully() {
        onView(withId(R.id.edittext_email))
                .perform(typeText("borisbayqa@gmail.com"));
        onView(withId(R.id.edittext_password))
                .perform(typeText("password1235"));
        onView(withId(R.id.button_sign_in))
                .perform(scrollTo())
                .perform(click());
        onView(withId(R.id.textview_no_accounts))
                .check(matches(isDisplayed()));
    }

// Homework #4

    private static void scrollToAndFillLoginScreen(int fieldId, String textToTypeIn){
        onView(withId(fieldId))
                .perform(scrollTo(), typeText(textToTypeIn));
    }

    private static void tapOnSignINButton(){
        onView(withId(R.id.button_sign_in))
                .perform(scrollTo(), click());
    }

    private static void tapOnFloatingActionButton() throws Exception {
        onView(withId(R.id.fab_manage_accounts))
                .perform(click());
        onView(withText("HomeActivity"))
                .check(matches(isDisplayed()));

        //        ViewInteraction floatingActionButton = onView(
//                allOf(withId(R.id.fab_manage_accounts), isDisplayed()));
//        floatingActionButton.perform(click());
    }

    private static void tapOnLinkAccountButton() throws Exception {
        onView(withId(R.id.button_link_account))
                .perform(scrollTo(), click());
        onView(withText("Android Testing"))
                .check(matches(isDisplayed()));

        //        ViewInteraction appCompatButton5 = onView(
//                allOf(withId(R.id.button_link_account), withText("Link Account"), isDisplayed()));
//        appCompatButton5.perform(click());
    }

    private static void createBankingAccount(int properId, String properValue){
        onView(withId(properId))
                .perform(typeText(properValue));
    }

    @Test

    public void fillOutAndloginAndAddBankAccount() throws Exception {
        scrollToAndFillLoginScreen(R.id.edittext_email, getProperties().getProperty("email"));
        scrollToAndFillLoginScreen(R.id.edittext_password, getProperties().getProperty("password"));
        tapOnSignINButton();
        tapOnFloatingActionButton();
        tapOnLinkAccountButton();
        createBankingAccount(R.id.edittext_bank_name, getProperties().getProperty("bank"));
        createBankingAccount(R.id.edittext_account_number, getProperties().getProperty("account"));
        createBankingAccount(R.id.edittext_password, getProperties().getProperty("passcode"));
        tapOnLinkAccountButton();
    }

}