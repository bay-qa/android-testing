//package io.mattcarroll.androidtesting.signin;
//
//import android.content.res.Resources;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.support.test.rule.ActivityTestRule;
//
//import io.mattcarroll.androidtesting.R;
//import io.mattcarroll.androidtesting.login.LoginActivity;
//import io.mattcarroll.androidtesting.signup.SignUpActivity;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.scrollTo;
//import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//
//
///**
// * Created by sodintsov on 8/30/17.
// */
//
//public class EspressoSignInTest {
//    @Rule
//    public final ActivityTestRule<LoginActivity> activityRule2 = new ActivityTestRule<>(LoginActivity.class, false, true);
//
//    private Resources resources;
//
//    @Before
//    public void setup(){
//        resources = InstrumentationRegistry.getTargetContext().getResources();
//    }
//
//    @Test
//    public void userSignInVerifyRequiredFieldsAreRequired(){
//        onView(ViewMatchers.withId(R.id.button_sign_in))
//                .perform(click());
//        onView(withId(R.id.edittext_email))
//                .check(matches(hasErrorText(resources.getString(R.string.input_error_required))));
//        }
//}
