package io.mattcarroll.androidtesting.signup.PageObjects;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 9/18/17.
 */

public class LinkAccountPage {
    public LinkAccountPage() {
        onView(withId(R.id.fab_manage_accounts))
                .check(matches(isDisplayed()));
    }

    public void goBack() {
        closeSoftKeyboard();
        pressBack();
    }

    public ListLinkAccountPage addButton(){
        onView(withId(R.id.fab_manage_accounts))
                .perform(click());
        return new ListLinkAccountPage();
    }

}





