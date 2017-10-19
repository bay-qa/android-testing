package io.mattcarroll.androidtesting.accounts;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.mattcarroll.androidtesting.BaseTest;
import io.mattcarroll.androidtesting.PageObjects.HomeActivityPage;
import io.mattcarroll.androidtesting.PageObjects.LoginPage;
import io.mattcarroll.androidtesting.R;
import io.mattcarroll.androidtesting.SplashActivity;
import io.mattcarroll.androidtesting.home.HomeActivity;
import io.mattcarroll.androidtesting.usersession.UserSession;

public class PageObjectLinkAccountTest extends BaseTest{

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
    public void linkinBankAccountPO(){
        HomeActivityPage homeActivityPage=new LoginPage()
                .email(getProperties().getProperty("email_login"))
                .password(getProperties().getProperty("password_login"))
                .signinAndExpectHomeActivityPage()
                .clickOnItemPlus()
                .clickOnItemLinkAccount()
                .bankName(getProperties().getProperty("bank_name"))
                .accountNumber(getProperties().getProperty("account_number"))
                .bankPassword(getProperties().getProperty("password_bank"))
                .clickOnItemLinkAccount();
        homeActivityPage.goBackToOverViewScreen();
                        new HomeActivityPage()
                 .verifyLastFourDigits(getProperties().getProperty("account_number"));


    }


}
