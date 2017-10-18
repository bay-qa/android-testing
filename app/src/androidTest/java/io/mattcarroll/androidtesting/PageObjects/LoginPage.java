package io.mattcarroll.androidtesting.PageObjects;


import android.support.annotation.NonNull;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.home.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginPage extends BaseTest{
    public LoginPage(){
        onView(withId(R.id.edittext_email))
                .check(matches(isDisplayed()));
    }
    public LoginPage email(@NonNull String email) {
        onView(withId(R.id.edittext_email))
                .perform(
                        scrollTo(),
                        typeText(email)
                );
        return this;
    }
    public LoginPage password(@NonNull String password) {
        onView(withId(R.id.edittext_password))
                .perform(
                        scrollTo(),
                        typeText(password)
                );
        return this;
    }
    public void signin() {
        onView(withId(R.id.button_sign_in))
                .perform(
                        scrollTo(),
                        click()
                );
    }
    public HomeActivityPage signinAndExpectHomeActivityPage() {
        signin();
        return new HomeActivityPage();
    }
    public LoginPage assertHasEmailError(@NonNull String error){
        onView(withId(R.id.edittext_email))
                .check(matches(hasErrorText(error)));
        return this;
    }


}
