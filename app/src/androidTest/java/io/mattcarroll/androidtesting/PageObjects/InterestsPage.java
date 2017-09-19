package io.mattcarroll.androidtesting.PageObjects;

import android.support.annotation.NonNull;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

/**
 * Created by borisgurtovyy on 9/11/17.
 */

public class InterestsPage {

    public InterestsPage() {

        onView(withId(R.id.button_next))
                .check(matches(isDisplayed()));
    }

    public InterestsPage selctInterests(@NonNull String ... interests){
        for (String interest : interests) {
            onData(is(interest)).perform(click());
        }
        return this;
    }

    public CredentialsPage submit() {
        onView(withId(R.id.button_next))
                .perform(click());
        return new CredentialsPage();
    }

    public void verifyViewIsChecked(String viewName) {
        onView(withText(viewName))
                .check(matches(isChecked()));
    }


    public void verifyViewIsNotChecked(String viewName) {
        onView(withText(viewName))
                .check(matches(isNotChecked()));
    }

    public void scrollToViewOnTheListView(String viewName) {
        onData(hasToString(viewName))
                .perform(scrollTo());
    }

}
