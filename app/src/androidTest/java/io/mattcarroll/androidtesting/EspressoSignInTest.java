package io.mattcarroll.androidtesting;

import io.mattcarroll.androidtesting.login.LoginActivity;

/**
 * Created by sodintsov on 8/30/17.
 */

public class EspressoSignInTest {

    @Rule
    public final ActivityTestRule <LoginActivity> ActivityRule = new ActivityTestRule<>(LoginActivity.class, false, true);
}
