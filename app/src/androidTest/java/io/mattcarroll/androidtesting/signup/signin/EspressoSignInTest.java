package io.mattcarroll.androidtesting.signup.signin;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by olenakupina on 9/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class EspressoSignInTest {

    @Rule
    public final ActivityTestRule<SplashActivity> activityRule = new ActivityTestRule<>(SplashActivity.class, false, true);

    private Resources resources;

    @Before
    public void setup() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    @Test

    public void userSignInTest() {



        onView(withId(R.id.edittext_email))
                .perform(typeText("kupina09011982@gmail.com"));


        onView(withId(R.id.edittext_password))
                .perform(typeText("test0013"));

        onView(withId(R.id.button_sign_in))
                .perform(click());

        onView(withId(R.id.textview_no_accounts))
                .check(matches(isDisplayed()));




    }


    }

