package com.project.denshaotoko.wordparser.parse;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.denshaotoko.wordparser.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Tests for the Parse main Screen
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ParseScreenTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<ParseActivity> mGithubActivityTestRule = new ActivityTestRule<>(ParseActivity.class);

    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    @Test
    public void lookUpString_checkWordCount() throws Exception {

        String input = "applepieshoe";

        // add delay to load dictionary
        pauseTestFor(5000);

        // Enter text to edit text
        onView(withId(R.id.et_input)).perform(clearText(), typeText(input));

        // click on the search button
        onView(withId(R.id.bt_parse)).perform(click());

        // add delay to view list
        pauseTestFor(3000);

        onView(withId(R.id.rv_words)).check(new RecyclerViewItemCountAssertion(5));

    }

    @Test
    public void lookUpString_checkSkipSymbols() throws Exception {

        String input = "app$epie$ho ";

        pauseTestFor(5000);

        // Enter text to edit text
        onView(withId(R.id.et_input)).perform(clearText(), typeText(input));

        // click on the search button
        onView(withId(R.id.bt_parse)).perform(click());

        // add delay to view list
        pauseTestFor(3000);

        onView(withId(R.id.rv_words)).check(new RecyclerViewItemCountAssertion(2));

        onView(withId(R.id.rv_words)).check(matches(hasDescendant(withText("pie"))));

    }



    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
