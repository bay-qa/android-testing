package io.mattcarroll.androidtesting.signup.PageObjects;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 9/18/17.
 */

public class ListLinkAccountPage {
    public ListLinkAccountPage() {
        onView(withId(R.id.button_link_account)).check(matches(isDisplayed()));
    }

    public BankAccountPage linkAccountButton(){
        onView(withId(R.id.button_link_account))
                .perform(click());
        return new BankAccountPage();
    }

}
