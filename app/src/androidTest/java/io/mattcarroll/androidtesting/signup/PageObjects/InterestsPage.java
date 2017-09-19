package io.mattcarroll.androidtesting.signup.PageObjects;


import android.support.annotation.NonNull;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

/**
 * Created by abzalbek on 9/14/17.
 */

public class InterestsPage {
    public InterestsPage() {

        onView(withId(R.id.listview_interests)).check(matches(isDisplayed()));
    }
        // very important!!!
    public  InterestsPage clickOnView(@NonNull String ...interests) {
        for (String interest:interests){
            onData(is(interest)).perform(click());
        }
        return this;
    }
    // that one for checking
    public static void scrollingListView(String itemName) {
        onData(allOf(is(instanceOf(String.class)), is(itemName))).perform(
                scrollTo()
        );
    }

    public void back() {
        pressBack();
    }

    public void pressNext(){
        onView(withId(R.id.button_next)).perform(
                click());
    }

    public CredentialsPage submitAndExpectCredendialPage(){
        pressNext();
        return new CredentialsPage();
    }

    public void checkingPopUP(){
        onView(withId(R.id.title_template)).check(matches(isDisplayed()));
    }


}
