package io.mattcarroll.androidtesting;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckedTextView;

import org.hamcrest.Matcher;

import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.allOf;

public class CustomViewActions {
    public static ViewAction setChecked(boolean checked) {
        // run global assertions set by ViewActions#addGlobalAssertion
        return actionWithAssertions(new SetCheckedTextViewAction(checked));
    }

    public static class SetCheckedTextViewAction implements ViewAction {
        private final boolean checked;

        private final ViewAction generalClickAction =
                // Arguments used by Espresso for click() implementation, see:
                // https://android.googlesource.com/platform/frameworks/testing/+/android-support-test/espresso/core/src/main/java/android/support/test/espresso/action/ViewActions.java#160
                // https://developer.android.com/reference/android/support/test/espresso/action/ViewActions.html#click()
                new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER,
                        InputDevice.SOURCE_UNKNOWN, MotionEvent.BUTTON_PRIMARY);

        public SetCheckedTextViewAction(boolean checked) {
            this.checked = checked;
        }

        @Override
        public Matcher<View> getConstraints() {
            return allOf(
                    // We can operate only on CheckedTextView
                    isAssignableFrom(CheckedTextView.class),

                    // Also require constraints from the class that do the actual work
                    generalClickAction.getConstraints()
            );
        }

        @Override
        public String getDescription() {
            return (checked ? "checking" : "unchecking") + " a CheckedTextView";
        }

        @Override
        public void perform(UiController uiController, View view) {
            // Use this method instead of Thread.sleep in ViewAction implementations
            // uiController.loopMainThreadForAtLeast(5000);

            // Thanks to getConstraints we can be sure that view is a CheckedTextView
            CheckedTextView checkedTextView = (CheckedTextView) view;
            if (checked != checkedTextView.isChecked()) {
                generalClickAction.perform(uiController, view);
            }
        }
    }
}
