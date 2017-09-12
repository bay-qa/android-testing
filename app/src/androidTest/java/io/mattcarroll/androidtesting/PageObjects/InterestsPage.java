package io.mattcarroll.androidtesting.PageObjects;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by borisgurtovyy on 9/11/17.
 */

public class InterestsPage {

    public InterestsPage() {

        onView(withText("Snowboarding"))
                .check(matches(isDisplayed()));
    }
}
