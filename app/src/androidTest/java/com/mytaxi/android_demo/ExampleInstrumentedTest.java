package com.mytaxi.android_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import com.mytaxi.android_demo.activities.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * Test to verify login, search for driver and call driver functionality
 *
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private String username;
    private String password;
    private String searchKeyword;
    private String driverName;
    private final String APP_TITLE_TEXT = "mytaxi demo";

    // launch the app
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // to allow mytaxi app to access device location
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    private MainActivity mActivity = null;

    // test data setup
    @Before
    public void testData() {
        mActivity = mActivityTestRule.getActivity();
        username = "crazydog335";
        password = "venture";
        driverName = "Sarah Scott";
        searchKeyword = "sa";

    }

    @Test
    public void test1_VerifyLogin() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        // verify the app package name
        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());

        // enter username
        ViewInteraction usernameTextBox = onView((withId(R.id.edt_username)));
        usernameTextBox.perform(ViewActions.typeText(username));

        //enter password
        ViewInteraction passwordTextBox = onView((withId(R.id.edt_password)));
        passwordTextBox.perform(ViewActions.typeText(password));

        // click on login button
        ViewInteraction loginButton = onView((withId(R.id.btn_login)));
        loginButton.perform(click());

        // wait for user to login
        Thread.sleep(1000);

        ViewInteraction appTextTitle = onView(
                allOf(
                        isAssignableFrom(AppCompatTextView.class),
                        withParent(isAssignableFrom(Toolbar.class))
                )
        );
        appTextTitle.check(matches(withText(APP_TITLE_TEXT)));
    }

    @Test
    public void test2_VerifySearchForDriver() throws Exception {

        // enter 'sa' in search text box
        Thread.sleep(1000);
        ViewInteraction driverSearchBox = onView((withId(R.id.textSearch)));
        driverSearchBox.perform(ViewActions.typeText(searchKeyword));

        // select the second option
        ViewInteraction secondOption = onView(withText(driverName))
                .inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))));
        secondOption.check(matches(isDisplayed())).perform(scrollTo()).perform(click());

        // verify the driver name
        ViewInteraction drivername = onView(withId(R.id.textViewDriverName));
        drivername.check(matches(withText(driverName)));

        // click on floating call button
        ViewInteraction callButton = onView(withId(R.id.fab));
        callButton.check(matches(isDisplayed()))
                  .perform(click());
        Thread.sleep(3000);
    }
}
