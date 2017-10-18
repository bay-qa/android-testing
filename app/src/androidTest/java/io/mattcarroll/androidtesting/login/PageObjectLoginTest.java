package io.mattcarroll.androidtesting.login;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.PageObjects.LoginPage;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.signup.SignUpActivity;
import io.mattcarroll.androidtesting.usersession.UserSession;

public class PageObjectLoginTest extends BaseTest{
    @Rule
    public final ActivityTestRule<SplashActivity> activityRule =
            new ActivityTestRule<>(SplashActivity.class, false, true);

    private Resources resources;

    @Before
    public void setup() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    @After
    public void teardown() {
        UserSession.getInstance().logout();
    }

    @Test
    public void userLogsInSuccessfullyPO(){
        new LoginPage()
                .email(getProperties().getProperty("email_login"))
                .password(getProperties().getProperty("password_login"))
                .signinAndExpectHomeActivityPage();
    }
}
