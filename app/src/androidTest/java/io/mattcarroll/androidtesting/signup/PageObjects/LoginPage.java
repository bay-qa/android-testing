package io.mattcarroll.androidtesting.signup.PageObjects;

import android.support.annotation.NonNull;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
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

/**
 * Created by abzalbek on 9/14/17.
 */

public class LoginPage {
    public LoginPage() {

        onView(withId(R.id.button_sign_in)).check(matches(isDisplayed()));
    }

    public LoginPage fillEmail(String email) {
        onView(withId(R.id.edittext_email))
                .perform(
                        scrollTo(),
                        typeText(email)
                );
        return this;
    }

    public LoginPage fillPassword(String password) {
        onView(withId(R.id.edittext_password))
                .perform(
                        scrollTo(),
                        typeText(password)
                );
        return this;
    }

    public LoginPage assertHasEmailError(@NonNull String error){
        onView(withId(R.id.edittext_email))
                .check(matches(hasErrorText(error)));
        return this;
    }

    public LoginPage assertHasPasswordError(@NonNull String error){
        onView(withId(R.id.edittext_password))
                .check(matches(hasErrorText(error)));
        return this;
    }

    public void goBack(){
        closeSoftKeyboard();
        pressBack();
    }

    public void clickSignIN(){
        onView(withId(R.id.button_sign_in))
                .perform(
                        click()
                );
    }
    public HomeActivityPage submit(){
        clickSignIN();
        return new HomeActivityPage();
    }


}
