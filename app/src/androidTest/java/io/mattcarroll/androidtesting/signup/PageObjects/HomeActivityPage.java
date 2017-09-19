package io.mattcarroll.androidtesting.signup.PageObjects;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 9/14/17.
 */

public class HomeActivityPage {

    public HomeActivityPage() {

        onView(withId(R.id.textview_no_accounts)).check(matches(isDisplayed()));
    }
}
