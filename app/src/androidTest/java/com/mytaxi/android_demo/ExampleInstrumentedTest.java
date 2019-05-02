package com.mytaxi.android_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;
import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.misc.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private String username;
    private String password;
    private String searchKeyword;
    private String driverName;

    @Rule public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void testData() {
        //mActivity = main.get();
        username = "crazydog335";
        password = "venture";
        driverName = "Sarah Scott";
        searchKeyword = "sa";
    }

    @Test
    public void verifyLogin() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());

        onView((withId(R.id.edt_username)))
                .perform(ViewActions.typeText(username));
        onView((withId(R.id.edt_password)))
                .perform(ViewActions.typeText(password));
        onView((withId(R.id.btn_login)))
                .perform(click());
    }
    @Test
    public void verifySearchForDriver() throws Exception {
        Thread.sleep(3000);
        onView((withId(R.id.textSearch)))
                .perform(ViewActions.typeText(searchKeyword));
        Thread.sleep(3000);
        onView(withText(driverName))
                //.inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(scrollTo()).perform(click());

    }
}
