package io.mattcarroll.androidtesting.signup.PageObjects;

import android.support.annotation.NonNull;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 9/18/17.
 */

public class CredentialsPage {

    public CredentialsPage() {
        onView(withId(R.id.autocompletetextview_email)).check(matches(isDisplayed()));
    }

    public CredentialsPage typeTextToEmailField(String email){
        onView(withId(R.id.autocompletetextview_email)).perform(typeText(email));
        return this;
    }

    public CredentialsPage typeTextToPasswordField(String passwrod){
        onView(withId(R.id.edittext_password)).perform(typeText(passwrod));
        return this;
    }

    public CredentialsPage assertHasEmailError(@NonNull String error){
        onView(withId(R.id.autocompletetextview_email))
                .check(matches(hasErrorText(error)));
        return this;
    }

    public CredentialsPage assertHasPasswordError(@NonNull String error){
        onView(withId(R.id.edittext_password))
                .check(matches(hasErrorText(error)));
        return this;
    }

    public void goBack(){
        closeSoftKeyboard();
        pressBack();
    }
    //button_next
    public void clickOnSignUPButton(){
        onView(withId(R.id.button_next))
                .perform(click());
    }

    public SignUPSuccesFullPage submitAndSuccess(){
        clickOnSignUPButton();
        return new SignUPSuccesFullPage();
    }

}
