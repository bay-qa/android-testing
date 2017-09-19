package io.mattcarroll.androidtesting.signup.PageObjects;

import android.support.annotation.NonNull;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbek on 9/18/17.
 */

public class BankAccountPage {
    public BankAccountPage() {
        onView(withId(R.id.edittext_bank_name))
                .check(matches(isDisplayed()));
    }

    public BankAccountPage fillBankName(@NonNull String bankName){
        onView(withId(R.id.edittext_bank_name))
                .perform(typeText(bankName));
        return this;
    }

    public BankAccountPage fillAccountNumber(@NonNull String accountNumber){
        onView(withId(R.id.edittext_account_number))
                .perform(typeText(accountNumber));
        return this;
    }
    public BankAccountPage fillPassword(@NonNull String password){
        onView(withId(R.id.edittext_password))
                .perform(typeText(password));
        return this;
    }

    public void goBack(){
        closeSoftKeyboard();
        pressBack();
    }
    public void pressLinkAccountButton(){
        onView(withId(R.id.button_link_account))
                .perform(click());
    }

    // decided to skip assertation of error!!!!
}
