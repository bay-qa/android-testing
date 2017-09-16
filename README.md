# Espresso steps
## 1. Configure Espresso dependencies
* Add repository and dependencies as described on
 https://developer.android.com/training/testing/espresso/setup.html
 https://developer.android.com/topic/libraries/testing-support-library/packages.html
* Force versions used in the app with `resolutionStrategy`
See:
 https://sites.google.com/a/android.com/tools/tech-docs/new-build-system/user-guide#TOC-Resolving-conflicts-between-main-and-test-APK
 https://docs.gradle.org/current/dsl/org.gradle.api.artifacts.ResolutionStrategy.html

* Define and use separate `espresso` build type based on debug (`testBuildType`, `buildTypes`)
* Switch to `espresso` build variant

## 2. Basic Espresso test
* Implement basic test with @RunWith, @Test, ActivityTestRule, basic actions and checks

## 3. Use string from resources
* Instrumented tests have access to AUT resources and objects
* InstrumentationRegistry has two similarly named methods:
  - getTargetContext() - returns the AUT context - use it operate on the app and its resources
  - getContext() - return the context of instrumentation package - use it access resources in test APK

## 4. Refactor duplicate code and introduce back button
* Move commonly used code to methods to avoid copy-paste and ease maintanance
* Caveats with Espress.pressBack():
  * Close soft keyboard before clicking it to make sure back button click is applied to an activity or fragment
  * Back button click on main activity will throw an exception (and AUT may be terminated at this time)

## 5. Introduce pressKey with complex keys
* Show that pressKey can be used both with simple keycode (KEYCODE_*) and complex keys via EspressKey.Builder class

## *. Access objects from the app
* Accessing objects from the app can be useful to reset AUT state

## *. Testcases for practice

===== Day 2
## 1. onData to select interests at the end of the list
* onData allows to select interests at the end of the list without failure
(ex. Football and Astronomy will failure with old implementation)

## 2. [Big] Espresso test with RecycleView, accessing app data, and complex matchers
* test data as final fields (some fields will be used in tests later)
* use build variants to use different files in normal run and under test
* launch the app from the beginning (SplashActivity)
 - Espresso won't do anything while animations are playing. But you will get an expection if
 animations are too long.
* private methods for more readable tests
* In previous commit we used onData to operate on lists. But unfortunately onData only works with
AdapterViews only and RecycleView is not an AdapterView.
Instead we should use RecyclerViewActions from espresso-contrib that has useful methods for working
 with RecycleViews. We use scrollTo(Matcher<View> itemViewMatcher) here.

## 3. Advanced interaction with AdapterView & custom Matcher
onData can be used with complex classes:
* implement custom matcher by inheriting it from BoundedMatcher. BounderMatcher already implements
checking for type of an object, so we can use matchesSafely(TypeWeInterestedIn) instead of matches(Object)
* use inAdapterView to make Espresso check one specific AdapterView
* use onChildView to work with specific child of the matched item view

===== Day 3
## 1. Verifying fired intent
Espresso Intents is important to isolate tests per activity. It allows to both verify that
 expected intents was fired when leaving activity and verify AUT response to receiving an intent
Documentation - https://developer.android.com/training/testing/espresso/intents.html
Advanced usage not covered here - https://github.com/googlesamples/android-testing/blob/master/ui/espresso/IntentsAdvancedSample/app/src/androidTest/java/com/example/android/testing/espresso/intents/AdvancedSample/ImageViewerActivityTest.java
* Replace ActivityTestRule with IntentsTestRule to enable Espresso Intents
* Use `intended` to verify fired intent
* Espress Intents has its own set of matchers to use against intents

## 2. Simulating receiving an intent
Use `intending` to stub response from other activities (launched with `startActivityForResult`)
 or to test AUT response to receiving an intent from another app

## 3. Sign Up tests implemented with Page Object pattern


===== Day 4
## 1. Add background routine that changes list we use in tests
This will break our tests in EspressoSingUpTest.
WARNING: This `Timer` implementation is only for IdlingResource demonstration. It is probably a bad
style to implement it this way.

## 2. Wait for routine using CountingIdleResource
Espresso waits for UI events in the current message queue to be handled, animations to complete
 (although animations can still fail you actions e.g. click(), so they still should be disabled in
 Developer settings of your Android device), and for default instances of AsyncTask to complete
 before it executes next test operation.
 In other cases like communication with your backend server Espresso needs your help to detect that
 background work is in progress and that it should wait for that work to complete.
 You do that by adding CountingIdlingResource (for simple cases) or implementing IdlingResource
  into the app (when CountingIdlingResource implementation does not suits you).

 In either case you should register instance of IdlingResource in Espresso registry using
 `IdlingRegistry.getInstance().register(idlingResource)` before background operation is started
 (probably during test setUp method)
 and unregister using `IdlingRegistry.getInstance().unregister(idlingResource)` when there is no
 longer background operation expected (probably during test tearDown method)

We will start with CountingIdlingResource. It is simple IdlingResource that wraps a thread-safe counter.
This resource is considered idle when counter is 0. And it is considered that some background
work is in progress when counter is greater than zero. The counter has value of 0 by default (idle).
CountingIdlingResource will throw IllegalStateException when the counter reaches a negative value.

NOTE: Espresso identifies IdlingResources by their names (returned by `getName()``). So make sure
they have unique names.
WARNING: Current Espresso version have a problem: `IdlingRegistry.getInstance().unregister`
 is not exactly unregister passed resource: when you "unregister" some resource and then add another
 resource with the same name, the former (unregistered) resource will still be used while the latter
 (registered) will be ignored.
Obvious workaround is to make all instances of some idling resource have unique names.

See https://developer.android.com/training/testing/espresso/idling-resource.html
    https://developer.android.com/reference/android/support/test/espresso/idling/CountingIdlingResource.html

* We will need IdlingResource in the AUT code, so change `espresso-idling-resource` dependency to
 compile
* Background work is started in CollectInterestsFragment which is displayed by SignUpActivity.
This fragment is not displayed when the activity is started. Also probably in the real application
it would not be the only fragment doing background work.
Thus we need some way to notify the activity that some background operation is in progress.
Let's make the activity to implement ListensForBackgroundWork interface with two methods:
 `onStartWork` and `onFinishWork`. Fragments should call these methods to notify the activity of
 background work.
* IdlingResource should not be created in production environment. It will be initialized only
 by calling getIdlingResource method from instrumented tests.
 This method has default (package only) access and @VisibleForTesting to indicate that it should be
 called from tests only.
* getIdlingResource method creates IdlingResource with unique name (see warning above)
* When doing background work fragments will check if the activity implement ListensForBackgroundWork
 and call `onStartWork` and `onFinishWork` methods
* The activity will increment IdlingResource counter in `onStartWork` and decrement it in `onFinishWork`
Note that there should be exactly one call of `onFinishWork` for each `onStartWork`
* @Before (set up) method in EspressoSignUpTest registers the idling resource in the activity to make
 Espresso wait for it
* @After (tear down) method in EspressoSignUpTest unregisters the idling resource once test case
 finished running (see also warning above)

## 3. Custom IdlingResource
Implement custom IdlingResource that is similar to CountingIdlingResource, but allows calling
 onFinishWork multiple times by utilizing unique work tags. The idea is to add unique tag for a work
 into a set. So the resource is idle when the set is empty.
Custom IdlingResource implementation should be thread-safe because its methods will be called from
 different threads sooner or later.
Also it is recommended to implement isIdleNow as close as possible to one simple boolean check.

===== Day 5

## 1. Custom global FailureHandler
* Make LinksAccountsTest#whenUserLinksAccountItAppearsInOverview failing by changing BALANCE field
* Add UiAutomator dependency for taking screenshot
* Implement custom FailureHandler that takes screenshot and delegate printing error to
 the default handler

## 2. Custom failure handler and custom view action to dump RecycleView descendant hierarchy
Default exception message doesn't containt dump of the recycleview descendants. And we need the
state of this descendants to know why .check is failing (suppose the screenshot is not enough).

* Implement custom ViewAction
 Espresso has HumanReadables#getViewHierarchyErrorMessage to dump hierarchy but it requires passing
 view that will be root of dumped hierarchy. One way to get view in Espresso is to implement
 a custom ViewAction.
* Third argument of getViewHierarchyErrorMessage is the original error (without hierarchy)
* Return a String from ViewAction via AtomicReference
* Call default Espresso failure handler with our fixed throwable

# Android Testing

This project is a fake Android app that is intended to be used in workshop training to learn Android testing practices.

The Android app is based on the concept of a personal financial tracking/analysis app.  The app supports the following user journeys:

 * Login
 * Signup - personal info, selecting personal interests, and selecting credentials
 * Accounts Overview - Shows transaction lists for each of your credit cards
 * Linked Accounts - Shows a list of all bank accounts you've linked to the app
 * Link Account - Links a bank account to the app (that account will then appear in the accounts overview)
 * Credit Cards - Shows aggregate credit information across all of your linked cards
 * Analysis - Shows the amount you spend per category across all of your linked cards

The project comes with a number of pre-written tests.  These tests are mostly Espresso integration tests, but some unit tests are included as well.

## User Journey Screenshots

![User Journey Screenshots](/docs/screenshots/user-journeys.jpg)
