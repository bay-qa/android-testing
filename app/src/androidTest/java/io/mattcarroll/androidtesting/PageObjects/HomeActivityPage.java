package io.mattcarroll.androidtesting.PageObjects;


import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;

import io.mattcarroll.androidtesting.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class HomeActivityPage{

    public HomeActivityPage() {

        onView(withId(R.id.fab_manage_accounts))
                .check(matches(isDisplayed()));
    }

    //click "+"
    public HomeActivityPage clickOnItemPlus (){
        onView(withId(R.id.fab_manage_accounts))
                .perform(click());
        return this;
    }

    //click "link account"
    public HomeActivityPage clickOnItemLinkAccount (){
        onView(withId(R.id.button_link_account))
                .perform(click());
        return this;
    }

    //type bank name
    public HomeActivityPage bankName(@NonNull String bankName){
        onView(withId(R.id.edittext_bank_name))
                .perform(typeText(bankName));
        return this;
    }

    //type account number name
    public HomeActivityPage accountNumber(@NonNull String accountNumber){
        onView(withId(R.id.edittext_account_number))
                .perform(typeText(accountNumber));
        return this;
    }

    //type bank password
    public HomeActivityPage bankPassword(@NonNull String bankPassword){
        onView(withId(R.id.edittext_password))
                .perform(typeText(bankPassword));
        return this;
    }

    public void goBackToOverViewScreen() {
        pressBack();
    }

    public HomeActivityPage verifyLastFourDigits(String accountNumber){
        String accountNumberLastFourDigits = accountNumber.substring(accountNumber.length() - 4, accountNumber.length());

        onView(withId(R.id.recyclerview_accounts))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(accountNumberLastFourDigits))))
                .check(matches(allOf(hasDescendant(allOf(withId(R.id.textview_account_last_digits),
                        withText(accountNumberLastFourDigits))))));
        return this;
    }
}
