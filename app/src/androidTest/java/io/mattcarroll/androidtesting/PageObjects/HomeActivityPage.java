package io.mattcarroll.androidtesting.PageObjects;


import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class HomeActivityPage{

    public HomeActivityPage() {

        onView(withId(R.id.fab_manage_accounts))
                .check(matches(isDisplayed()));
    }

}
